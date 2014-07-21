package org.apache.wolf.locator.strategy;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.wolf.locator.snitch.IEndpointSnitch;
import org.apache.wolf.locator.token.TokenMetadata;
import org.apache.wolf.token.StringToken;
import org.apache.wolf.token.Token;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

public abstract class AbstractReplicationStrategy {

	private TokenMetadata tokenMetadata;
	
	private final Map<Token, ArrayList<InetAddress>> cachedEndpoints = new NonBlockingHashMap<Token, ArrayList<InetAddress>>();

	protected final Map<String,String> configOptions;
	
	protected IEndpointSnitch snitch;
	
	AbstractReplicationStrategy(TokenMetadata metadata,Map<String,String> configOptions){
		this.tokenMetadata=metadata;
		this.configOptions=configOptions;
	}
	
	public ArrayList<InetAddress> getNaturalEndpoint(String str){
		Token token=new StringToken(str);
		Token keyToken=TokenMetadata.firstToken(tokenMetadata.getSortedTokens(), token);
		ArrayList<InetAddress> endpoints=getCachedEndpoints(keyToken);
		if(endpoints==null){
			TokenMetadata tokenMetadataClone=tokenMetadata.cloneOnlyTokenMap();
			keyToken=TokenMetadata.firstToken(tokenMetadata.getSortedTokens(), token);
			endpoints=new ArrayList<InetAddress>(calculateNaturalEndpoints(token,tokenMetadataClone));
			cacheEndpoints(keyToken,endpoints);
		}
		return endpoints;
	}

	private void cacheEndpoints(Token keyToken, ArrayList<InetAddress> endpoints) {
		cachedEndpoints.put(keyToken, endpoints);
	}

	public abstract Collection<? extends InetAddress> calculateNaturalEndpoints(
			Token token, TokenMetadata tokenMetadataClone);

	private ArrayList<InetAddress> getCachedEndpoints(Token keyToken) {
		if(keyToken==null)
			return null;
		return cachedEndpoints.get(keyToken);
	}

	public static AbstractReplicationStrategy createReplicationStrategy(String table,
			Class<? extends AbstractReplicationStrategy> strategyClass,
			TokenMetadata tokenMetadata, IEndpointSnitch snitch,
			Map<String, String> strategyOptions) {
		AbstractReplicationStrategy strategy;
		Class[] parameterTypes=new Class[]{String.class,TokenMetadata.class,IEndpointSnitch.class,Map.class};
		try{
			Constructor<? extends AbstractReplicationStrategy> constructor=strategyClass.getConstructor(parameterTypes);
			strategy=constructor.newInstance(table,tokenMetadata,snitch,strategyOptions);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return strategy;
	}

	public abstract void validateOptions() throws ConfigurationException;
}

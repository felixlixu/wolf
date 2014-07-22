package org.apache.wolf.locator.strategy;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.wolf.dht.token.Token;
import org.apache.wolf.locator.snitch.IEndpointSnitch;
import org.apache.wolf.locator.token.TokenMetadata;

public class OldNetworkTopologyStrategy extends AbstractReplicationStrategy {

	public OldNetworkTopologyStrategy(String table,TokenMetadata metadata,
			IEndpointSnitch snitch,Map<String, String> configOptions) {
		super(table,metadata, snitch,configOptions);
	}

	@Override
	public Collection<? extends InetAddress> calculateNaturalEndpoints(
			Token token, TokenMetadata metadata) {
		int replicas=getReplicationFactor();
		List<InetAddress> endpoints=new ArrayList<InetAddress>(replicas);
		ArrayList<Token> tokens=metadata.getSortedTokens();
		if(tokens.isEmpty())
			return endpoints;
		Iterator<Token> iter=TokenMetadata.ringIterator(tokens,token,false);
		Token primaryToken=iter.next();
		endpoints.add(metadata.getEndpoint(primaryToken));
		boolean bDataCenter=false;
		boolean bOtherRack=false;
		while(endpoints.size()<replicas&&iter.hasNext()){
			Token t=iter.next();
			if(!snitch.getDatacenter(metadata.getEndpoint(primaryToken)).equals(snitch.getDatacenter(metadata.getEndpoint(t)))){
				if(!bDataCenter){
					endpoints.add(metadata.getEndpoint(t));
					bDataCenter=true;
				}
				continue;
			}
			if(!snitch.getRack(metadata.getEndpoint(primaryToken)).equals(snitch.getRack(metadata.getEndpoint(t))) &&
			snitch.getDatacenter(metadata.getEndpoint(primaryToken)).equals(snitch.getDatacenter(metadata.getEndpoint(t)))){
				if(!bOtherRack){
					endpoints.add(metadata.getEndpoint(t));
					bOtherRack=true;
				}
			}
		}
		
        if (endpoints.size() < replicas)
        {
            iter = TokenMetadata.ringIterator(tokens, token, false);
            while (endpoints.size() < replicas && iter.hasNext())
            {
                Token t = iter.next();
                if (!endpoints.contains(metadata.getEndpoint(t)))
                    endpoints.add(metadata.getEndpoint(t));
            }
        }
		return endpoints;
	}

	private int getReplicationFactor() {
		return Integer.parseInt(this.configOptions.get("replication_factor"));
	}

	@Override
	public void validateOptions() throws ConfigurationException {
		// TODO Auto-generated method stub
		
	}

}

package org.apache.wolf.locator.token;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.wolf.DhtService;
import org.apache.wolf.locator.strategy.AbstractReplicationStrategy;
import org.apache.wolf.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterators;

public class TokenMetadata {
	
	private static Logger logger = LoggerFactory.getLogger(TokenMetadata.class);
	@SuppressWarnings("rawtypes")
	private BiMap<Token,InetAddress> tokenToEndpointMap;
	@SuppressWarnings("rawtypes")
	private ArrayList<Token> sortedTokens;
	private final ReadWriteLock lock=new ReentrantReadWriteLock(true);
	private final CopyOnWriteArrayList<AbstractReplicationStrategy> subscribers=new CopyOnWriteArrayList<AbstractReplicationStrategy>();
	@SuppressWarnings("rawtypes")
	public static Token firstToken(final ArrayList<Token> ring,Token start){
		return DhtService.firstToken(ring, start);
	}
	
	public TokenMetadata(){
		this(null);
	}

	@SuppressWarnings("rawtypes")
	public TokenMetadata(BiMap<Token,InetAddress> tokenToEndpointMap) {
		if(tokenToEndpointMap==null)
			tokenToEndpointMap=HashBiMap.create();
		this.tokenToEndpointMap=tokenToEndpointMap;
		setSortedTokens(sortTokens());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<Token> sortTokens() {
		ArrayList tokens=new ArrayList<Token>(tokenToEndpointMap.keySet());
		Collections.sort(tokens);
		return tokens;
	}

	public ArrayList<Token> getSortedTokens() {
		return sortedTokens;
	}

	public void setSortedTokens(ArrayList<Token> sortedTokens) {
		this.sortedTokens = sortedTokens;
	}

	public TokenMetadata cloneOnlyTokenMap() {
		lock.readLock().lock();
		try{
			return new TokenMetadata(HashBiMap.create(tokenToEndpointMap));
		}finally{
			lock.readLock().unlock();
		}
	}

	public static Iterator<Token> ringIterator(final ArrayList<Token> ring,
			Token start, boolean includeMin) {
		if(ring.isEmpty()){
			return includeMin?Iterators.singletonIterator(DhtService.getPartitioner().getMinimumToken())
					:Iterators.<Token>emptyIterator();
		}
		final boolean insertMin=(includeMin&&!ring.get(0).isMinimum())?true:false;
		final int startIndex=firstTokenIndex(ring,start,insertMin);
		return new AbstractIterator<Token>(){

			int j=startIndex;
			@Override
			protected Token computeNext() {
				if(j<-1){
					return endOfData();
				}
				try{
					if(j==-1){
						return DhtService.getPartitioner().getMinimumToken();
					}
					return ring.get(j);
				}finally{
					j++;
					if(j==ring.size()){
						j=insertMin?-1:0;
					}
					if(j==startIndex){
						j=-2;
					}
				}
			}
			
		};
	}

	private static int firstTokenIndex(ArrayList<Token> ring, Token start,
			boolean insertMin) {
		return DhtService.firstTokenIndex(ring, start, insertMin);
	}

	public InetAddress getEndpoint(Token primaryToken) {
		lock.readLock().lock();
		try{
			return tokenToEndpointMap.get(primaryToken);
		}finally{
			lock.readLock().unlock();
		}
	}

	public void updateNormalToken(Token token,InetAddress endpoint){
		assert token!=null;
		assert endpoint!=null;
		lock.writeLock().lock();
		try{
			tokenToEndpointMap.inverse().remove(endpoint);
			InetAddress prev=tokenToEndpointMap.put(token, endpoint);
			if(!endpoint.equals(prev)){
				if(prev!=null){
					logger.warn("Token " + token + " changing ownership from " + prev + " to " + endpoint);
				}
				sortedTokens=sortTokens();
			}
		}finally{
			lock.writeLock().unlock();
		}
	}

	public void unregister(AbstractReplicationStrategy subscriber) {
		subscribers.remove(subscriber);
	}
}

package org.apache.wolf.locator.token;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.wolf.DhtService;
import org.apache.wolf.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class TokenMetadata {
	
	private static Logger logger = LoggerFactory.getLogger(TokenMetadata.class);
	@SuppressWarnings("rawtypes")
	private BiMap<Token,InetAddress> tokenToEndpointMap;
	@SuppressWarnings("rawtypes")
	private ArrayList<Token> sortedTokens;
	
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
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

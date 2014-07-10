package org.apache.wolf.locator.token;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.wolf.DhtService;
import org.apache.wolf.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenMetadata {
	
	private static Logger logger = LoggerFactory.getLogger(TokenMetadata.class);
	
	public static Token firstToken(final ArrayList<Token> ring,Token start){
		return DhtService.firstToken(ring, start);
	}
}

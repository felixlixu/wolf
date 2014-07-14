package org.apache.wolf.token;

public class StringToken extends Token<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7735779025386301648L;


	public StringToken(String token){
		super(token);
	}
	
	
	public int compareTo(Token<String> o){
		return token.compareTo(o.token);
	}
}

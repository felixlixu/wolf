package org.apache.wolf.token;

public class StringToken extends Token<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7735779025386301648L;


	public StringToken(String token){
		super(token);
	}
	
	@Override
	public int compareTo(Token<String> o){
		return this.token.compareTo(o.token);
	}

}

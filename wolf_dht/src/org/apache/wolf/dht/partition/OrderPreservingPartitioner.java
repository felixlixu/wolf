package org.apache.wolf.dht.partition;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import javax.naming.ConfigurationException;

import org.apache.wolf.dht.token.DecoratedKey;
import org.apache.wolf.dht.token.StringToken;
import org.apache.wolf.dht.token.Token;
import org.apache.wolf.dht.token.TokenFactory;
import org.apache.wolf.utils.ByteBufferUtil;
import org.apache.wolf.utils.FBUtilities;
import org.apache.wolf.utils.Pair;

public class OrderPreservingPartitioner extends AbstractPartitioner<StringToken> {

	public static final StringToken MINIMUM=new StringToken("");
	public static final BigInteger CHAR_MASK = new BigInteger("65535");
    public final static char DELIMITER = ',';
    public final static String DELIMITER_STR = new String(new char[] { DELIMITER });
    
	@Override
	public Token<?> getMinimumToken() {
		return MINIMUM;
	}

	@Override
	public StringToken getToken(ByteBuffer key) {
		String skey;
		try{
			skey=ByteBufferUtil.String(key);
		}catch(CharacterCodingException e){
			throw new RuntimeException("The provided key was not UTF8 encoded.",e);
		}
		return new StringToken(skey);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Token midpoint(Token left, Token right) {
		int sigchars=Math.max(((StringToken)left).token.length(), ((StringToken)right).token.length());
		BigInteger leftchar=bigForString(((StringToken)left).token,sigchars);
		BigInteger rightchar=bigForString(((StringToken)right).token,sigchars);
		Pair<BigInteger,Boolean> midpair=FBUtilities.midpoint(leftchar,rightchar,16*sigchars);
		return new StringToken(stringForBig(midpair.left, sigchars, midpair.right));
	}

	private String stringForBig(BigInteger big, int sigchars, Boolean remainder) {
        char[] chars = new char[sigchars + (remainder ? 1 : 0)];
        if (remainder)
            // remaining bit is the most significant in the last char
            chars[sigchars] |= 0x8000;
        for (int i = 0; i < sigchars; i++)
        {
            int maskpos = 16 * (sigchars - (i + 1));
            // apply bitmask and get char value
            chars[i] = (char)(big.and(CHAR_MASK.shiftLeft(maskpos)).shiftRight(maskpos).intValue() & 0xFFFF);
        }
        return new String(chars);
	}

	//return integer from hight to low.
	private static BigInteger bigForString(String str, int sigchars) {
		assert str.length()<=sigchars;
		BigInteger big=BigInteger.ZERO;
		for(int i=0;i<str.length();i++){
			int charops=16*(sigchars-(i+1));
			BigInteger charbig=BigInteger.valueOf(str.charAt(i)&0xFFFF);
			big=big.or(charbig.shiftLeft(charops));
		}
		return big;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TokenFactory getTokenFactory() {
		return tokenFactory;
	}
	
	private final TokenFactory<String> tokenFactory=new TokenFactory<String>(){

		@Override
		public ByteBuffer toByteArray(Token<String> stringToken) {
			return ByteBufferUtil.bytes(stringToken.token);
		}

		@Override
		public Token<String> fromByteArray(ByteBuffer bytes) {
			try{
				return new StringToken(ByteBufferUtil.String(bytes));
			}catch(CharacterCodingException e){
				throw new RuntimeException(e);
			}
		}

		@Override
		public Token<String> fromString(String string) {
			return new StringToken(string);
		}

		@Override
		public void validate(String token) throws ConfigurationException {
			if(token.contains(DELIMITER_STR))
				 throw new ConfigurationException("Tokens may not contain the character " + DELIMITER_STR);
		}
		
	};

	@Override
	public DecoratedKey decoratekey(ByteBuffer key) {
		return new DecoratedKey<StringToken>(getToken(key),key);
	}

}

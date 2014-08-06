package org.apache.wolf.db.type;

import java.util.HashMap;
import java.util.Map;

public class TypeParser {

	private static final Map<String,AbstractType> cache=new HashMap<String,AbstractType>();
	
	public static AbstractType parse(String str) {
		if(str==null)
			return BytesType.instance;
		AbstractType type=cache.get(str);
		if(type!=null)
			return type;
		int i=0;
		i=skipBlank(str,i);
		int j=i;
		while(!isEOS(str,i)&&isIdentifierChar(str.charAt(i))){
			++i;
		}
		if(i==j)
			return BytesType.instance;
		String name=str.substring(j,i);
		i=skipBlank(str,i);
		if(!isEOS(str,i)&&str.charAt(i)=='(')
			type=getAbstractType(name,new TypeParser(str,i));
		else
			type=getAbstractType(name);
		cache.put(str, type);
		return type;
	}

	private static AbstractType getAbstractType(String name,
			TypeParser typeParser) {
		// TODO Auto-generated method stub
		return null;
	}

	private static int skipBlank(String str, int i) {
		while(!isEOS(str,i)&&isBlank(str.charAt(i)))
			++i;
		return i;
	}
	
    private static boolean isIdentifierChar(int c)
    {
        return (c >= '0' && c <= '9')
            || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
            || c == '-' || c == '+' || c == '.' || c == '_' || c == '&';
    }

	private static boolean isBlank(char c) {
		return c==' '||c=='\t'||c=='\n';
	}

	private static boolean isEOS(String str, int i) {
		return i>=str.length();
	}

}

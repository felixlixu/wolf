package org.apache.wolf.data.basetype;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.naming.ConfigurationException;
import org.apache.wolf.utils.FBUtilities;

public class TypeParser {

	@SuppressWarnings("rawtypes")
	private static final Map<String,AbstractType> cache=new HashMap<String,AbstractType>();
	private  String str;
	private  int idx;
	public static final TypeParser EMPTY_PARSER=new TypeParser("",0);
	
	public TypeParser(String str, int idx) {
		this.str=str;
		this.idx=idx;
	}

	@SuppressWarnings("rawtypes")
	public static AbstractType parse(String str) throws ConfigurationException, InvocationTargetException {
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

	@SuppressWarnings("rawtypes")
	private static AbstractType getAbstractType(String compareWith,
			TypeParser parser) throws ConfigurationException, InvocationTargetException {
		String className=compareWith.contains(".")?compareWith:"org.apache.wolf.db.type."+compareWith;
		Class<? extends AbstractType> typeClass=FBUtilities.classForName(className, "abstract-type");
		try{
			Method method=typeClass.getDeclaredMethod("getInstance", TypeParser.class);
			return (AbstractType) method.invoke(null, parser);
		}catch(NoSuchMethodException e){
			AbstractType type=getRawAbstractType(typeClass);
			return AbstractType.parseDefaultParameters(type,parser);
		}catch(IllegalAccessException e){
			AbstractType type=getRawAbstractType(typeClass);
			return AbstractType.parseDefaultParameters(type,parser);			
		}catch(InvocationTargetException e){
			@SuppressWarnings("unused")
			ConfigurationException ex=new ConfigurationException("Invalid definition for comparator"+typeClass.getName()+".");
			e.initCause(e.getTargetException());
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	private static AbstractType getRawAbstractType(
			Class<? extends AbstractType> typeClass) throws ConfigurationException {
		try {
			Field field=typeClass.getDeclaredField("instance");
			return (AbstractType)field.get(null);
		} catch (Exception e) {
			 throw new ConfigurationException("Invalid comparator class " + typeClass.getName() + ": must define a public static instance field or a public static method getInstance(TypeParser).");
		}
	}

	@SuppressWarnings("rawtypes")
	private static AbstractType getAbstractType(String compareWith) throws ConfigurationException {
		String className=compareWith.contains(".")?compareWith:"org.apache.wolf.db.type."+compareWith;
		Class<? extends AbstractType> typeClass=FBUtilities.classForName(className, "abstract-type");
		try{
			Field field=typeClass.getDeclaredField("instance");
			return (AbstractType)field.get(null);
		}catch(NoSuchFieldException e){
			return getRawAbstractType(typeClass,EMPTY_PARSER);
		}catch(IllegalAccessException e){
			return getRawAbstractType(typeClass,EMPTY_PARSER);
		}
	}

	@SuppressWarnings("rawtypes")
	private static AbstractType getRawAbstractType(
			Class<? extends AbstractType> typeClass, TypeParser emptyParser) throws ConfigurationException {
		Field field;
		try {
			field = typeClass.getDeclaredField("instance");
			try {
				return (AbstractType)field.get(null);
			} catch (IllegalArgumentException e) {
				throw new ConfigurationException("Invalid comparator class " + typeClass.getName() + ": must define a public static instance field or a public static method getInstance(TypeParser).");
			} catch (IllegalAccessException e) {
				throw new ConfigurationException("Invalid comparator class " + typeClass.getName() + ": must define a public static instance field or a public static method getInstance(TypeParser).");
			}
		} catch (NoSuchFieldException e) {
			throw new ConfigurationException("Invalid comparator class " + typeClass.getName() + ": must define a public static instance field or a public static method getInstance(TypeParser).");
		}
		
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

	public Map<String, String> getKeyValueParameters() throws ConfigurationException {
        Map<String, String> map = new HashMap<String, String>();

        if (isEOS())
            return map;

        if (str.charAt(idx) != '(')
            throw new IllegalStateException();

        ++idx; // skipping '('

        while (skipBlankAndComma())
        {
            if (str.charAt(idx) == ')')
            {
                ++idx;
                return map;
            }

            String k = readNextIdentifier();
            String v = "";
            skipBlank();
            if (str.charAt(idx) == '=')
            {
                ++idx;
                skipBlank();
                v = readNextIdentifier();
            }
            else if (str.charAt(idx) != ',' && str.charAt(idx) != ')')
            {
                throwSyntaxError("unexpected character '" + str.charAt(idx) + "'");
            }
            map.put(k, v);
        }
        throw new ConfigurationException(String.format("Syntax error parsing '%s' at char %d: unexpected end of string", str, idx));
	}

	private void throwSyntaxError(String msg) throws ConfigurationException {
		throw new ConfigurationException(String.format("Syntax error parsing '%s' at char %d: %s", str, idx, msg));
	}

	private void skipBlank() {
		idx=skipBlank(str,idx);
	}

	private String readNextIdentifier() {
        int i = idx;
        while (!isEOS() && isIdentifierChar(str.charAt(idx)))
            ++idx;

        return str.substring(i, idx);
	}

	private boolean skipBlankAndComma() {
        boolean commaFound = false;
        while (!isEOS())
        {
            int c = str.charAt(idx);
            if (c == ',')
            {
                if (commaFound)
                    return true;
                else
                    commaFound = true;
            }
            else if (!isBlank(c))
            {
                return true;
            }
            ++idx;
        }
        return false;
	}

	private boolean isBlank(int c) {
		return c == ' ' || c == '\t' || c == '\n';
	}

	private boolean isEOS() {
		return isEOS(str,idx);
	}

}

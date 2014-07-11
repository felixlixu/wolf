package org.apache.wolf.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

import javax.naming.ConfigurationException;



public class FBUtilities {

	public static final BigInteger TWO = new BigInteger("2");
	
	//private static volatile Logger logger_=LoggerFactory.getLogger(FBUtilities.class);
    public static <T> T construct(String classname, String readable) throws ConfigurationException
    {
        Class<T> cls = FBUtilities.classForName(classname, readable);
        try
        {
            return cls.getConstructor().newInstance();
        }
        catch (NoSuchMethodException e)
        {
            throw new ConfigurationException(String.format("No default constructor for %s class '%s'.", readable, classname));
        }
        catch (IllegalAccessException e)
        {
            throw new ConfigurationException(String.format("Default constructor for %s class '%s' is inaccessible.", readable, classname));
        }
        catch (InstantiationException e)
        {
            throw new ConfigurationException(String.format("Cannot use abstract class '%s' as %s.", classname, readable));
        }
        catch (InvocationTargetException e)
        {
            if (e.getCause() instanceof ConfigurationException)
                throw (ConfigurationException)e.getCause();
            throw new ConfigurationException();
        }
    }

	@SuppressWarnings("unchecked")
	public static<T> Class<T> classForName(String classname, String readable) throws ConfigurationException {
        try
        {
            return (Class<T>)Class.forName(classname);
        }
        catch (ClassNotFoundException e)
        {
            throw new ConfigurationException(String.format("Unable to find %s class '%s'", readable, classname));
        }
	}

	public static int encodedUTF8Length(String str) {
		int strlen=str.length();
		int utflen=0;
		for(int i=0;i<strlen;i++){
			int c=str.charAt(i);
			if((c>0x0001)&&(c<=0x007F))
				utflen++;
			else if(c>0x007F)
				utflen +=3;
			else
				utflen+=2;
		}
		
		return utflen;
	}

    /**
     * Given two bit arrays represented as BigIntegers, containing the given
     * number of significant bits, calculate a midpoint.
     *
     **/
	public static Pair<BigInteger, Boolean> midpoint(BigInteger left,
			BigInteger right, int sigbits) {
		BigInteger midpoint;
		boolean remainder;
		if(left.compareTo(right)<0){
			BigInteger sum=left.add(right);
			remainder=sum.testBit(0);
			midpoint=sum.shiftRight(1);
		}else{
			BigInteger max=TWO.pow(sigbits);
            BigInteger distance = max.add(right).subtract(left);
            remainder = distance.testBit(0);
            midpoint = distance.shiftRight(1).add(left).mod(max);
		}
		return new Pair<BigInteger,Boolean>(midpoint,remainder);
	}
}

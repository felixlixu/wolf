package org.apache.wolf.util;

import java.lang.reflect.InvocationTargetException;

import javax.naming.ConfigurationException;

public class FBUtilities {

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
}

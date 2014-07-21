package org.apache.wolf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;

public class CLibrary {

    private static Logger logger = LoggerFactory.getLogger(CLibrary.class);

    private static final int MCL_CURRENT = 1;
	
	private static native int mlockall(int flags) throws LastErrorException;
	
	   static
	    {
	        try
	        {
	            Native.register("c");
	        }
	        catch (NoClassDefFoundError e)
	        {
	            logger.info("JNA not found. Native methods will be disabled.");
	        }
	        catch (UnsatisfiedLinkError e)
	        {
	            logger.info("Unable to link C library. Native methods will be disabled.");
	        }
	        catch (NoSuchMethodError e)
	        {
	            logger.warn("Obsolete version of JNA present; unable to register C library. Upgrade to JNA 3.2.7 or later");
	        }
	    }
	
	public static void tryMlockall(){
		try{
			int result=mlockall(MCL_CURRENT);
			assert result==0;
			logger.info("JNA mlockall successful");
		}catch(UnsatisfiedLinkError e){
			
		}catch(RuntimeException e){
			if(!(e instanceof LastErrorException)){
				
			}
		}
	}
}

package org.apache.wolf.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FBUtilities {

	private static volatile Logger logger_=LoggerFactory.getLogger(FBUtilities.class);
	private static volatile InetAddress localInetAddress_;
	
	public static InetAddress getLocalAddress(){
		if(localInetAddress_==null){
			try{
				localInetAddress_=DatabaseDescriptor.getListenAddress()==null
								  ?InetAddress.getLocalHost()
								  :DatabaseDescriptor.getListenAddress();
								
			}catch(UnknownHostException e){
				throw new RuntimeException(e);
			}
		}
		return localInetAddress_;
	}
}

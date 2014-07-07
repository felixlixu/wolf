package org.apache.wolf.utils;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.naming.ConfigurationException;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FBUtilities {

	private static volatile Logger logger_=LoggerFactory.getLogger(FBUtilities.class);
	private static volatile InetAddress localInetAddress_;
	private static InetAddress broadcastInetAddress;
	
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

	public static InetAddress getBroadcastAddress() {
		if(broadcastInetAddress==null){
			broadcastInetAddress=DatabaseDescriptor.getBroadcastAddress()==null
					?getLocalAddress()
					:DatabaseDescriptor.getBroadcastAddress();
		}
		return broadcastInetAddress;
	}
}

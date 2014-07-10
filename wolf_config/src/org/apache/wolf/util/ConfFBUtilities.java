package org.apache.wolf.util;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.naming.ConfigurationException;

import org.apache.wolf.conf.DatabaseDescriptor;

public class ConfFBUtilities {
	
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
	
	public static InetAddress getBroadcastAddress() {
		if(broadcastInetAddress==null){
			broadcastInetAddress=DatabaseDescriptor.getBroadcastAddress()==null
					?getLocalAddress()
					:DatabaseDescriptor.getBroadcastAddress();
		}
		return broadcastInetAddress;
	}


}

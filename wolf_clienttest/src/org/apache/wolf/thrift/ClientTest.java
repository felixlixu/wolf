package org.apache.wolf.thrift;
import java.net.InetAddress;
import java.util.HashMap;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.gossip.GossipService;
import org.apache.wolf.service.StorageService;


public class ClientTest {

	private static void startClient() throws Exception{
		StorageService.instance.initClient();
		try{
			Thread.sleep(10000L);
		}catch(Exception ex){
			throw new AssertionError(ex);
		}
	}
	
    /** 
     * @param args 
     * @throws TException  
     * @throws AuthorizationException 
     * @throws AuthenticationException 
     */  
    public static void main(String[] args) throws TException, AuthenticationException, AuthorizationException {  
        // TODO Auto-generated method stub  
		int listenPort = DatabaseDescriptor.getRpcPort();
		InetAddress listenAddr = DatabaseDescriptor.getRpcAddress();
		System.out.println("The address is"+listenAddr+" The port is"+listenPort);
    	TTransport transport = new TSocket("localhost",listenPort);  
        long start=System.currentTimeMillis();  
//      TTransport transport = new TSocket("218.11.178.110",9090);  
        TProtocol protocol = new TBinaryProtocol(transport);  
        Wolf.Client client=new Wolf.Client(protocol);  
        transport.open();  
  
        AuthenticationRequest auth_request=new AuthenticationRequest();
        auth_request.credentials=new HashMap<String,String>();
        client.login(auth_request);
        System.out.println((System.currentTimeMillis()-start));  
        System.out.println("client sucess!");  
    } 

}

package org.apache.wolf.thrift;
import java.util.ArrayList;
import java.util.List;

import org.apache.db.RowMutation;
import org.apache.db.filter.QueryPath;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.wolf.service.StorageService;
import org.apache.wolf.utils.ByteBufferUtil;


public class ClientTest {

	private static String KEYSPACE="Keyspace1";
	private static String COLUMN_FAMILY="Standard1";

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
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
    	//startClient();
    	//setupKeyspace(createConnection());
    	testWriting();
		/*TSocket socket=new TSocket("localhost",9001);
		TTransport trans=socket;
		TProtocol protocol=new TBinaryProtocol(trans);
		Wolf.Client client=new Wolf.Client(protocol);
		trans.open();
		AuthenticationRequest auth_request=new AuthenticationRequest();
		Map<String, String> map = new HashMap<String, String>();
		auth_request.credentials=map;
		List<CfDef> cfDefList=new ArrayList<CfDef>();
		CfDef columnFamily=new CfDef(KEYSPACE,COLUMN_FAMILY);
		cfDefList.add(columnFamily);
		
		String f=client.system_add_keyspace(new KsDef(KEYSPACE,"org.apache.wolf.locator.strategy.OldNetworkTopologyStrategy",cfDefList));
		
		//client.login(auth_request);
		System.out.println(f);
		trans.close();*/
    }
    
    private static void testWriting() throws Exception{
    	for(int i=0;i<100;i++){
    		RowMutation change=new RowMutation(KEYSPACE,ByteBufferUtil.bytes(("key"+i)));
    		ColumnPath cp=new ColumnPath(COLUMN_FAMILY).setColumn(("colb").getBytes());
    		change.add(new QueryPath(cp),ByteBufferUtil.bytes("value"+i),0);
    		//StorageProxy.mutate(Arrays.asList(change),ConsistencyLevel.ONE);
    	}
    	System.out.println("Done Writing");
    }

	private static Wolf.Client createConnection() throws TTransportException {
		return createConnection("localhost",9001,false);
	}

	private static void setupKeyspace(Wolf.Client client) {
		List<CfDef> cfDefList=new ArrayList<CfDef>();
		CfDef columnFamily=new CfDef(KEYSPACE,COLUMN_FAMILY);
		cfDefList.add(columnFamily);
		
		try {
			client.system_add_keyspace(new KsDef(KEYSPACE,"org.apache.wolf.locator.strategy.OldNetworkTopologyStrategy",cfDefList));
			//int magnitude=client.describe_ring(KEYSPACE).size();
			//client
			try{
				Thread.sleep(1000*50);
			}catch(InterruptedException e){
				throw new RuntimeException(e);
			}
		} catch (InvalidRequestException e1) {
			e1.printStackTrace();
		} catch (SchemaDisagreementException e1) {
			e1.printStackTrace();
		} catch (TException e1) {
			e1.printStackTrace();
		}

		
	}

	private static Wolf.Client createConnection(String host,int port,boolean framed) throws TTransportException {
		TSocket socket=new TSocket(host,port);
		TTransport trans=socket;
		TProtocol protocol=new TBinaryProtocol(trans);
		trans.open();
		return new Wolf.Client(protocol);
	} 

}

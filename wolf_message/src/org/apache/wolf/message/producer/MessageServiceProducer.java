package org.apache.wolf.message.producer;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.ConfigurationException;

import org.apache.wolf.concurrent.Stage;
import org.apache.wolf.concurrent.StageManager;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.message.handler.IMessageCall;
import org.apache.wolf.message.handler.IVerbHandler;
import org.apache.wolf.message.handler.MessageDeliveryTask;
import org.apache.wolf.message.msg.Message;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.message.net.OutboundTcpConnection;
import org.apache.wolf.message.net.OutboundTcpConnectionPool;
import org.apache.wolf.message.net.SocketThread;
import org.apache.wolf.serialize.SerializerType;
import org.apache.wolf.util.ConfFBUtilities;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class MessageServiceProducer {
	
	private static Logger logger = LoggerFactory.getLogger(MessageServiceProducer.class);
	
	private static final int VERSION_11=4;
	private final Map<MessageVerb,IVerbHandler> verbHandlers_;
	private final NonBlockingHashMap<InetAddress,OutboundTcpConnectionPool> connectionManagers_=new NonBlockingHashMap<InetAddress,OutboundTcpConnectionPool>();
	public static final MessageVerb[] VERBS=MessageVerb.values();
	private List<SocketThread> socketThreads=Lists.newArrayList();
	private static AtomicInteger idGen=new AtomicInteger(0);

	public static SerializerType serializerType_=SerializerType.BINARY;
	
	public static MessageServiceProducer instance=new MessageServiceProducer();
	public static final int PROTOCOL_MAGIC = 0XCA552DFA;
	public static final int version_=VERSION_11;

	public static final EnumMap<MessageVerb,Stage> verbStages=new EnumMap<MessageVerb,Stage>(MessageVerb.class){
		private static final long serialVersionUID = 3826972761561603836L;
		{
			put(MessageVerb.MUTATION,Stage.MUTATION);
			put(MessageVerb.GOSSIP_DIGEST_SYN,Stage.GOSSIP);
		}
	};
	
	public MessageServiceProducer(){
		verbHandlers_=new EnumMap<MessageVerb,IVerbHandler>(MessageVerb.class);
	}
	
	public void registerVerbHandler(MessageVerb verb,IVerbHandler handler){
		assert !verbHandlers_.containsKey(verb);
		verbHandlers_.put(verb, handler);
	}
	
	public void listen(InetAddress localAddress) throws ConfigurationException, IOException {
		for(ServerSocket ss:getServerSocket(localAddress)){
			SocketThread th=new SocketThread(ss,"ACCEPT-"+localAddress);
			th.start();
			socketThreads.add(th);
		}
		//listenGate.singalAll();
	}
	
	public String sendRR(Message message,InetAddress to,IMessageCall cb,long timeout){
		String id=addCallback(cb,message,to,timeout);
		sendOneWay(message,id,to);
		return id;
	}
	
	public static AtomicInteger getIdGen() {
		return idGen;
	}
	
	public static void validateMagic(int magic) throws IOException {
		if(magic!=PROTOCOL_MAGIC)
			throw new IOException("invalid protocol header");
	}

	public static int getBits(int x, int p, int n) {
		 return x >>> (p + 1) - n & ~(-1 << n);
	}

	public void receive(Message message, String id) {
        if (logger.isTraceEnabled())
            logger.trace(ConfFBUtilities.getLocalAddress() + " received " + message.getVerb()
                          + " from " + id + "@" + message.getFrom());
        Runnable runnable=new MessageDeliveryTask(message,id);
        ExecutorService stage=StageManager.getStage(message.getMessageType());
        stage.execute(runnable);
	}

	public IVerbHandler getVerbHandler(MessageVerb verb) {
		return verbHandlers_.get(verb);
	}
	
	private List<ServerSocket> getServerSocket(InetAddress localAddress) throws IOException, ConfigurationException {
		final List<ServerSocket> ss=new ArrayList<ServerSocket>();
		ServerSocketChannel serverChannel=ServerSocketChannel.open();
		ServerSocket socket=serverChannel.socket();
		socket.setReuseAddress(true);
		InetSocketAddress address=new InetSocketAddress(localAddress,DatabaseDescriptor.getStoragePort());
		try{
			socket.bind(address);
		}catch(BindException e){
            if (e.getMessage().contains("in use"))
                throw new ConfigurationException(address + " is in use by another process.  Change listen_address:storage_port in cassandra.yaml to values that do not conflict with other services");
            else if (e.getMessage().contains("Cannot assign requested address"))
                throw new ConfigurationException("Unable to bind to address " + address
                        + ". Set listen_address in cassandra.yaml to an interface you can bind to, e.g., your private IP address on EC2");
            else
                throw e;
		}
		
		ss.add(socket);
		return ss;
	}
	
	private String addCallback(IMessageCall cb, Message message,
			InetAddress to, long timeout) {
		String messageId=nextId();
		return messageId;
	}

	private static String nextId() {
		return Integer.toString(idGen.incrementAndGet());
	}
	
	private void sendOneWay(Message message, String id, InetAddress to) {
		
		if(logger.isTraceEnabled()){
			logger.trace(ConfFBUtilities.getLocalAddress() + " sending " + message.getVerb() + " to " + id + "@" + to);
		}
		if(message.getFrom().equals(to)){
			receive(message,id);
			return;
		}
		OutboundTcpConnection connection=getConnection(to,message);
		connection.enqueue(message,id);
	}
	
	private OutboundTcpConnection getConnection(InetAddress to,Message message) {
		return getConnectionPool(to).getConnection(message);
	}

	private OutboundTcpConnectionPool getConnectionPool(InetAddress to) {
		OutboundTcpConnectionPool cp=connectionManagers_.get(to);
		if(cp==null){
			connectionManagers_.putIfAbsent(to, new OutboundTcpConnectionPool(to));
			cp=connectionManagers_.get(to);
		}
		return cp;
	}

	public void waitUntilListening() {
		// TODO Auto-generated method stub
		
	}

	public void sendOneWay(Message message, InetAddress to) {
		sendOneWay(message,nextId(),to);
	}

}

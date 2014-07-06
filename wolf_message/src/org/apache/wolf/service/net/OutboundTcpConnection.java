package org.apache.wolf.service.net;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.message.Header;
import org.apache.wolf.message.Message;
import org.apache.wolf.service.MessageService;
import org.apache.wolf.utils.FBUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundTcpConnection extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(OutboundTcpConnection.class);
	private volatile BlockingQueue<Entry> active=new LinkedBlockingQueue<Entry>();
	private volatile BlockingQueue<Entry> backlog=new LinkedBlockingQueue<Entry>();
	private Socket socket;
	private DataOutputStream out;
	private final AtomicLong dropped=new AtomicLong();
	private final OutboundTcpConnectionPool poolReference;
	private volatile long completed; 
	
	public OutboundTcpConnection(
			OutboundTcpConnectionPool pool) {
		super("WRITE-"+pool.endPoint());
		this.poolReference=pool;
	}

	public void enqueue(Message message, String id) {
		expireMessages();
        try
        {
            backlog.put(new Entry(message, id, System.currentTimeMillis()));
        }
        catch (InterruptedException e)
        {
            throw new AssertionError(e);
        }
	}

	private void expireMessages() {
		while(true){
			Entry entry=backlog.peek();
			if(entry==null||entry.timestamp>=System.currentTimeMillis()-DatabaseDescriptor.getRpcTimeout())
				break;
			Entry entry2=backlog.poll();
			if(entry2!=entry){
				if(entry2!=null)
					active.add(entry2);
				break;
			}
			dropped.incrementAndGet();
		}
	}
	
	public void run(){
		while(true){
			Entry entry=active.poll();
			if(entry==null){
				try{
					entry=backlog.take();
				}catch(InterruptedException e){
					throw new AssertionError(e);
				}
				BlockingQueue<Entry> tmp=backlog;
				backlog=active;
				active=tmp;
			}
			Message m=entry.message;
			String id=entry.id;
			if(socket!=null||connect()){
				writeConnected(m,id);
			}
		}
	}
	
	
	private void writeConnected(Message m, String id) {
		try {
			write(m,id,out);
			completed++;
			if(active.peek()==null){
				out.flush();
			}
		} catch (Exception e) {
			if(!(e instanceof IOException)){
				System.out.print(e.getMessage());
				System.out.print(e.getStackTrace());
				logger.error("error writing to " + poolReference.endPoint(), e);
			}
			else if(logger.isDebugEnabled())
				logger.debug("error writing to " + poolReference.endPoint(), e);
			disconnect();
		}

	}

	private void disconnect() {
		if(socket!=null){
			try {
				socket.close();
			} catch (IOException e) {
				if (logger.isDebugEnabled())
                    logger.debug("exception closing connection to " + poolReference.endPoint(), e);
			}
			out=null;
			socket=null;
		}
	}

	private void write(Message message, String id, DataOutputStream out2) throws IOException {
		int header=0;
		header|=MessageService.serializerType_.ordinal();
		if(false)
			header|=4;
		header|=(message.getVersion()<<8);
		out.writeInt(MessageService.PROTOCOL_MAGIC);
		out.writeInt(header);
		byte[] bytes=message.getBody();
		int total=messageLength(message.getHeader(),id,bytes);
		out.writeInt(total);
		out.writeUTF(id);
		Header.serializer().serialized(message.getHeader(), out, message.getVersion());
		out.writeInt(bytes.length);
		out.write(bytes);
	}

	static int messageLength(Header header, String id, byte[] bytes) {
		return 2+FBUtilities.encodedUTF8Length(id)+header.serializedSize()+4+bytes.length;
	}

	private boolean connect() {
        if (logger.isDebugEnabled())
            logger.debug("attempting to connect to " + poolReference.endPoint());
        long start=System.currentTimeMillis();
		while(System.currentTimeMillis()<start+DatabaseDescriptor.getRpcTimeout()){
			try {
				socket=poolReference.newSocket();
				socket.setKeepAlive(true);
				socket.setTcpNoDelay(true);
				out=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(),4096));
				return true;
			} catch (IOException e) {
				socket=null;
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}


	private static class Entry{
		final Message message;
		final String id;
		final long timestamp;
		
		Entry(Message message,String id,long timestamp){
			this.message=message;
			this.id=id;
			this.timestamp=timestamp;
		}
	}
}

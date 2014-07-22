package org.apache.wolf.message.net;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.wolf.message.msg.Header;
import org.apache.wolf.message.msg.Message;
import org.apache.wolf.message.producer.MessageServiceProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncomingTcpConnection extends Thread {

	private static Logger logger = LoggerFactory.getLogger(IncomingTcpConnection.class);
	private Socket socket;
	private InetAddress from;
	private static final int CHUNK_SIZE=1024*1024;

	public IncomingTcpConnection(Socket socket) {
		assert socket!=null;
		this.socket=socket;
		from=socket.getInetAddress();
	}
	
	public void run(){
		DataInputStream input;
		//boolean isStream=false;
		int version;
		try{
			input=new DataInputStream(socket.getInputStream());
			MessageServiceProducer.validateMagic(input.readInt());
			int header=input.readInt();
			//isStream=MessageServiceProducer.getBits(header,3,1)==1;
			version=MessageServiceProducer.getBits(header, 15, 8);
			logger.debug("Version for {} is {} ",from,version);
		    
			input=new DataInputStream(new BufferedInputStream(socket.getInputStream(),4096));
			receiveMessage(input,version);
			
			//System.out.println("validate successfully");
		}catch(EOFException e){
			 logger.trace("eof reading from socket; closing", e);
		}catch(IOException e){
			logger.debug("IOError reading from socket; closing", e);
		}catch(Exception e){
			logger.debug("IOError reading from socket; closing", e);
		}finally{
			close();
		}
	}

	private Message receiveMessage(DataInputStream input, int version) throws IOException {
		int totalSize=input.readInt();
		String id=input.readUTF();
		Header header=Header.serializer().deserialized(input, version);
		
		int bodySize=input.readInt();
		byte[] body=new byte[bodySize];
		int remainder=bodySize%CHUNK_SIZE;
		for(int offset=0;offset<bodySize-remainder;offset++){
			input.readFully(body,offset,CHUNK_SIZE);
		}
		input.readFully(body,bodySize-remainder,remainder);
		long remaining=totalSize-OutboundTcpConnection.messageLength(header,id,body);
		while(remaining>0){
			remaining-=input.skip(remaining);
		}
		if(version<=MessageServiceProducer.version_){
			Message message=new Message(header,body,version);
			MessageServiceProducer.instance.receive(message,id);
			return message;
		}
		logger.debug("Received connection from newer protocol version {}. Ignorning message", version);
		return null;
	}

	private void close() {
		if(from!=null)
			return;
		try {
			socket.close();
		} catch (IOException e) {
            if (logger.isDebugEnabled())
                logger.debug("error closing socket", e);
		}
	}

}

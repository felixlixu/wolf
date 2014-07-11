package org.apache.wolf.service.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.message.Message;
import org.apache.wolf.util.ConfFBUtilities;

public class OutboundTcpConnectionPool {

	private final InetAddress id;
	private final OutboundTcpConnection cmdCon;
	private final OutboundTcpConnection ackCon;
	private InetAddress resetedEndpoint;

	public OutboundTcpConnectionPool(InetAddress remoteEp) {
		id=remoteEp;
		cmdCon=new OutboundTcpConnection(this);
		cmdCon.start();
		ackCon=new OutboundTcpConnection(this);
		ackCon.start();
	}
	
	public OutboundTcpConnection getConnection(Message msg){
		return ackCon;
	}

	public InetAddress endPoint() {
		return resetedEndpoint==null?id:resetedEndpoint;
	}

	public Socket newSocket() throws IOException {
		return new Socket(endPoint(),DatabaseDescriptor.getStoragePort(),ConfFBUtilities.getLocalAddress(),0);
	}

}

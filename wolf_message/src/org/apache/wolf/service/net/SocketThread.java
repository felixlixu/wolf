package org.apache.wolf.service.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread extends Thread {

	private final ServerSocket server;
	
	public SocketThread(ServerSocket server, String name) {
		super(name);
		this.server=server;
	}
	
	public void run(){
		while(true){
			try{
				Socket socket=server.accept();
				new IncomingTcpConnection(socket).start();
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}
	}
	
	void close() throws IOException{
		server.close();
	}

}

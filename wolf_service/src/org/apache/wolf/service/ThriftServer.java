package org.apache.wolf.service;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.thrift.Wolf;
import org.apache.wolf.thrift.WolfServer;

public class ThriftServer extends Thread {

	private TServer serverEngine;
	
	public ThriftServer(InetAddress listenAddr,int listenPort){
		final WolfServer wolfserver=new WolfServer();
		Wolf.Processor processor=new Wolf.Processor(wolfserver);
		
		//TProtocolFactory tprotocolFactory=new TBinaryProtocol.Factory(true,true,DatabaseDescriptor.getThriftMaxMessageLength());
		TProtocolFactory tprotocolFactory=new TBinaryProtocol.Factory(true, true);
		int tFramedTransportSize=DatabaseDescriptor.getThriftFramedTransportSize();
		TTransportFactory inTransportFactory=new TFramedTransport.Factory(tFramedTransportSize);
		TTransportFactory outTransportFactory=new TFramedTransport.Factory(tFramedTransportSize);
		TServerTransport serverTransport;
		try {
			serverTransport = new TServerSocket(new InetSocketAddress(listenAddr,listenPort));
		} catch (TTransportException e) {
			throw new RuntimeException(String.format("Unable to create thrift socket to %s:%s", listenAddr, listenPort), e);
		}
		
		Args trArgs=new Args(serverTransport);
		trArgs.processor(processor);
		//trArgs.inputTransportFactory(inTransportFactory);
		//trArgs.outputTransportFactory(outTransportFactory);
		trArgs.protocolFactory(tprotocolFactory);
		serverEngine=new TThreadPoolServer(trArgs);
	}
	
	public void run(){
		serverEngine.serve();
	}
	
	public void stopServer(){
		serverEngine.stop();
	}
}

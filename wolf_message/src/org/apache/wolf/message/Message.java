package org.apache.wolf.message;

import java.net.InetAddress;

import org.apache.wolf.concurrent.Stage;
import org.apache.wolf.message.producer.MessageServiceProducer;

public class Message {
	final Header header_;
	private final byte[] body_;
	private final transient int version;
	
	public Header getHeader() {
		return header_;
	}
	public byte[] getBody() {
		return body_;
	}
	public int getVersion() {
		return version;
	}
	
	public Message(Header header,byte[] body,int version){
		assert header!=null;
		assert body!=null;
		this.header_=header;
		this.body_=body;
		this.version=version;
	}
	public Message(InetAddress from, MessageVerb verb,
			byte[] body, Integer version) {
		this(new Header(from,verb),body,version);
	}
	public MessageVerb getVerb() {
		return this.getHeader().getVerb();
	}
	public InetAddress getFrom() {
		return this.getHeader().getFrom();
	}
	
	public Stage getMessageType(){
		return MessageServiceProducer.verbStages.get(getVerb());
	}
}

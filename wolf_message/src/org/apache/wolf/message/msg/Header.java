package org.apache.wolf.message.msg;

import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;

import org.apache.wolf.message.serialize.CompactEndpointSerializationHelper;
import org.apache.wolf.message.serialize.HeaderSerializer;
import org.apache.wolf.serialize.IVersionedSerializer;
import org.apache.wolf.utils.FBUtilities;

public class Header {

	private final InetAddress from_;
	private final MessageVerb verb_;
	protected final Map<String,byte[]> details_;
	private static IVersionedSerializer<Header> serializer_;
	static{
		serializer_=new HeaderSerializer();
	}
	
	public static IVersionedSerializer<Header> serializer(){
		return serializer_;
	}
	
	public InetAddress getFrom() {
		return from_;
	}

	public MessageVerb getVerb() {
		return verb_;
	}

	public Map<String, byte[]> getDetails() {
		return details_;
	}

	public Header(InetAddress from,MessageVerb verb_){
		this(from,verb_,Collections.<String,byte[]>emptyMap());
	}

	public Header(InetAddress from, MessageVerb verb,
			Map<String, byte[]> detail) {
		assert from!=null;
		assert verb!=null;
		
		this.from_=from;
		this.verb_=verb;
		this.details_=detail;
	}

	public int serializedSize() {
		int size=0;
		size+=CompactEndpointSerializationHelper.serializedSize(this.getFrom());
		size+=4; //verb
		size+=4; //size of detail
		if(this.getDetails()==null)
			return size;
		for(String key:this.getDetails().keySet()){
			size+=2+FBUtilities.encodedUTF8Length(key);
			byte[] value=this.getDetails().get(key);
			size+=4+value.length;
		}
		return size;
		
	}
}

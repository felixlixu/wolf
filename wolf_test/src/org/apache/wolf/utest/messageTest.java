package org.apache.wolf.utest;

import java.io.IOException;
import java.net.InetAddress;

import javax.naming.ConfigurationException;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.message.MessageService;
import org.apache.wolf.message.handler.IMessageCall;
import org.apache.wolf.message.handler.MessageCallTest;
import org.apache.wolf.message.msg.Header;
import org.apache.wolf.message.msg.Message;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.util.ConfFBUtilities;
import org.apache.wolf.utils.FBUtilities;
import org.junit.Test;

public class messageTest {	
	@Test
	public void sendRRTest(){
		InetAddress to=ConfFBUtilities.getLocalAddress();
		Header header=new Header(to,MessageVerb.MUTATION,null);
		Message message=new Message(header,new byte[10],12);
		IMessageCall cb=new MessageCallTest();
		MessageService.instance.sendRR(message, to, cb, 122);
	}
}

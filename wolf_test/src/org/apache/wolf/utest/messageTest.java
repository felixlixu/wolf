package org.apache.wolf.utest;

import java.io.IOException;
import java.net.InetAddress;

import javax.naming.ConfigurationException;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.message.Header;
import org.apache.wolf.message.Message;
import org.apache.wolf.message.MessageVerb;
import org.apache.wolf.message.handler.IMessageCall;
import org.apache.wolf.message.handler.MessageCallTest;
import org.apache.wolf.service.MessageService;
import org.apache.wolf.util.ConfFBUtilities;
import org.apache.wolf.utils.FBUtilities;
import org.junit.Test;

public class messageTest {
	/*@Test
	public void serverTest(){
		try {
			MessageService.instance.listen(DatabaseDescriptor.getListenAddress());
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void sendRRTest(){
		InetAddress to=ConfFBUtilities.getLocalAddress();
		Header header=new Header(to,MessageVerb.MUTATION,null);
		Message message=new Message(header,new byte[10],12);
		IMessageCall cb=new MessageCallTest();
		MessageService.instance.sendRR(message, to, cb, 122);
	}
}

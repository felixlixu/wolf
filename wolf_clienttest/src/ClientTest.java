import java.net.InetAddress;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.wolf.message.Header;
import org.apache.wolf.message.Message;
import org.apache.wolf.message.MessageVerb;
import org.apache.wolf.message.handler.IMessageCall;
import org.apache.wolf.message.handler.MessageCallTest;
import org.apache.wolf.service.MessageService;
import org.apache.wolf.utils.FBUtilities;


public class ClientTest {

	public static void main(String[] args) {
		InetAddress to=FBUtilities.getLocalAddress();
		HashMap<String,byte[]> h=new HashMap<String,byte[]>();
		h.put("Test", new byte[9]);
		
		Header header=new Header(to,MessageVerb.MUTATION,null);
		Message message=new Message(header,new byte[10],3);
		IMessageCall cb=new MessageCallTest();
		MessageService.instance.sendRR(message, to, cb, 122);
		//MessageService.instance.sendRR(message, to, cb, 122);
		//MessageService.instance.sendRR(message, to, cb, 122);
		//MessageService.instance.sendRR(message, to, cb, 122);
		//MessageService.instance.sendRR(message, to, cb, 122);
	}

}

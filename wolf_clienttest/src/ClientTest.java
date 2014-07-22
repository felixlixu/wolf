import org.apache.wolf.gossip.GossipService;


public class ClientTest {

	public static void main(String[] args) {
		GossipService.instance.start(50);
	}

}

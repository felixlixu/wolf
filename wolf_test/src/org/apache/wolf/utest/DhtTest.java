package org.apache.wolf.utest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.naming.ConfigurationException;

import org.apache.wolf.DhtService;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.locator.strategy.AbstractReplicationStrategy;
import org.apache.wolf.locator.strategy.OldNetworkTopologyStrategy;
import org.apache.wolf.locator.token.TokenMetadata;
import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.partition.OrderPreservingPartitioner;
import org.apache.wolf.token.StringToken;
import org.apache.wolf.token.Token;
import org.junit.Assert;
import org.junit.Test;

public class DhtTest {
	@Test
	public void CollectionTest(){
		List<Integer> list=new ArrayList<Integer>();
		Random random=new Random();
		Integer target=49;
		for(int i=0;i<10;i++){
			Integer f=random.nextInt(100);
			list.add(f);
		}
		Collections.sort(list);
		int t=Collections.binarySearch(list, target);
		if(t<0){
			t=(t+1)*(-1);
		}
		list.add(t, target);
		for(int i=0;i<11;i++){
			System.out.println(list.get(i).toString());
		}
	}
	
	@Test
	public void TestPartitionerGetMinimum(){
		Token token=new StringToken("");
		Assert.assertEquals(new OrderPreservingPartitioner().getMinimumToken().compareTo(token),0);
	}
	
	@Test
	public void getTokenTest(){
		String str="helloworld";
		ByteBuffer bf=ByteBuffer.wrap(str.getBytes());
		StringToken strToke=new OrderPreservingPartitioner().getToken(bf);
		Assert.assertEquals(str,strToke.token);
	}
	
	@Test
	public void TestBigInteger(){
		StringToken str=new StringToken("THISdddddd");
		StringToken str1=new StringToken("THISddd");
		System.out.println(new OrderPreservingPartitioner().midpoint(str, str1).token);
		Assert.assertTrue(new OrderPreservingPartitioner().midpoint(str, str1).token.toString().contains("HI"));
	}
	
	@Test
    public void tokenFactory() throws UnknownHostException
    {
		TokenMetadata metadata=new TokenMetadata();
		Token token1 = new StringToken("HelloWorld12");
        InetAddress add1 = DatabaseDescriptor.getBroadcastAddress();
        metadata.updateNormalToken(token1, add1);
        Assert.assertEquals(metadata.getSortedTokens().size(), 1);
    }
	
	@Test
	public void TestAssignToken() throws ConfigurationException{
		TokenMetadata metadata=new TokenMetadata();
		Map<String,String> configOptions=new HashMap<String,String>();
		configOptions.put("replication_factor", "1");
		IPartitioner partitioner=DatabaseDescriptor.getPartitioner();
		DhtService.setPartitioner(partitioner);
		
		Token token1 = new StringToken("HelloWorld12");
        InetAddress add1 = DatabaseDescriptor.getBroadcastAddress();
        metadata.updateNormalToken(token1, add1);
		
		AbstractReplicationStrategy replication=new OldNetworkTopologyStrategy(metadata,configOptions);
		String token="helloworld";
		ArrayList<InetAddress> endpoints=replication.getNaturalEndpoint(token);
		Assert.assertEquals(endpoints.size(), 1);
		Assert.assertEquals(endpoints.get(0).getHostName(), "localhost");
	}
}

package org.apache.wolf.utest;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.wolf.partition.OrderPreservingPartitioner;
import org.apache.wolf.token.StringToken;
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
		StringToken token=new StringToken("");
		Assert.assertEquals(new OrderPreservingPartitioner().getMinimumToken().compareTo(token),0);
	}
	
	@Test
	public void getTokenTest(){
		String str="helloworld";
		ByteBuffer bf=ByteBuffer.wrap(str.getBytes());
		StringToken strToke=new OrderPreservingPartitioner().getToken(bf);
		Assert.assertEquals(str,strToke.token);
	}
}

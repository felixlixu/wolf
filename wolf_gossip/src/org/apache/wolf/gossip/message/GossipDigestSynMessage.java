package org.apache.wolf.gossip.message;

import java.util.List;

import org.apache.wolf.gossip.message.serialize.GossipDigestSynMessageSerializer;
import org.apache.wolf.serialize.IVersionedSerializer;

public class GossipDigestSynMessage {

	private String clusterId;
	private List<GossipDigest> gDigests;
	
	public static IVersionedSerializer<GossipDigestSynMessage> serializer=new GossipDigestSynMessageSerializer();

	public GossipDigestSynMessage(String clusterName,
			List<GossipDigest> gDigests) {
		this.setClusterId(clusterName);
		this.setgDigests(gDigests);
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public List<GossipDigest> getgDigests() {
		return gDigests;
	}

	public void setgDigests(List<GossipDigest> gDigests) {
		this.gDigests = gDigests;
	}

}

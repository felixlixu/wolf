package org.apache.wolf.gossip.message;

import java.net.InetAddress;

import org.apache.wolf.gossip.message.serialize.GossipDigestSerializer;
import org.apache.wolf.io.serialize.IVersionedSerializer;

public class GossipDigest implements Comparable<GossipDigest> {

	private int generation;
	private int maxVersion;
	private InetAddress address;
	private static final IVersionedSerializer<GossipDigest> serializer=new GossipDigestSerializer();

	public GossipDigest(InetAddress endpoint, int generation2, int maxVersion2) {
		this.address=endpoint;
		this.generation=generation2;
		this.maxVersion=maxVersion2;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int genreation) {
		this.generation = genreation;
	}

	public int getMaxVersion() {
		return maxVersion;
	}

	public void setMaxVersion(int maxVersion) {
		this.maxVersion = maxVersion;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	@Override
	public int compareTo(GossipDigest old) {
		if(generation!=old.getGeneration())
			return generation-old.getGeneration();
		return (maxVersion-old.getMaxVersion());
	}

	public static IVersionedSerializer<GossipDigest> getSerializer() {
		return serializer;
	}

}

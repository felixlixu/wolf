package org.apache.wolf.gossip.state;

import org.apache.wolf.gossip.state.serialize.HeartBeatStateSerializer;
import org.apache.wolf.serialize.IVersionedSerializer;

public class HeartBeatState {

	private int generation;
	private int version;
	private static IVersionedSerializer<HeartBeatState> serializer=new HeartBeatStateSerializer();
	

	public int getGeneration() {
		return generation;
	}

	public int getVersion() {
		return version;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public HeartBeatState(int gen) {
		this(gen,0);
	}

	public HeartBeatState(int gen, int v) {
		this.generation=gen;
		this.version=v;
	}

	public void updateHearBeatState() {
		version=VersionGenerator.getNextVersion();
	}

	public int getHeartBeatVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static IVersionedSerializer<HeartBeatState> getSerializer() {
		return serializer;
	}
	
}

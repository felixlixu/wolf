package org.apache.wolf.conf;

public class Config {
	
	public static String SNAPSHOT_SUBDIR_NAME="snapshots";
	private String listen_address;
	private String storage_port;
	private String rpc_Timeout;
	private int concurrent_writes;
	private String[] seeds;
	private String broadcast_address;
	private String endpoint_snitch;
	public boolean dynamic_snitch=true;
	private String cluster_name;
	private String partitioner;

	public Config(){}

	public String getListen_address() {
		return listen_address;
	}

	public void setListen_address(String listen_address) {
		this.listen_address = listen_address;
	}

	public String getStorage_port() {
		return storage_port;
	}

	public void setStorage_port(String storage_port) {
		this.storage_port = storage_port;
	}

	public String getRpc_Timeout() {
		return rpc_Timeout;
	}

	public void setRpc_Timeout(String rpc_Timeout) {
		this.rpc_Timeout = rpc_Timeout;
	}

	public int getConcurrent_writes() {
		return concurrent_writes;
	}

	public void setConcurrent_writes(int concurrent_writes) {
		this.concurrent_writes = concurrent_writes;
	}

	public String[] getSeeds() {
		return seeds;
	}

	public void setSeeds(String[] seeds) {
		this.seeds = seeds;
	}

	public String getBroadcast_address() {
		return broadcast_address;
	}

	public void setBroadcast_address(String broadcast_address) {
		this.broadcast_address = broadcast_address;
	}

	public String getEndpoint_snitch() {
		return endpoint_snitch;
	}

	public void setEndpoint_snitch(String endpoint_snitch) {
		this.endpoint_snitch = endpoint_snitch;
	}

	public String getCluster_name() {
		return cluster_name;
	}

	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}

	public String getPartitioner() {
		return partitioner;
	}

	public void setPartitioner(String partitioner) {
		this.partitioner = partitioner;
	}

}

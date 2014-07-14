package org.apache.wolf.locator.snitch;

import java.net.InetAddress;

public class SimpleSnitch extends AbstractEndpointSnitch {

	@Override
	public String getDatacenter(InetAddress endpoint) {
		return "datacenter1";
	}

	@Override
	public String getRack(InetAddress endpoint) {
		return "rack1";
	}



}

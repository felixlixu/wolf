package org.apache.wolf.locator.snitch;

import java.net.InetAddress;

public class DynamicEndpointSnitch implements IEndpointSnitch {

	private IEndpointSnitch subsnitch;

	public DynamicEndpointSnitch(IEndpointSnitch snitch) {
		this.subsnitch=snitch;
	}

	@Override
	public void gossiperStarting() {
		subsnitch.gossiperStarting();
	}

	@Override
	public String getDatacenter(InetAddress endpoint) {
		return subsnitch.getDatacenter(endpoint);
	}

	@Override
	public String getRack(InetAddress endpoint) {
		return subsnitch.getRack(endpoint);
	}

}

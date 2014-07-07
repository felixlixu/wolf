package org.apache.wolf.locator;

public class DynamicEndpointSnitch implements IEndpointSnitch {

	private IEndpointSnitch subsnitch;

	public DynamicEndpointSnitch(IEndpointSnitch snitch) {
		this.subsnitch=snitch;
	}

	@Override
	public void gossiperStarting() {
		subsnitch.gossiperStarting();
	}

}

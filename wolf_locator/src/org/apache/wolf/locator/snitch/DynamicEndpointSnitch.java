package org.apache.wolf.locator.snitch;

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

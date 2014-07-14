package org.apache.wolf.locator.snitch;

import java.net.InetAddress;

public interface IEndpointSnitch {

	public void gossiperStarting();

	public String getDatacenter(InetAddress endpoint);

	public String getRack(InetAddress endpoint);
}

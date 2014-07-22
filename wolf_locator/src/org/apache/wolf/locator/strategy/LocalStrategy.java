package org.apache.wolf.locator.strategy;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.wolf.dht.token.Token;
import org.apache.wolf.locator.snitch.IEndpointSnitch;
import org.apache.wolf.locator.token.TokenMetadata;

public class LocalStrategy extends AbstractReplicationStrategy {

	public LocalStrategy(String table,TokenMetadata metadata,IEndpointSnitch snitch,Map<String, String> configOptions) {
		super(table,metadata,snitch,configOptions);
	}

	@Override
	public Collection<? extends InetAddress> calculateNaturalEndpoints(
			Token token, TokenMetadata tokenMetadataClone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateOptions() throws ConfigurationException {
		// TODO Auto-generated method stub

	}

}

package org.apache.wolf.locator.strategy;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.wolf.locator.token.TokenMetadata;
import org.apache.wolf.token.Token;

public class LocalStrategy extends AbstractReplicationStrategy {

	LocalStrategy(TokenMetadata metadata, Map<String, String> configOptions) {
		super(metadata, configOptions);
		// TODO Auto-generated constructor stub
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

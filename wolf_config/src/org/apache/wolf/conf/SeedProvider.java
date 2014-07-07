package org.apache.wolf.conf;

import java.net.InetAddress;
import java.util.List;

public interface SeedProvider {
 List<InetAddress> getSeeds();
}

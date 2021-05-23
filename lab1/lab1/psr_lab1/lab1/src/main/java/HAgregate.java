import java.net.UnknownHostException;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import models.Library;

public class HAgregate {

    public static void init() throws UnknownHostException {
        ClientConfig clientConfig = HConfig.getClientConfig();
		final HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		IMap<Long, Library> libraries = client.getMap("libraries");
		System.out.println("\nLowest library size:");
		System.out.println(libraries.aggregate(Aggregators.integerMin("size"))+ "\n");
	}
}

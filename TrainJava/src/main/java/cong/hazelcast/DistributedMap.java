package cong.hazelcast;

import java.util.concurrent.ConcurrentMap;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class DistributedMap {

	public static void main(String[] args) {
		Config config = new Config();
		HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
		ConcurrentMap<String, String> map = h.getMap("my-distributed-map");
        map.put("key", "value");
        map.get("key");
         
        //Concurrent Map methods
        map.putIfAbsent("somekey", "somevalue");
        map.replace("key", "value", "newvalue");
	}

}

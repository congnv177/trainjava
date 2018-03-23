package cong.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QuorumConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class ClusterQuorum {

	public static void main(String[] args) {
		QuorumConfig qconfig = new QuorumConfig();
		qconfig.setName("MIN_TWO").setEnabled(true).setSize(2);
		
		MapConfig mconfig = new MapConfig();
		mconfig.setName("MIN_TWO").setQuorumName("MIN_TWO");
		
		Config config = new Config();
		config.addMapConfig(mconfig);
		config.addQuorumConfig(qconfig);
		
		HazelcastInstance hz1 = Hazelcast.newHazelcastInstance(config);
		HazelcastInstance hz2 = Hazelcast.newHazelcastInstance(config);
		
		IMap<String, String> map = hz1.getMap("MIN_TWO");
		map.put("1", "one");
		map.put("2", "two");
		
		for(String val : map.values()) {
			System.out.println(val);
		}
		
		hz2.getLifecycleService().shutdown();
		map.put("3", "three");
	}

}

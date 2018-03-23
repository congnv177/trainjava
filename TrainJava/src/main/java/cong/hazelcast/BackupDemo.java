package cong.hazelcast;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class BackupDemo {

	public static void main(String[] args) {
		HazelcastInstance member1 = Hazelcast.newHazelcastInstance();
		HazelcastInstance member2 = Hazelcast.newHazelcastInstance();
		
		Map<Integer, String> customers = member1.getMap("customers");
		customers.put(1, "google");
		customers.put(2, "apple");
		customers.put(3, "microsoft");
		customers.put(4, "AOL");
		customers.put(5, "Yahoo");
		
		System.out.println("Hazelcast Nodes in this cluster :"+Hazelcast.getAllHazelcastInstances().size());
		member1.shutdown();
		
		Map<Integer, String> customersRestored = member2.getMap("customers");
		for(String val: customersRestored.values()) {
			System.out.println(" - "+val);
		}
	}

}

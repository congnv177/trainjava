package cong.hazelcast;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastInstCreator {

	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		Map<Integer, String> map = hazelcastInstance.getMap("customers");
		map.put(1, "apple");
		map.put(2, "google");
		
		System.out.println("size of map inside hazelcast :" + hazelcastInstance.getMap("customers"));
		
	}

}

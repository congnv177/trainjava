package cong.hazelcast;

import java.util.concurrent.BlockingQueue;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Member {

	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		System.out.println("Instance Started...!");
		
		BlockingQueue<String> queue = hazelcastInstance.getQueue("queue");
		for(;;) {
			try {
				System.out.println(queue.take());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

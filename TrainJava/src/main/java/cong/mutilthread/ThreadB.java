package cong.mutilthread;

public class ThreadB implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(100);
			System.out.println("Vao thread B...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

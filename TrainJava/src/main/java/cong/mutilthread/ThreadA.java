package cong.mutilthread;

public class ThreadA implements Runnable{

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			System.out.println("Vao thread A...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

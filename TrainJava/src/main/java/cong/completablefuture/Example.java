package cong.completablefuture;

import java.util.concurrent.CompletableFuture;

public class Example {

	public static void main(String[] args) {
		//Create new Calculator object
		Calculator calculator = new Calculator(2, 3);
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(calculator);
		cf.thenAccept(result -> {
			System.out.println(result);
		});
		System.out.println("End...");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) throws ExecutionException, InterruptedException {
//	// Create thread pool using Executor Framework
//	ExecutorService executor = Executors.newFixedThreadPool(10);
//
//	// Create new Calculator object
//	Calculator c = new Calculator(2, 3);
//
//	Future<Integer> f = executor.submit(c);
//
//	System.out.println(f.get());
//
//	System.out.println("End...");
//
//	executor.shutdown();
//}
}

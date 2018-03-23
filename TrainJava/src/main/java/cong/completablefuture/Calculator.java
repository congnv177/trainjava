package cong.completablefuture;

import java.util.function.Supplier;

public class Calculator implements Supplier<Integer>{

	private int a;
	private int b;

	public Calculator(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	public Integer get() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return a + b;
	}
//	public class Calculator implements Callable<Integer> {
//    
//    private int a;
//    private int b;
// 
//    public Calculator(int a, int b) {
//        this.a = a;
//        this.b = b;
//    }
//    
//    public Integer call() throws Exception {
//        Thread.sleep(3000);
//        
//        return a + b;
//    }
//}
}

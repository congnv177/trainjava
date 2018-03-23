package cong.rabbitmq;

public class CallFuture<T> {
	private Callback<T> callback;

	public Callback<T> getCallback() {
		return callback;
	}

	public void setCallback(Callback<T> callback) {
		this.callback = callback;
	}

	public void complete(T t) {
		if (callback != null) {
			callback.apply(t);
		} else {
			System.out.println("Callback is NULL");
		}
	}
}

package cong.rabbitmq;

public interface Callback<T> {
	void apply(T t);
}

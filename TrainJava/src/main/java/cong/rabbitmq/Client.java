package cong.rabbitmq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Client {
	private Connection connection;
	private Channel channel;
	private String requestQueueName = "rpc_queue";
	private String replyQueueName;
	
	public static void main(String[] args) {
		System.out.println("Bạn tên là gì?");		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Callback<String> callback = new Callback<String>() {
			
			@Override
			public void apply(String t) {
				System.out.println("Received: " + t);
			}
		};
		
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				Client conn;
				try {
					conn = new Client();
					CallFuture<String> future = conn.call(reader.readLine().toString());
					future.setCallback(callback);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Client() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		connection = factory.newConnection();
		channel = connection.createChannel();

		replyQueueName = channel.queueDeclare().getQueue();
	}

	public CallFuture<String> call(String message) throws IOException, InterruptedException {
		CallFuture<String> callFuture = new CallFuture<>();
		final String corrId = UUID.randomUUID().toString();

		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();
		channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
		channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				if (properties.getCorrelationId().equals(corrId)) {
					callFuture.complete(new String(body, "UTF-8"));
				}
			}
		});

		return callFuture;
	}

	public void close() throws IOException {
		connection.close();
	}
}

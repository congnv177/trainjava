package cong.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import lombok.extern.slf4j.Slf4j;

//-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
//-DAsyncLoggerConfig.RingBufferSize=1024*1024
//-Dlog4j.configurationFile=conf/log4j2.xml
@Slf4j
public class Server {
	private static final String RPC_QUEUE_NAME = "rpc_queue";

	private static String conn(String txt) {
		if (!txt.equals(null))
			return "Chào bạn " + txt;
		return "Vui lòng nhập tên!";
	}

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		Connection connection = null;
		try {
			connection = factory.newConnection();
			final Channel channel = connection.createChannel();

			channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

			channel.basicQos(1);

			log.debug(" [x] Awaiting RPC requests");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
							.correlationId(properties.getCorrelationId()).build();

					String response = "";
					try {
						String message = new String(body, "UTF-8");
						Thread.sleep(2000);
						response = conn(message);
					} catch (RuntimeException e) {
						log.debug(" [.] " + e.toString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
						channel.basicAck(envelope.getDeliveryTag(), false);
						// RabbitMq consumer worker thread notifies the RPC server owner thread
						synchronized (this) {
							this.notify();
						}
					}
				}
			};

			channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
			// Wait and be prepared to consume the message from RPC client.
			while (true) {
				synchronized (consumer) {
					try {
						consumer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (IOException _ignore) {
				}
		}
	}

}

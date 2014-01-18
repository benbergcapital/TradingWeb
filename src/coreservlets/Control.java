package coreservlets; // Always use packages!!
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;
import java.util.logging.Level;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;



public class Control {
  
	 private static String queue_web_request = "";
	 private static String queue_web_response = "";
		 private static String QUsername="";
		 private  static String QPassword="";
		 static String requestQueueName = "rpc_queue";
	public static String SendMessage(String Request)
	{	 byte[] response = null;
		try{
			 Properties props = new Properties();
			
			props.load(new FileInputStream("c:\\config.properties"));
			QUsername = props.getProperty("qusername");
	    	QPassword = props.getProperty("qpassword");
	    	queue_web_response = props.getProperty("queue_web_response");
	    	queue_web_request = props.getProperty("queue_web_request");
			
			
	    	 ConnectionFactory factory = new ConnectionFactory();
			    factory.setHost("localhost");
			    factory.setUsername(QUsername); 
				factory.setPassword(QPassword); 
				factory.setVirtualHost("/"); 
			    
			    Connection connection = factory.newConnection();
	    	
			Channel channel = connection.createChannel();
		
			String replyQueueName= channel.queueDeclare().getQueue(); 
			QueueingConsumer consumer = new QueueingConsumer(channel);
			    channel.basicConsume(replyQueueName, true, consumer);
			 
			    String corrId = java.util.UUID.randomUUID().toString();

			    BasicProperties props1 = new BasicProperties
	                    .Builder()
	                    .correlationId(corrId)
	                    .replyTo(replyQueueName)
	                    .build();

	channel.basicPublish("", requestQueueName, props1, Request.getBytes());

				while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
					if (delivery.getProperties().getCorrelationId().equals(corrId)) 
					{
							response = delivery.getBody();
								break;
					}
				}
				 connection.close();
	    
		}
		catch (Exception e)
		{
			
		}
			//	return response;
		String[] ar_result = new String[6];  
				ByteArrayInputStream b = new ByteArrayInputStream(response);
		       
		        try {
		        	 ObjectInputStream o = new ObjectInputStream(b);
					ar_result= (String[]) o.readObject();
				} 
			 catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Request;
		
	
	}
	
	
  public static int randomInt(int range) {
	  System.out.println("mrandomInt ran");
	  return(33);
  }

   
  }

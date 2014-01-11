package coreservlets; // Always use packages!!
import java.io.IOException;
import java.util.logging.Level;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import org.jeromq.*;

public class RanUtilities {
  
	   private final static String QUEUE_WEBQUERY = "WEBREQUEST";
		 private final static String QUEUE_WEBRESPONSE = "WEBRESPONSE";
	public static String SendMessage(String Request)
	{
		try{
				 ConnectionFactory factory = new ConnectionFactory();
				    factory.setHost("localhost");
				    Connection connection = factory.newConnection();
				    Channel channel_Recv = connection.createChannel();
				    Channel channel_Send = connection.createChannel();
				    channel_Recv.queueDeclare(QUEUE_WEBRESPONSE, false, false, false, null);
				    channel_Send.queueDeclare(QUEUE_WEBQUERY, false, false, false, null);
				    
				    String message = Request;
				    channel_Send.basicPublish("", QUEUE_WEBQUERY, null, message.getBytes());
				    System.out.println(" [x] Sent '" + message + "'"); 
				    
				    QueueingConsumer consumer = new QueueingConsumer(channel_Recv);
				    channel_Recv.basicConsume(QUEUE_WEBRESPONSE, true, consumer);
				    
				  
				     System.out.println("Web Server waiting for web query response on Queue : "+QUEUE_WEBRESPONSE);
				      
				     
				     QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				      String message_response = new String(delivery.getBody());
				      
				      System.out.println(" [x] Received '" + message_response + "'");
				      
				      channel_Recv.close();
				      channel_Send.close();
				      
					    connection.close();
					    return message_response;
		    
		}
		catch (Exception e)
		{
			
			 System.out.println(e.toString());
		}
		
		
		
		
		return null;
	}
	
	
  public static int randomInt(int range) {
	  System.out.println("mrandomInt ran");
	  return(33);
  }

   
  }

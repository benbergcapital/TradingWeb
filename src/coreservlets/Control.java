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
	{	String response = "";
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
                   Channel channel_Recv = connection.createChannel();
                   Channel channel_Send = connection.createChannel();
                   channel_Recv.queueDeclare(queue_web_response, false, false, false, null);
                   channel_Send.queueDeclare(queue_web_request, false, false, false, null);
                   
                   String message = Request;
                   channel_Send.basicPublish("", queue_web_request, null, message.getBytes());
                   System.out.println(" [x] Sent '" + message + "'"); 
                   
                   QueueingConsumer consumer = new QueueingConsumer(channel_Recv);
                   channel_Recv.basicConsume(queue_web_response, true, consumer);
                   
                 
                    System.out.println("Web Server waiting for web query response on Queue : "+queue_web_response);
                     
                    String message_response="";
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery(10000);
                    if (!(delivery == null))
                    {
                     message_response = new String(delivery.getBody());
                     
                     System.out.println(" [x] Received '" + message_response + "'");
                     
                     channel_Recv.close();
                     channel_Send.close();
                     
                    }
                    else
                    {
                    message_response = "404";
                    }
                     
                           connection.close();
                           return message_response;
	    
		}
		catch (Exception e)
		{
			
		}
	/*		//	return response;
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
				
				*/
				return response;
		
	
	}
	
	
  public static int randomInt(int range) {
	  System.out.println("mrandomInt ran");
	  return(33);
  }

   
  }
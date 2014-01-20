package coreservlets; // Always use packages!!
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;



import org.json.simple.JSONObject;


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
		 private static boolean IsReady =false;
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
			    
			
             //      Channel channel_Recv = connection.createChannel();
           //        Channel channel_Send = connection.createChannel();
           //        channel_Recv.queueDeclare(queue_web_response, false, false, false, null);
           //        channel_Send.queueDeclare(queue_web_request, false, false, false, null);
                   
				  String corrId = java.util.UUID.randomUUID().toString();
				   Connection connection = factory.newConnection();
			        Channel channel = connection.createChannel();

			        channel.exchangeDeclare("Exchange.Web", "topic");

			        String routingKey = "web.request."+corrId;
			        JSONObject obj = new JSONObject();
	                 
	                obj.put("CorrelationID",corrId);
	                obj.put("Message", Request);
			        
			        
	                
	                
	                
	                //start listen before sending
	                String queueName = channel.queueDeclare().getQueue();
	                
	            //	 Thread t = new Thread(
               	//	        new StartListening(channel, queueName, response,corrId), "IdleConnectionKeepAlive"
               	//	    );
	            //	 t.start();
	                
	            	 ExecutorService pool = Executors.newFixedThreadPool(3);
	            	 Callable<String> callable = new StartListening(channel, queueName,corrId);
	            	 Future<String> future = pool.submit(callable);
	            	 
	            //	 while (!IsReady)
	            //	 {
	            //		 
	            //	 }
	            	 Thread.sleep(500);
			        channel.basicPublish("Exchange.Web", routingKey, null, obj.toJSONString().getBytes());
			        System.out.println(" [x] Sent '" + routingKey + "':'" + obj.toJSONString() + "'");

			
                       response = future.get();
                       channel.close();
           		    connection.close();	        
			       

			         
			        
			      

			       

			        
			        
			        System.out.println("Joined back to main thread");
			        System.out.println("resposne = "+response);
			     //   QueueingConsumer consumer = new QueueingConsumer(channel);
			    //    channel.basicConsume(queueName, true, consumer);

			     //   while (true) {
			    //        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			     //       response = new String(delivery.getBody());
			      //      String routingKey1 = delivery.getEnvelope().getRoutingKey();

			        //    System.out.println(" [x] Received '" + routingKey1 + "':'" + response + "'");
			    //    }
			   // channel.close();
			   // connection.close();	        
                   
                   /*
                   
                   
                   channel_Send.basicPublish("", queue_web_request, null, obj.toJSONString().getBytes());
                   System.out.println(" [x] Sent '" + obj.toJSONString() + "'"); 
                   
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
                     
                           
                           return message_response;
                           
                           
                           */
	    
					}
					catch (Exception e)
					{
						
					}
		
		
			
	
				return response;
		
	
	}
	public static void SetReady()
	{
		IsReady = true;
	}
	
	private static class StartListening implements Callable<String> {

		ConnectionFactory factory;
		Connection connection;
		Channel channel;
		String queueName;
		String response="";
		String CorrId;
		
	    public StartListening(Channel channel, String queueName,  String CorrId) {
	        this.channel = channel;
	        this.queueName = queueName;
	         this.CorrId = CorrId;
	    }

	    @Override
		public String call() throws Exception {
	    	System.out.println("waitinging for messages. To exit press CTRL+C");
	    	  try{
	    		  channel.queueBind(queueName, "Exchange.Web", "web.response."+CorrId);
	    		  QueueingConsumer consumer = new QueueingConsumer(channel);
	    		  channel.basicConsume(queueName, true, consumer);
	    			Control.IsReady = true;
	    			System.out.println("waitinging for messages : "+IsReady);
		     //   while (true) {
		            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		          
		            response = new String(delivery.getBody());
		            String routingKey1 = delivery.getEnvelope().getRoutingKey();

		            System.out.println(" [x] Received '" + routingKey1 + "':'" + response + "'");
		    //    }
		  
	  				
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  System.out.println(e.toString());
	    		  response ="";
	    	  }
	  		
			return response;
		}
	            	
	    
	}
	
	
	
	
  public static int randomInt(int range) {
	  System.out.println("mrandomInt ran");
	  return(33);
  }

   
  }
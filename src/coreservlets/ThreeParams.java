package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/** Servlet that prints out the param1, param2, and param3
 *  request parameters. Does not filter out HTML tags.
 *  <p>
 *  From <a href="http://courses.coreservlets.com/Course-Materials/">the
 *  coreservlets.com tutorials on servlets, JSP, Struts, JSF, Ajax, GWT, and Java</a>.
 */
@WebServlet("/ThreeParams")
public class ThreeParams extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  
	  String Response="";
	  
	  if (request.getParameter("btn").equals("RUN_EMAIL_LISTENER"))
	  {
		  Response=Control.SendMessage("RUN_EMAIL_LISTENER");
		  
	  }
	  if (request.getParameter("btn").equals("CREATE_ORDER"))
	  {
		  String ticker=request.getParameterValues("ticker")[0];
		  String quantity=request.getParameterValues("quantity")[0];
		  String side=request.getParameterValues("side")[0];
		  
		  Response=Control.SendMessage("NEW_ORDER;"+ticker+";"+quantity+";"+side);
		  
	  }
	  else
	  {
	 Response= Control.SendMessage("CANCELALL");
	  }
	  
	  response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	  
	 //   out.println(Response);
	  
	
  
    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE></TITLE>" +
                "<meta http-equiv=\"refresh\" content=\"2; URL=/TradingWeb/Index.jsp\">" +
                "</HEAD>\n" +
                "<BODY>\n" +
                Response+"\n"+
                ". Auto redirect back in 2 seconds "+
                "</BODY></HTML>");
                
  }
  }


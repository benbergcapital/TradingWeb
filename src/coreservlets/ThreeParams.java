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
	  
	 String Response= Control.SendMessage("CANCELALL");
	    
	  response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	  
	    out.println(Response);
	  
	
     String title = "Cancelling Orders";
    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>" + title + "</TITLE>" +
                "<meta http-equiv=\"refresh\" content=\"2; URL=/TradingWeb/Index.jsp\">" +
                "</HEAD>\n" +
                "<BODY>\n" +
                Response+"\n"+
                "Auto redirect back in 2 seconds "+
                "</BODY></HTML>");
                
  }
}

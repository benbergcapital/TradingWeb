<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- 
One of four random-number examples. This one uses expressions. 
-->
<HTML>
<HEAD>
<TITLE>Random Numbers</TITLE>
<LINK REL=STYLESHEET
      HREF=""
      TYPE="text/css">

  <head>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
    <script type='text/javascript'>
      google.load('visualization', '1', {packages:['table']});
      google.setOnLoadCallback(drawHistoryTable);
      google.setOnLoadCallback(drawOrdersTable);
     google.setOnLoadCallback(drawPositionsTable);

 function drawHistoryTable() {
    	  
    	  var myObject = <%=coreservlets.Control.SendMessage("GETHISTORY")%>;
    	   	
    	  var data = new google.visualization.DataTable(myObject);

        var table = new google.visualization.Table(document.getElementById('table_history_div'));
        var options = {
        		  width: 400,
        		 
        		  ShowRowNumber:true
        		};
       table.draw(data, options);
      }
      
      function drawOrdersTable() {
    	  
    	  var myObject = <%=coreservlets.Control.SendMessage("GETORDERS")%>;
    	   	
    	  var data = new google.visualization.DataTable(myObject);

        var table = new google.visualization.Table(document.getElementById('table_orders_div'));
        var options = {
        		  width: 400,
        		 
        		  ShowRowNumber:true
        		};
       table.draw(data, options);
      }
 function drawPositionsTable() {
    	  
    	  var myObject = <%=coreservlets.Control.SendMessage("GETPOSITIONS")%>;
    	   	
    	  var data = new google.visualization.DataTable(myObject);

        var table = new google.visualization.Table(document.getElementById('table_positions_div'));
        var options = {
      		  width: 400,
      		 
      		  ShowRowNumber:true
      		};
       table.draw(data, options);
      }
 
 window.onload = function IsConnected() {
	  
	  var response = "<%=coreservlets.Control.SendMessage("ISCONNECTED")%>";
	
	document.getElementById("ConnectionStatus").innerText = "System Status = "+response;

};

 
    </script>
  </head>

</HEAD>
<BODY>
<div id="ConnectionStatus">asdasd</div>
<H3>History</H3>
   <div id='table_history_div'></div>
<H3>Live Orders</H3>
   <div id='table_orders_div'></div>
    <form action="${pageContext.request.contextPath}/ThreeParams" method="post">
    <input type="submit" name="Cancel Any Orders" value="Cancel Orders" />
 </form>
<H3>Open Positions</H3>
  <div id='table_positions_div'></div>
 
 
<br><br><br><br><br><br>

</BODY></HTML>
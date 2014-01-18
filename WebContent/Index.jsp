<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- 
One of four random-number examples. This one uses expressions. 
-->
<HTML>
<HEAD>
<TITLE>Random Numbers</TITLE>
<LINK REL=STYLESHEET HREF="" TYPE="text/css">
<head>
<script type='text/javascript' src='https://www.google.com/jsapi'></script>
<script type='text/javascript'>
      google.load('visualization', '1', {packages:['table']});
      google.setOnLoadCallback(drawHistoryTable);
      google.setOnLoadCallback(drawOrdersTable);
     google.setOnLoadCallback(drawPositionsTable);
     google.setOnLoadCallback(drawErrorsTable);
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
 function drawErrorsTable() {
	  
	  var myObject = <%=coreservlets.Control.SendMessage("GET_ERRORS")%>;
	   	
	  var data = new google.visualization.DataTable(myObject);

   var table = new google.visualization.Table(document.getElementById('table_errors_div'));
   var options = {
 		  width: 400,
 		 
 		  ShowRowNumber:true
 		};
  table.draw(data, options);
 }
 window.onload = function IsConnected() {
	  
	  var APIresponse = "<%=coreservlets.Control.SendMessage("ISCONNECTED")%>";
	  var IMAPresponse = "<%=coreservlets.Control.SendMessage("CHECK_EMAIL_LISTENER")%>";
		document.getElementById("APIStatus").innerText = APIresponse;
		document.getElementById("IMAPStatus").innerText = IMAPresponse;

	
	
		if (APIresponse == "CONNECTED")
			{
			var div = document.getElementById( 'APIStatus' );
		 		 div.style.backgroundColor = 'lightgreen';
			}
		else
			{
			var div = document.getElementById( 'APIStatus' );
	 		 div.style.backgroundColor = 'red';
			
			}
		if (IMAPresponse == "CONNECTED")
		{
		var div = document.getElementById( 'IMAPStatus' );
	 		 div.style.backgroundColor = 'lightgreen';
		}
		else
		{
		var div = document.getElementById( 'IMAPStatus' );
 		 div.style.backgroundColor = 'red';
		
		}
		

	
 };
</script>
</head>

</HEAD>
<BODY>

<table>
<tr>
<td>
API Connection:
</td>
<td>
<div id="APIStatus">      </div>
</td>
</tr>
<tr>
<td>
IMAP connection:
</td>
<td>
<div id="IMAPStatus">      </div>
</td>
</tr>
</table>

	<form action="${pageContext.request.contextPath}/ThreeParams"
		method="post">
		<input type="submit" name="btn" id="runEmailListener" value="RUN_EMAIL_LISTENER" />
	</form>
	<H3>History</H3>
	<div id='table_history_div'></div>
	<H3>Live Orders</H3>
	<div id='table_orders_div'></div>
	<form action="${pageContext.request.contextPath}/ThreeParams"
		method="post">
		<input type="submit" name="btn" value="Cancel_Orders" />
	</form>
	<H3>Open Positions</H3>
	<div id='table_positions_div'></div>
	<br>
	<div id='table_errors_div'></div>
	<H3>Create New Order</H3>
	<form action="${pageContext.request.contextPath}/ThreeParams"
		method="post">
		<table width="100">
			<tr>
				<td>Ticker:</td>
				<td><input type="text" name="ticker"></td>
			</tr>
			<tr>
				<td>Side:</td>
				<td><input type="radio" name="side" value="buy">BUY <input
					type="radio" name="side" value="sell">SELL</td>
			</tr>
			<tr>
				<td>Quantity:</td>
				<td><input type="number" name="quantity"></td>
			</tr>
			<tr>
				<td><input type="submit" name="btn" value="CREATE_ORDER">
				</td>
			</tr>
		</table>

	</form>

	<br>
	<br>
	<br>
	<br>
	<br>
	<br>

</BODY>
</HTML>
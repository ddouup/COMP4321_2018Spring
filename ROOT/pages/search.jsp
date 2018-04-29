<%@page language="java" import="java.util.*" %>
<%@ page import="searchEngine.HelloWorld" %>
<html>
<head>
<title>Search</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Custom Theme files -->
<link href="../css/style.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<!--webfont-->
<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<br>
	<div class="header">	
      <div class="container"> 
  	     <div class="logo">
			<h1><a href="../index.html">Search Engine</a></h1>
			<div class="clearfix"></div>
		 </div>
		 	<div class="top_right">
			      <ul class="nav1">
		            <li id="search">
						<form action="" method="get">
							<input type="text" name="search_text" id="search_text" placeholder="Search"/>
							<input type="button" name="search_button" id="search_button">
						</form>
					</li>
	              </ul>
	     	</div>
		 <div class="clearfix"></div>
		</div>
	</div>
	<div class="register">
		<div class="container">
			<%--Get request parameter--%>
			<%
			String query = request.getParameter("search_text");
			if(query!=null)
			{
				out.println("You input "+query);
				HelloWorld h = new HelloWorld();
			}
			else
			{
				out.println("You input nothing");
			}

			%>
		</div>
	</div>
	<div class="grid_3">
	  <div class="container">
         <p>Copyright &copy;  DOU Daihui, TANG Can-yao, Yang Mingyuan</p>
	  </div>
	</div>
</body>
</html>		
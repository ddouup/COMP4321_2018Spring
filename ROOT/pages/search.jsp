<%@ page language="java" import="java.util.*,searchEngine.*" %>
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
			<%= HelloWorld.getName() %>
			<%--Get request parameter--%>
			<%
			String query = request.getParameter("search_text");
			if(query!=null)
			{
				out.println("You input "+query);
				SearchEngine s = new SearchEngine();
				Vector<PageList> result = s.search(query);
				out.println(result.size());
				for(int i = 0; i < result.size(); i++)
				{	
				%>
				<div>
					<h5>Number: <%= i+1%></h5>
					<h5>Score: <%= result.get(i).score%></h5>
					<h5><%= result.get(i).title%></h5>
					<h5><a href="<%= result.get(i).url%>" target="_blank" style="color:blue;"><%= result.get(i).url%></a></h5>
					<h5><%= result.get(i).key%></h5>
					<h5><%= result.get(i).datesizeofpage%></h5>
					<h5>Parent links: <br><%=result.get(i).parentlink%></h5>
					<h5>Child links: <br><%=result.get(i).childlink%></h5>
				</div>
				<%
				}
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
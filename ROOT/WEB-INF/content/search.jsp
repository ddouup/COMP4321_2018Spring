<%@ page language="java" import="java.util.*, StopStem, SearchEngine" %>
<html>
<head>
<title>Search</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Custom Theme files -->
<link href="css/style.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<!--webfont-->
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<br>
	<div class="header">	
      <div class="container"> 
  	     <div class="logo">
			<h1><a href="index.html">Search Engine</a></h1>
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
			if(request.getParameter("txtname")!=null)
			{	
				out.println("You input "+request.getParameter("txtname"));
				StopStem stopStem = new StopStem("stopwords.txt");
				Vector<String> query = new Vector<String>();
				String contents = request.getParameter("txtname")
				StringTokenizer st = new StringTokenizer(contents);
				while (st.hasMoreTokens()) {
					String word = st.nextToken();
					if (!stopStem.isStopWord(word)){
						word = stopStem.stem(word);
						out.println(word);
						boolean isWord=word.matches("^[A-Za-z0-9]+");
						if(isWord)
						query.add(word);
					}
				}
				SearchEngine searchEngine = new SearchEngine();
				// TODO: search and print result
				/*
				Vector<> result = searchEngine.search(query);
				for (int i = 1; i < result.size(); i++){
					
				}
				*/
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
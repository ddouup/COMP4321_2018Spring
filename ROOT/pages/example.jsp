<%@ page import="java.util.*" %>

<html>
<body>
	<% java.util.Date date = new java.util.Date(); %>

	Hello!  The time is now <%= date %> <br>


	<%! 
	    String print_hour(Date date)
	    {
	        return "It is now "+date.getHours()%12+" o\'clock<BR>" ;
	    }
	%>

	<%=print_hour(date)%>

	<%
	    out.println( "<BR>Your machine's address is " );
	    out.println( request.getRemoteHost());
	%>
	<br>
	
	<%--Get request parameter--%>
	<%
	if(request.getParameter("txtname")!=null)
	{
		out.println("You input "+request.getParameter("txtname"));
	}
	else
	{
		out.println("You input nothing");
	}

	%>
	<br>

	<%--Use of cookies--%>
	<%
	Cookie ck[] = request.getCookies();
	int times = 0;
	if(ck == null)
	{
	     out.println("It is the first time you visit this page");
	}
	else
	{
	     for(int i = 0; i < ck.length; i++)
	     {
		if(ck[i].getName().equals("times"))
			times = Integer.parseInt(ck[i].getValue());
	     }
	     out.println("It is the "+(times+1)+" times you visit this page");
	}

	Cookie newck = new Cookie("times",Integer.toString(times+1));
	newck.setMaxAge(60*60); // 1 hour
	response.addCookie(newck);

	%>
	<br>

	<%--Use of sessions--%>
	<%
	if(session.isNew()){
	session.setMaxInactiveInterval(1800);
	%>

	Hi, what is your name?
	<form method="post">
	<input type="text" name="txtname">
	<input type="submit" value="Submit">
	</form>
	<%
	}else{
		
		if(request.getParameter("txtname")!=null)
		{
			session.setAttribute("name",request.getParameter("txtname"));
		}

		if(session.getAttribute("name")==null)
		{
			session.invalidate();
			out.println("You have not entered your name! Please refresh");
		}
		else
		{
			
			out.println("Hi! "+session.getAttribute("name"));		
		}
		
	}
	%>
</body>
</html>

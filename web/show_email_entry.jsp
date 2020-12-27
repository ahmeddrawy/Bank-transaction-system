<html>
<head>
<title>Email List application</title>
</head>
<body>
<%
//String firstName = request.getParameter("firstName");
HttpSession sessioN = request.getSession();
String firstName = sessioN.getAttribute("firstName").toString();
//firstName = firstName.toUpperCase();
String lastName = sessioN.getAttribute("lastName").toString();
String emailAddress = sessioN.getAttribute("emailAddress").toString();
%>
<h1>Thanks for joining our email list</h1>
<p>Here is the information that you entered:</p>
 
<table cellspacing="5" cellpadding="5" border="1">
<tr>
<td align="right">First name:</td>
<td><% out.println( firstName);%></td>
</tr>
<tr>
<td align="right">Last name:</td>
<td><%= lastName %></td>
</tr>
<tr>
<td align="right">Email address:</td>
<td><%= emailAddress %></td>
</tr>
</table>
 
<p>To enter another email address, click on the Back <br>
button in your browser or the Return button shown <br>
below.</p>
 
<form action="index.html" method="post">
<input type="submit" value="Return">
</form>
</body>
</html>

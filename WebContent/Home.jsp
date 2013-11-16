<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Game"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="GameController" method="post">
		<h1>Games in the Database</h1>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Description</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Status</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			
				<%
				Game[] Items = (Game[]) request.getSession().getAttribute("items");
				for (int i = 0; i < Items.length; i++) {
				%>
				<tr>
	   				<td><label><%= Items[i].getID()%></label></td>
	   				<td><label><%= Items[i].getName()%></label></td>
	   				<td><label><%= Items[i].getDescription()%></label></td>
	   				<td><label><%= Items[i].getPrice()%></label></td>
	   				<td><label><%= Items[i].getQty()%></label></td>
	   				<td><label><%= Items[i].getStatus()%></label></td>
   				 <% if (Items[i].getID() != -1) {  %>
	   				<td><a href="GameEditController?id=<%= Items[i].getID()%>">Edit</a></td>
	   				<td><a href="GameController?option=delete&id=<%= Items[i].getID()%>">Delete</a></td>
				<% } %>
				</tr>
				<% } %>
		</table>
		<a href="AddGame.jsp">Add Game</a>
		<input type="hidden" name="option" value="commit" />
		<input type="submit" value ="Commit" />
	</form>
</body>
</html>
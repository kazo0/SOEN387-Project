<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Cart" import="models.OrderItem"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="CheckoutController" method="post">
		<h1>Cart</h1>
		<table>
			<tr>
				<th>Name</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Delete</th>
			</tr>
			
				<%
				Cart cart = (Cart) request.getSession().getAttribute("cart");
				OrderItem[] Items = cart.getItems();
				for (int i = 0; i < Items.length; i++) {
				%>
				<tr>
	   				<td><label><%= Items[i].getPrice()%></label></td>
	   				<td><label><%= Items[i].getPrice()%></label></td>
	   				<td><input type="text" name="quantity[]" value="<%= Items[i].getQuantity()%>" /></td>
	   				<td><a href="CartController?option=delete&index=<%= i%>">Delete</a></td>
				</tr>
				<% } %>
		</table>
		<input type="submit" value ="Update Cart" />
		<input type="submit" value ="Checkout" />
		
	</form>
</body>
</html>
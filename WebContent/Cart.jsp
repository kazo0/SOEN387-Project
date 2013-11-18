<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Cart" import="models.OrderItem"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Store Cart</title>
<style>
 .button {
    font: bold 11px Arial;
    text-decoration: none;
    background-color: #EEEEEE;
    color: #333333;
    padding: 2px 6px 2px 6px;
    border-top: 1px solid #CCCCCC;
    border-right: 1px solid #333333;
    border-bottom: 1px solid #333333;
    border-left: 1px solid #CCCCCC;
   }
</style>
</head>
<body>
<form action="CartController" method="post">
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
				if (cart != null) {
					OrderItem[] Items = cart.getItems();
					for (int i = 0; i < Items.length; i++) {
					%>
					<tr>
		   				<td><label><%= Items[i].getName()%></label></td>
		   				<td><label><%= Items[i].getPrice()%></label></td>
		   				<td><input type="text" name="Quantity[]" value="<%= Items[i].getQuantity()%>" /></td>
		   				<td><a href="CartController?option=delete&index=<%= i%>">Delete</a></td>
					</tr>
					<% }} %>
					
		</table>
		<p><%= cart.getItemCount() + " " %> Item(S) in the Cart</p>
		<p><label>Total : </label><%= cart.getTotal() %></p>
		<input type="submit" name="update" value ="Update Cart" />
		<input type="submit" name="checkout" value ="Checkout" />
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Game"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Our Store</title>
<style>
ul.products li {
    width: 200px;
    height: 250px;
    display: inline-block;
    vertical-align: top;
    *display: inline;
    *zoom: 1;
    padding: .6em;
}

ul.products {
	background-color: white;
}

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
<ul class="products">
	<%
	Game[] Items = (Game[]) request.getSession().getAttribute("items");
	for (int i = 0; i < Items.length; i++) {
	%>
    <li>
         <img width="100" height="125" src="<%= Items[i].getImage() %>">
         <h4><%= Items[i].getName() %></h4>
         <p>$<%= Items[i].getCategory() %></p>
         <p>$<%= Items[i].getPrice() %></p>
         <p><%= Items[i].getQty() %> Available</p>
         <p><a href="CartController?option=add&gameID=<%= Items[i].getID() %>&price=<%= Items[i].getPrice() %>&name=<%= Items[i].getName() %>" class="button">Add to Cart</a></p>
    </li>
    <% } %>
</ul>
</body>
</html>
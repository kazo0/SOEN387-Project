<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Game" import="models.Cart" import="models.Categories"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"><head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<title>Home - 387 Team5</title>

<!-- ////////////////////////////////// -->
<!-- //      Start Stylesheets       // -->
<!-- ////////////////////////////////// -->
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!-- ////////////////////////////////// -->
<!-- //      Javascript Files        // -->
<!-- ////////////////////////////////// -->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/easySlider1.5.js"></script>
<script type="text/javascript">
	$(document).ready(function(){	
		$("#slider").easySlider({
			auto: true,
			continuous: true 
		});
	});	
</script>

</head>
<body>
	
	<div id="container">
		<!-- centercolumn -->
		<div id="centercolumn">
		
			<div id="top">
				<div id="logo"><a href="HomeController"><img src="images/logo.gif" alt="" /></a></div><!-- end logo -->
				<div id="topmenu">
					<ul>
						<li><a href="#">LOGOUT</a></li>
					</ul>
				</div>
				<div id="topsearch">
				<form method="post" action="HomeController">
					<input type="hidden" name="option" value="search"/>
					<p><span class="bg_input"><input type="text" name="search" class="inputbox" /></span></p>
				</form>
				</div>
			</div>
			
			<div id="slides_container">
				<div class="clr"></div>
				<div id="introText">
					<h1>Product Title</h1>
					<p>This website is a project for Concordia SOEN387 in collaboration with Game Zone, a video game retailer in Montreal. With its agreement, we used it as a sample case in our course. We have implemented an online video game store where a user may browse and purchase video games and where an admin can manager said orders. </p>
					<!-- <p style="text-align:right"><img src="images/but_details.png" alt="" /></p> -->
				</div>
				<div id="content_slider">
					<div id="slider">
						<ul>
							<li><img src="images/stickman.png" alt="" /></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div id="menu">
				<div id="mainmenu">
					<ul>
						<li><a href="HomeController" class="active">Home</a></li>
						<li><a href="Cart.jsp">Checkout</a></li>
						<li><a href="OrderController?option=my">My Orders</a></li>
						<% if ((Boolean)request.getSession().getAttribute("isAdmin") == true)
							{
								%>
								<li><a href="InventoryController">Manage Inventory</a></li>
								<li><a href="OrderController?option=manage">Manage Orders</a></li>
								<%
							}%>
					</ul>
				</div>
				<div id="topcart">
					<% Cart Cart = (Cart) request.getSession().getAttribute("cart");	%>
					<a href="Cart.jsp"><img src="images/icon_cart.png" alt="" /></a>&nbsp;now in your cart <strong><%= Cart == null? 0 + " " : Cart.getItemCount() + " "%> items</strong>	
				</div>
				
			</div>
			

			
			<div id="content">
				<div id="content_left">
			
					<div id="maincontent">
					
						<div class="mainbox">
						<div class="mainbox_linetop">
						<div class="mainbox_lineleft">
						<div class="mainbox_lineright">
							<div class="maincurve_tl">
								<div class="maincurve_tr">
									<div class="maincurve_bl">
									<div class="maincurve_br">
										<div class="padbox">
										<div style="margin:0 auto; width:695px;">
										
										
										<%
										Game[] Items = (Game[]) request.getSession().getAttribute("games");
										for (int i = 0; i < Items.length; i++) {%>
									<div id="menu">
				<div id="mainmenu">
					<ul>
						<li><a href="HomeController" class="active">Home</a></li>
						<li><a href="Cart.jsp">Checkout</a></li>
						<li><a href="OrderController?option=my">My Orders</a></li>
						<% if ((Boolean)request.getSession().getAttribute("isAdmin") == true)
							{
								%>
								<li><a href="InventoryController">Manage Inventory</a></li>
								<li><a href="OrderController?option=manage">Manage Orders</a></li>
								<%
							}%>
					</ul>
				</div>
				<div id="topcart">
					<% Cart = (Cart) request.getSession().getAttribute("cart");	%>
					<a href="Cart.jsp"><img src="images/icon_cart.png" alt="" /></a>&nbsp;now in your cart <strong><%= Cart == null? 0 + " " : Cart.getItemCount() + " "%> items</strong>	
				</div>
				
			</div>
										<%
											// Even Item
											if (i % 2 == 0)
											{
												if (i == Items.length -1)
												{
													// If it's a last right item;
													%><div class="product"><%
												}
												else if (i == Items.length -2) 
												{
													// If it's the last left item
													%><div class="product borderright"><%
												}
												else 
												// If its ODD
												%><div class="product borderright borderbottom"><%
											}
											// Odd Item
											else {
												if (i == Items.length -1)
												{
													// If it's a last left item;
													%><div class="product"><%
												}
												else 
												// If its Even
												%><div class="product borderbottom"><%
											}
											%>
											<h4 class="title_product"><%= Items[i].getName()%></h4>
											<p class="category_small"><%= Items[i].getCategory() %></p>
											<p><a href="product_description.html"><img width="141" height="129" src="<%= Items[i].getImage()==null? "images/notfound.jpg" :  Items[i].getImage()%>" alt="" class="imgleft" /></a></p>
											<p class="price_small">$<%= Items[i].getPrice() %></p>
											<p><a href="DetailsController?gameID=<%= i %>"><img src="images/but_details.png" alt="" /></a>&nbsp;&nbsp;</p>
										</div>
										<%} %>
										<div class="clr"></div>
										</div>
										</div>
									</div>
									</div>
								</div>
							</div>
						</div>
						</div>
						</div>
						</div>
						
					</div>
				</div>
				<div id="content_right">
					<div class="sidebox">
						<div class="sidebox_repeat">
						<div class="sidebox_t">
						<div class="sidebox_b">
							<div class="padbox_side">
								<h3>Categories</h3>
								<ul>
									<%
										String[]  cat = new Categories().Categories;
										for(String category : cat) {
									%>
										<li><a href="HomeController?category=<%=category %>"><%= category %></a></li>
									<% } %>	
								</ul>
							</div>
						</div>
						</div>
						</div>
					</div>

				</div>
				<div class="clr"></div>
			</div>
			
		</div>
	</div>
	
	<div id="bottom_container">
		<div id="footer">
			<div id="footleft">
				Copyright &copy;2013. All rights reserved
			</div>
			
		</div>
	</div>
	
</body>
</html>
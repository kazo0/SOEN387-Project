<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Order" import="models.OrderItem"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Orders - 387 Team5</title>

<!-- ////////////////////////////////// -->
<!-- //      Start Stylesheets       // -->
<!-- ////////////////////////////////// -->
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/inner.css" rel="stylesheet" type="text/css" />
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
		<div id="centercolumn">
			<div id="top">
				<div id="logo"><a href="HomeController"><img src="images/logo.gif" alt="" /></a></div>
				<div id="topmenu">
					<ul>
						<li><a href="LogoutController">LOGOUT</a></li>
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
					<h1>Project Description</h1>
					<p>This website is a project for Concordia SOEN387 in collaboration with Game Zone, a video game retailer in Montreal. With its agreement, we used it as a sample case in our course. We have implemented an online video game store where a user may browse and purchase video games and where an admin can manager said orders. </p>
				</div><!-- end introTex -->
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
						<li><a href="HomeController">Home</a></li>
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
				
			</div>
			<div id="content">
				<div id="content_left">
					
					<div id="maincontent">
					
						<div class="mainbox large">
						<div class="mainbox_linetop">
						<div class="mainbox_lineleft">
						<div class="mainbox_lineright">
							<div class="maincurve_tl">
								<div class="maincurve_tr">
									<div class="maincurve_bl">
									<div class="maincurve_br">
										<div class="padbox">
											<div id="breadcrumb"><a href="HomeController">Home</a>  &gt;  <a href="InventoryController">Inventory</a></div>
											<br />
											<h3>Orders</h3>
											<% Order[] orders = (Order[])request.getSession().getAttribute("userOrders");
											
											for (int i = 0; i < orders.length; i++) 
											{
												%>	
													<div class="box_large">
															<div class="box_large_b">
																<div class="box_large_t">
																	<div class="padbox_small">
																	<div class="headings">
																		<ul>
																			<li class="priview">Order Items</li>
																			<li class="quantity">Quantity</li>
																			<li class="priceHead">Price</li>
																			<li class="status">Total</li>		
																		</ul>
																		<div class="clr"></div>
																	</div>
																	
																	<%
																	for(OrderItem oi : orders[i].getOrderedGames())
																	{ %>
																	
																	<div class="proList">
																		<ul>
																			<li class="priview"><%= oi.getName() %></li>
																			<li class="quantity"><%= oi.getQuantity() %></li>
																			<li class="priceHead"><%= oi.getPrice()%></li>	
																			<li class="total">$<%= oi.getPrice() *  oi.getQuantity()%></li>			
																		</ul>
																		<div class="clr"></div>
																	</div>
																	<br />
																	<% } %>
																	<p><label>Total : </label><%= orders[i].getTotal() %></p>
																	<% String status = (String)orders[i].getOrderStatus();
																	String color = "green";
																	if (status.equals("Shipped"))
																	{
																		color = "navy";
																	}
																	else if (status.equals("Processing"))
																	{
																		color = "orange";
																	}
																		%>
																	<p>Status : <label style="color:<%=color%>;font-weight:bold"><%= status %></label></p>
																	</div>
																</div>
															</div>
													</div>
											<% } %>
											<div class="clr"></div>
											<br /><br />
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
				<div class="clr"></div>
			</div>
			
		</div>
		
	</div>

	<div id="bottom_container">
		<div id="footer">
			<div id="footleft">
			</div>
		</div>
	</div>
	
</body>
</html>

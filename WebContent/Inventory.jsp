<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Game"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Inventory - 387 Team5</title>

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
								<li><a href="InventoryController" class="active">Manage Inventory</a></li>
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
											<h3>Inventory</h3>
											<div class="box_large">
													<div class="box_large_b">
														<div class="box_large_t">
															<div class="padbox_small">
															<div class="headings">
																<ul>
																	<li class="priview">Name</li>
																	<li class="priceHead">Price</li>
																	<li class="quantity">Qty</li>
																	<li class="status">Status</li>
																	<li class="edit">Edit</li>
																	<li class="remove">Remove</li>
																</ul>
																<div class="clr"></div>
															</div>
														
															<form action="GameController" method="post" >
															<%
																Game[] Items = (Game[]) request.getSession().getAttribute("items");
																for (int i = 0; i < Items.length; i++) {
																%>
																	<div class="proList">
																		<ul>
																			<li class="priview"><%= Items[i].getName() %></li>
																			<li class="priceHead">$<%=Items[i].getPrice() %></li>
																			<li class="quantity"><%= Items[i].getQty() %></li>
																			<li class="status"><%= Items[i].getStatus() %></li>
																			<% if (Items[i].getID() != -1) {  %>
																				<li class=edit>&nbsp;<a href="GameEditController?id=<%= Items[i].getID()%>">Edit</a></li>
																				<li class="remove">&nbsp;<a href="GameController?option=delete&id=<%= Items[i].getID()%>">Remove</a></li>
																			<%} %>
																		</ul>
																		<div class="clr"></div>
																	</div>
																<% } %>
		
															<br />
																
																<input type="submit" name="add" value ="Add Game" />
																<input type="hidden" name="option" value="commit" />
																<input type="submit" value ="Commit" />
															</form>
															</div>
														</div>
													</div>
											</div>
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

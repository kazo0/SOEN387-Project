<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Game" import="models.Cart" import="models.Categories"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"><head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <meta name="robots" content="index, follow" />
  <meta name="keywords" content="" />
  <meta name="title" content="" />
  <meta name="description" content="" />
<title>Shopping Cart - 387 Team5</title>

<!-- ////////////////////////////////// -->
<!-- //      Start Stylesheets       // -->
<!-- ////////////////////////////////// -->
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
    <script type="text/javascript" src="js/unitpngfix.js"></script>
	<link href="css/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
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
	
	<!-- BEGIN CONTAINER -->
	<div id="container">
		<!-- centercolumn -->
		<div id="centercolumn">
		
			<!-- BEGIN HEADER -->
			<div id="top">
				<div id="logo"><a href="HomeController"><img src="images/logo.gif" alt="" /></a></div><!-- end logo -->
				<div id="topmenu">
					<ul>
						<li><a href="#">LOGIN</a></li>
					</ul>
				</div><!-- end topmenu -->
				<div id="topsearch">
				<form method="post" action="HomeController">
					<p><span class="bg_input"><input type="text" name="search" class="inputbox" /></span></p>
				</form>
				</div><!-- end topsearch -->
			</div>
			
			<div id="slides_container">
				<div class="clr"></div>
				<div id="introText">
					<h1>Product Title</h1>
					<p>This website is a project for Concordia SOEN387 in collaboration with Game Zone, a video game retailer in Montreal. With its agreement, we used it as a sample case in our course. We have implemented an online video game store where a user may browse and purchase video games and where an admin can manager said orders. </p>
					<p style="text-align:right"><a href="product_description.html"><img src="images/but_details.png" alt="" /></a></p>
				</div><!-- end introTex -->
				<div id="content_slider">
					<div id="slider">
						<ul>
							<li><img src="images/stickman.png" alt="" /></li>
						</ul>
					</div><!-- end slider -->
				</div><!-- end content_slider -->
			</div><!-- end slides_container -->
			
			<div id="menu">
				<div id="mainmenu">
					<ul>
						<li><a href="HomeController" class="active">Home</a></li>
						<li><a href="Cart.jsp">Checkout</a></li>
					</ul>
				</div><!-- end mainmenu -->
				<div id="topcart">
					<% Cart Cart = (Cart) request.getSession().getAttribute("cart");	%>
					<a href="Cart.jsp"><img src="images/icon_cart.png" alt="" /></a>&nbsp;now in your cart <strong><%= Cart == null? 0 + " " : Cart.getItemCount() + " "%> items</strong>	
				</div>
				<!-- end topcart -->
			</div><!-- end menu -->
			
			<!-- END OF HEADER -->
			
			<!-- BEGIN CONTENT -->
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
										Game[] Items = (Game[]) request.getSession().getAttribute("items");
										for (int i = 0; i < Items.length; i++) {
 
										// If the Item is the Last Row Right
										if (i == Items.length -1) {%>
							 			<div class="product">
											<h4 class="title_product"><%= Items[i].getName()%></h4>
											<p><a href="product_description.html"><img width="141" height="129" src="<%= Items[i].getImage()==null? "images/notfound.jpg" :  Items[i].getImage()%>" alt="" class="imgleft" /></a></p>
											<p>Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae.</p>
											<p class="price_small">$<%= Items[i].getPrice() %></p>
											<p><a href="product_description.html"><img src="images/but_details.png" alt="" /></a>&nbsp;&nbsp;<a href="CartController?option=add&gameID=<%= Items[i].getID() %>&price=<%= Items[i].getPrice() %>&name=<%= Items[i].getName() %>"><img src="images/but_addtocart.png" alt="" /></a></p>
										</div>
										 <% continue ; }
										// If the Item is the Last Row Left
										else if (i == Items.length -2) {%>
										<br style="clear:both" />
										<div class="product borderright" >
											<h4 class="title_product"><%= Items[i].getName()%></h4>
											<p><a href="product_description.html"><img width="141" height="129" src="<%= Items[i].getImage()==null? "images/notfound.jpg" :  Items[i].getImage()%>" alt="" class="imgleft" /></a></p>
											<p>Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae.</p>
											<p class="price_small">$<%= Items[i].getPrice() %></p>
											<p><a href="product_description.html"><img src="images/but_details.png" alt="" /></a>&nbsp;&nbsp;<a href="CartController?option=add&gameID=<%= Items[i].getID() %>&price=<%= Items[i].getPrice() %>&name=<%= Items[i].getName() %>"><img src="images/but_addtocart.png" alt="" /></a></p>
										</div>	
										 <%continue; } %>
										<%
										// If the Item is Even
										if (i % 2 == 0) { %>
										<div class="product borderright borderbottom">
											<h4 class="title_product"><%= Items[i].getName()%></h4>
											<p><a href="product_description.html"><img width="141" height="129" src="<%= Items[i].getImage()==null? "images/notfound.jpg" :  Items[i].getImage()%>" alt="" class="imgleft" /></a></p>
											<p>Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae.</p>
											<p class="price_small">$<%= Items[i].getPrice() %></p>
											<p><a href="product_description.html"><img src="images/but_details.png" alt="" /></a>&nbsp;&nbsp;<a href="CartController?option=add&gameID=<%= Items[i].getID() %>&price=<%= Items[i].getPrice() %>&name=<%= Items[i].getName() %>"><img src="images/but_addtocart.png" alt="" /></a></p>
										</div>
										<% }
										// If the Item is Odd
										else { %>
											<div class="product borderbottom">
											<h4 class="title_product"><%= Items[i].getName()%></h4>
											<p><a href="product_description.html"><img width="141" height="129" src="<%= Items[i].getImage()==null? "images/notfound.jpg" :  Items[i].getImage()%>" alt="" class="imgleft" /></a></p>
											<p>Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae.</p>
											<p class="price_small">$<%= Items[i].getPrice() %></p>
											<p><a href="product_description.html"><img src="images/but_details.png" alt="" /></a>&nbsp;&nbsp;<a href="CartController?option=add&gameID=<%= Items[i].getID() %>&price=<%= Items[i].getPrice() %>&name=<%= Items[i].getName() %>"><img src="images/but_addtocart.png" alt="" /></a></p>
										</div>
										
										<%} %>
									 
									    <% } %>
								
										<div class="clr"></div><!-- clear float -->
										</div>
										</div>
									</div>
									</div>
								</div>
							</div>
						</div>
						</div>
						</div>
						</div><!-- end mainbox -->
						
					</div><!-- end maincontent -->
				</div><!-- end content_left -->
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
					</div><!-- end sidebox -->

				</div><!-- end content_right -->
				<div class="clr"></div><!-- clear float -->
			</div><!-- end content -->
			<!-- END OF CONTENT -->
			
		</div>
		<!-- end centercolumn -->
	</div>
	<!-- END OF CONTAINER -->
	
	<!-- BEGIN FOOTER -->
	<div id="bottom_container">
		<div id="footer">
			<div id="footleft">
				Copyright &copy;2013. All rights reserved
			</div><!-- end footleft -->
			
		</div>
	</div>
	<!-- END OF FOOTER -->
	
</body>
</html>
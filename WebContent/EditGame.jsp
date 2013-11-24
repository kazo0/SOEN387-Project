<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="models.Game" import="models.Categories"%>
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
						<li><a href="InventoryController" class="active">Inventory</a></li>
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
											<div id="breadcrumb"><a href="HomeController">Home</a>  &gt;  <a href="InventoryController">Inventory</a> &gt; <a href="#">Edit Game</a> </div>
											<br />
											<h3>Edit Game</h3>
												
												<%
													Game gm = (Game) request.getSession().getAttribute("game");
												%>
												
												<form action="GameController" method="post">
														<table>
															<tr>
																<td><label>Name :</label></td>
																<td><input name="Name" type="text" value="<%= gm.getName() %>"/> </td>
															</tr>
															<tr>
																<td><label>Description :</label></td>
																<td><input name="Description" type="text" value="<%= gm.getDescription() %>" /> </td>
															</tr>
															<tr>
																<td><label>Price :</label></td>
																<td><input name="Price" type="text" value="<%= gm.getPrice() %>" /> </td>
															</tr>
															<tr>
																<td><label>Quantity :</label></td>
																<td><input name="Quantity" type="text" value="<%= gm.getQty() %>" /> </td>
															</tr>
															<tr>
																<td><label>Image :</label></td>
																<td><input name="Image" type="text" value="<%= gm.getImage() %>" /> </td>
															</tr>
															<tr>
																<td><label>Category :</label></td>
																<td>
																	<select name="Category">
																		<% String[] cat = new Categories().Categories;%>
																		<% for(int i = 0; i < cat.length; i+=1) { %>
																			<% String selected = gm.getCategory().equals(cat[i])? "selected" : ""; %>
																			<option value="<%= cat[i] %>" <%= selected %> ><%= cat[i]%></option>
												   				 		<% } %>
																	</select>
																</td>
															</tr>
														</table>
														<input type="hidden" name="id" value="<%= gm.getID() %>" />
														<input type="hidden" name="option" value="edit"/>
														
														<input type="submit" value="edit" />
												</form>
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
				Copyright &copy;2013. All rights reserved
			</div>
		</div>
	</div>
	
</body>
</html>
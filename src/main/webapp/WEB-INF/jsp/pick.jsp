<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- Access the bootstrap Css like this, 
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!-- 
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />
	<link href="../resources/css/b2b2c.css" rel="stylesheet">
	<link href="../webjars/bootstrap/3.3.6/css/bootstrap.min.css"
		rel="stylesheet">
	<link rel="stylesheet"
		href="../webjars/bootstrap-social/4.12.0/bootstrap-social.css">
	<link href="../webjars/font-awesome/4.6.1/css/font-awesome.css"
		rel="stylesheet">
		
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
	
	<link rel="stylesheet" href="jquery.rating.css">
    <script src="jquery.js"></script>
    <script src="jquery.rating.js"></script>
	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oswald">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open Sans">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<style>
	h1,h2,h3,h4,h5,h6 {font-family: "Oswald"}
	body {font-family: "Open Sans"}
	#limit {max-width: 500px;}
	/* The Modal (background) */
	.modal {
	    display: none; /* Hidden by default */
	    position: fixed; /* Stay in place */
	    z-index: 1; /* Sit on top */
	    padding-top: 100px; /* Location of the box */
	    left: 0;
	    top: 0;
	    width: 100%; /* Full width */
	    height: 100%; /* Full height */
	    overflow: auto; /* Enable scroll if needed */
	    background-color: rgb(0,0,0); /* Fallback color */
	    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
	}
	
	/* Modal Content */
	.modal-content {
	    background-color: #fefefe;
	    margin: auto;
	    padding: 20px;
	    border: 1px solid #888;
	    width: 80%;
	}
	
	/* The Close Button */
	.close {
	    color: #aaaaaa;
	    float: right;
	    font-size: 28px;
	    font-weight: bold;
	}
	
	.close:hover,
	.close:focus {
	    color: #000;
	    text-decoration: none;
	    cursor: pointer;
	}
	</style>
</head>



<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="https://www.imdb.com">IMDB</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="/">Home</a></li>
					<li><a href="/about">About</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div id="myModal" class="modal">
	
	  <div class="panel-heading text-center panel-primary">
			<h2>Sign In or Register</h2>
		</div>

	<div class="w3-container w3-white w3-light-grey">
	<div class="table-responsive">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">
			<h2>Login</h2>
		</div>
		
		<form action="/login" method="POST">
			<table class="table table-striped">
				<tr>
					<td><h4>Email:</h4></td>
					<td><input type="email" value="" name="lemail" /></td>
					<td><h4>Password:</h4></td>
					<td><input type="password" value="" name="lpassword" /></td>
					<td ><input id="signIn" type="submit" value='Sign In' class="btn btn-primary" /></td>
				</tr>
				
			</table>
		</form>
		<label class="error">${fail}${sucess}</label>
	</div>
	</div>
	
	
	<div class="table-responsive">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">
			<h2>Register</h2>
		</div>

		<form method="POST" action="/register" action="../user/register">
			<table class="table table-striped">
				<tr>
					<td><h4>Username:</h4></td>
					<td><input type="text" value="" name="username" /></td>
				</tr>
				<tr>
					<td><h4>Email:</h4></td>
					<td><input type="email" value="" name="email" /></td>
				</tr>
				
				<tr>
					<td><h4>Password</h4></td>
					<td><input type="password" name="password" /></td>
				</tr>
				
				<tr>
					<td><h4>Confirm Password</h4></td>
					<td><input type="password" name="confirmPassword" /></td>
				</tr>
				
				<tr>
					<td><input type="submit" value='Register' class="btn btn-warning" /></td>
				</tr>
			</table>
		</form>
	</div>
	</div>
	</div>
	
	</div>
	<div class="w3-container w3-white w3-light-grey">
		<h1>${pick.title}</h1>
		<table class="table table-striped">
			<tr>
				<td><img height="300" width="250" src="https://image.tmdb.org/t/p/w500/${pick.posterPath}"/> </td>
				<td>
					<form action="/pick-${pick.id}" method="POST">
						<h5>Rating:</h5>
						<input style="width: 70px;" class="w3-input w3-border" type="number" name="rating" min="0" max="5" step="1" value="5">
						<br>
				        <h5>Comment:</h5> 
				        <input class="w3-input w3-border" type="text" name="comment">
				        <br>
					   	<!-- <input type="submit" value="Submit" /> -->
					   	
						   	<c:if test="${not empty loggedInUser}">
							   	  <p><button type="submit" value="Submit" class="w3-button w3-block w3-cyan">Send</button></p>
							</c:if>
							
						
					    <%-- <c:catch var="exception"> 
						    ${loggedInUser.name} 
						    <p><button type="submit" value="Submit" class="w3-button w3-block w3-cyan">Send</button></p>
					    </c:catch>
						<c:if test="${not empty exception}">
							<td><button class="w3-button w3-block w3-cyan" type="submit" value="Submit">Login/Register to Submit</button></td>
						</c:if> --%>
				  	</form> 
				  	
						   	<c:if test="${empty loggedInUser}">
								<p><button onclick="form.action='SecondServlet';" id="myBtn" class="w3-button w3-block w3-cyan" type="submit" value="Submit">Login/Register to Submit</button></p>
							</c:if>
							
					
				</td>
			</tr>
			<tr>
				<td><h2>Description: </h2></td>
				<td><h3>${pick.description}</h3></td>
			</tr>
			<tr>
				<td><h2>Release Date: </h2></td>
				<td><h3>${pick.date}</h3></td>
			</tr>
			<tr>
				<td><h2>Movie Rating: </h2></td>
				<td><h3>${pick.rating}</h3></td>
			</tr>
			<tr>
				<td><h2>Comments: </h2></td>
				<td>
					<table class="table">
						<c:forEach items="${comments}" var="comment">
									<tr>
										<td><div><p style="color:#32b9d1;">${comment.user}:</p><h5>${comment.comment}</h5></div><font size = "1">${comment.timestamp}</font></td>
									</tr>
									<%-- <tr>
										<td><font size = "1">${comment.timestamp}</font><br /></td>
									</tr> --%>
						</c:forEach>
					</table>
				</td>
			</tr>
			
		</table>
	</div>
	
	<script>
	// Get the modal
	var modal = document.getElementById('myModal');
	
	// Get the button that opens the modal
	var btn = document.getElementById("myBtn");
	
	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];
	
	// When the user clicks the button, open the modal 
	btn.onclick = function() {
	    modal.style.display = "block";
	}
	
	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
	    modal.style.display = "none";
	}
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
	</script>
	
</body>

<footer class="w3-container w3-dark-grey" style="padding:32px">
  <p>Built by <a href="https://github.com/risheeks" target="_blank">Risheek</a>, <a href="https://github.com/sidBOT" target="_blank">Siddhant</a> and <a href="https://github.com/mehta105" target="_blank">Ayush</a></p>
</footer>

</html>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- Access the bootstrap Css like this, 
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link href="../resources/css/b2b2c.css" rel="stylesheet">
	<link href="../webjars/bootstrap/3.3.6/css/bootstrap.min.css"
		rel="stylesheet">
	<link rel="stylesheet"
		href="../webjars/bootstrap-social/4.12.0/bootstrap-social.css">
	<link href="../webjars/font-awesome/4.6.1/css/font-awesome.css"
		rel="stylesheet">
		
	<title>Soggy Apples</title>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oswald">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open Sans">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<!-- <div class="navbar-header">
				<a class="navbar-brand" href="https://www.imdb.com">IMDB</a>
			</div> -->
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<c:if test="${not empty loggedInUser}">
						<li style="margin-left:1500;" ><a>Welcome ${loggedInUser.username}</a></li>
					</c:if>
					<c:if test="${empty loggedInUser}">
						<li><a class="navbar-brand" href="https://www.imdb.com">IMDB</a></li>
					</c:if>
					<li><a href="/">Home</a></li>
					<li><a href="/about">About</a></li>
					<c:if test="${not empty loggedInUser}">
						<li><a href="/logout">Logout</a></li>
					</c:if>
					
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div>
			<div class="starter-template">
				<h1 class="w3-xxxlarge"><b>SOGGY APPLES</b></h1>
				<h2>Search: "${title}"</h2>
				<br>
				<br>
			</div>
			<div class="w3-container w3-white w3-display-topright w3-light-grey">
				<br>
				<br>
				<br>
			  	<div class="w3-container w3-padding w3-black">
			          <h5>Enter the name of a movie below</h5>
			  	</div>
			  	<form action="/api" method="POST">
				   	
				   	<p><input class="w3-input w3-border" type="text" name="title" placeholder="Enter movie" ></p>
				   		<!-- <input type="submit" value="Submit" /> -->
				    <p><button type="submit" value="Submit" class="w3-button w3-block w3-cyan">Find Movie!</button></p>
			  	</form> 
			  </div>
		</div>
	</div>
	
	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<div class="w3-container w3-white w3-light-grey">
		<table class="table table-striped">
			<tr>
					<th>Poster</th>
					<th>Title</th>
					<th>Description</th>
			</tr>
			<c:forEach items="${movies}" var="movie">
						<c id="movies">
						
						<tr>
							<td><a href="/pick-${movie.id}" ><img height="100" width="100" src="https://image.tmdb.org/t/p/w500/${movie.posterPath}"/></a></td>
							<td><a href="/pick-${movie.id}" >${movie.title}</a></td>
							<td>${movie.description}</td>
						</tr>
						</c>
			</c:forEach>
		</table>
	</div>
	
</body>

<footer class="w3-container w3-dark-grey" style="padding:32px">
  <p>Built by <a href="https://github.com/risheeks" target="_blank">Risheek</a>, <a href="https://github.com/sidBOT" target="_blank">Siddhant</a> and <a href="https://github.com/mehta105" target="_blank">Ayush</a></p>
</footer>

</html>

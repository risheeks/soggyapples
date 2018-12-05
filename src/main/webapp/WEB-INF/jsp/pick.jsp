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
					<li><a class="active" >About</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<h1>${pick.title}</h1>
	<img height="300" width="250" src="https://image.tmdb.org/t/p/w500/${pick.posterPath}"/>
	<h2>Description: ${pick.description}</h2>
	<h2>Release Date: ${pick.date}</h2>
	
	<form action="/pick-${pick.id}" method="POST">
		
	   	<p><input class="w3-input w3-border" type="text" name="title" placeholder="Enter movie" ></p>
	   
	   		<!-- <input type="submit" value="Submit" /> -->
	    <p><button type="submit" value="Submit" class="w3-button w3-block w3-cyan">Find Movie!</button></p>
  	</form> 
</body>

<footer class="w3-container w3-dark-grey" style="padding:32px">
  <p>Built by <a href="https://github.com/risheeks" target="_blank">Risheek</a>, <a href="https://github.com/sidBOT" target="_blank">Siddhant</a> and <a href="https://github.com/mehta105" target="_blank">Ayush</a></p>
</footer>

</html>
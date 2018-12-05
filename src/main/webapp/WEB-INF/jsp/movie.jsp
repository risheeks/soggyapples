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

	<div class="container">

		<div class="starter-template">
			<h1 class="w3-xxxlarge"><b>SOGGY APPLES</b></h1>
			<h2>Search: "${title}"</h2>
		</div>

	</div>
	
	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<table class="table table-striped">
		<tr>
				<th>Poster</th>
				<th>Title</th>
				<th>Description</th>
		</tr>
		<c:forEach items="${movies}" var="movie">
					<c id="products">
					
					<tr>
						<td><a href="/pick-${movie.title}" ><img height="100" width="100" src="https://image.tmdb.org/t/p/w500/${movie.posterPath}"/></a></td>
						<td><a href="/pick-${movie.title}" >${movie.title}</a></td>
						<td>${movie.description}</td>
					</tr>
					</c>
		</c:forEach>
	</table>

</body>

<footer class="w3-container w3-dark-grey" style="padding:32px">
  <p>Built by <a href="https://github.com/risheeks" target="_blank">Risheek</a>, <a href="https://github.com/sidBOT" target="_blank">Siddhant</a> and <a href="https://github.com/mehta105" target="_blank">Ayush</a></p>
</footer>

</html>

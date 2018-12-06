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
					<li><a href="/about">About</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<div class="w3-container w3-white w3-light-grey">
		<h1>Development Team</h1>
		<table class="table table-striped">
			<tr>
					<th></th>
					<th>Names</th>
					<th>Contact</th>
			</tr>
			<tr>
				<td><h3>1)</h3></td>
				<td><h3>Risheek Narayanadevarakere</h3></td>
				<td><h3>naraya15@purdue.edu</h3></td>
			</tr>
			<tr>
				<td><h3>3)</h3></td>
				<td><h3>Ayush Mehta</h3></td>
				<td><h3>mehta105@purdue.edu</h3></td>
			</tr>
			<tr>
				<td><h3>2)</h3></td>
				<td><h3>Siddhant Patel</h3></td>
				<td><h3>patel716@purdue.edu</h3></td>
			</tr>
			
		</table>
	</div>

</body>

<footer class="w3-container w3-dark-grey" style="padding:32px">
  <p>Built by <a href="https://github.com/risheeks" target="_blank">Risheek</a>, <a href="https://github.com/sidBOT" target="_blank">Siddhant</a> and <a href="https://github.com/mehta105" target="_blank">Ayush</a></p>
</footer>

</html>

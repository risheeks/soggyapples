<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
h1,h2,h3,h4,h5,h6 {font-family: "Oswald"}
body {font-family: "Open Sans"}
#limit {max-width: 500px;}
.mySlides {display:none;
			display: block;
    margin-left: auto;
    margin-right: auto;
    width: 50%;
}
.w3-left, .w3-right, .w3-badge {cursor:pointer}
.w3-badge {height:13px;width:13px;padding:0}
</style>

<div class="w3-bar w3-black w3-hide-small">
  <a href="http://www.imdb.com" class="w3-bar-item w3-button"><i>IMDB</i></a>
</div>
  

<!-- w3-content defines a container for fixed size centered content, 
and is wrapped around the whole page content, except for the footer in this example -->
<div class="w3-content" style="max-width:1200px">

  <!-- Header -->
  <header class="w3-container w3-center w3-padding-48 w3-light-grey">
    <h1 class="w3-xxxlarge"><b>SOGGY APPLES</b></h1>
    <h6>Welcome to the website where you can <span class="w3-tag">rate and comment on movies!</span></h6>
  </header>
  
  <div class="w3-container w3-white w3-center w3-light-grey">

  	<div class="w3-container w3-padding w3-black">
          <h5>Enter the name of a movie below</h5>
  	</div>
  	<form action="/api" method="POST">
	   	<p><input class="w3-input w3-border" type="text" name="title" placeholder="Enter movie" ></p>
	   

	   		<!-- <input type="submit" value="Submit" /> -->
	    <p><button type="submit" value="Submit" class="w3-button w3-block w3-cyan">Find Movie!</button></p>
  	</form> 
  </div>
  

  	<c:forEach items="${movies}" var="movie">
		<a href="/pick-${movie.title}" ><img class="mySlides" height="300" width="250" src="https://image.tmdb.org/t/p/w500/${movie.posterPath}"></a>			
	</c:forEach>
  

 

  <div class="w3-center w3-container w3-section w3-text-white w3-display-bottommiddle" style="width:30%">
    <div class="w3-left w3-hover-text-khaki" onclick="plusDivs(-1)">&#10094;</div>
    <div class="w3-right w3-hover-text-khaki" onclick="plusDivs(1)">&#10095;</div>
    <!-- <span class="w3-badge demo w3-border w3-transparent w3-hover-white" onclick="currentDiv(1)"></span>
    <span class="w3-badge demo w3-border w3-transparent w3-hover-white" onclick="currentDiv(2)"></span>
    <span class="w3-badge demo w3-border w3-transparent w3-hover-white" onclick="currentDiv(3)"></span> -->
    <c:forEach items="${movies}" var="movie" varStatus="loop">
    	    <span class="w3-badge demo w3-border w3-transparent w3-hover-white" onclick="currentDiv([loop.count])"></span>
	</c:forEach>
  </div>
  

<!-- Footer -->
<footer class="w3-container w3-dark-grey" style="padding:32px">
  <p>Built by <a href="https://github.com/risheeks" target="_blank">Risheek</a>, <a href="https://github.com/sidBOT" target="_blank">Siddhant</a> and <a href="https://github.com/mehta105" target="_blank">Ayush</a></p>
</footer>
<script>
var slideIndex = 1;
showDivs(slideIndex);

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function currentDiv(n) {
  showDivs(slideIndex = n);
}

function showDivs(n) {
  var i;
  var x = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("demo");
  if (n > x.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = x.length}
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";  
  }
  for (i = 0; i < dots.length; i++) {
     dots[i].className = dots[i].className.replace(" w3-white", "");
  }
  x[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " w3-white";
}
</script>
</body>
</html>

    <%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<title>Жми кнопку, бич!</title>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/basic.js"></script>
<link rel="stylesheet" href="css/basic.css" type="text/css" media="screen"/>
</head>

<body>
	<p class="Large">Введите id дилеммы</p>
	<form id="updateDilemma">
		<label for="id">ID: </label>
		<input type="text" id="id" size="40" name="id"/>
		<input type="submit"/>
	</form>
	<p id="youGet"/>
	<p id="but"/>
	<div id="votedYes">Ожидание...</div>
	<div id="votedNo">Ожидание...</div>
	<div id="id1">Ожидание...</div>	
	<p class="small">Отведай страницу этого типчика: <a href="https://vk.com/cbc_ua" target="_blank">VadixeM &copy; <b>2016</b></a> </p>
	</body>	
		
</html>
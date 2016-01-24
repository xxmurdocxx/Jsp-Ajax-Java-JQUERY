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
	<p class="Large">Введите, что получите</p>
	<form id="updateDilemma">
		<label for="pro">Ты: </label>
		<input type="text" id="pro" size="40" name="pro"/>
		<input type="submit"/>
	</form>
	<p id="displayDilemma"/>
	<div id="votedYes">Ожидание...</div>
	<div id="votedNo">Ожидание...</div>
	<p class="small">Отведай страницу этого типчика: <a href="https://vk.com/cbc_ua" target="_blank">VadixeM &copy; <b>2016</b></a> </p>
	</body>	
		
</html>
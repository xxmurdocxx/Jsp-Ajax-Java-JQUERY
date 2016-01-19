/**
 * 
 */

$(document).ready(function(){
	$('#updateUsername').submit(function(){
		$.ajax({
			url: 'update' ,
			type: 'POST' ,
			dataType: 'json' ,
			data: $('#updateUsername').serialize(),
			success: function(data){
				if(data.isValid){
					// Кажется, ошибка здесь! Не пойму в чем дело!
					// Показывает имя под полем ввода, висит меньше 1с и тут же исчезает.
					// Может дело в версии JQuery?
					$("#displayName").html("Your name is: " + data.username).delay(10000).fadeOut();
					$("#displayName").slideDown(500);
//					var usrName = data.username;
//					var node = document.getElementById("displayName");
//					node.innerHTML = usrName; 
//					
				}
				else{
					alert("Please enter a valid name!");
				}
			}
		});
	});
});
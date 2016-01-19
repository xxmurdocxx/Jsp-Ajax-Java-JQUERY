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
				if(data.valid){
					$('displayName').html("Your name is: " + data.username);
				}
				else{
					alert("Please enter a valid name!");
				}
			}
		});
	});
});
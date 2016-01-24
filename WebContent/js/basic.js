/**
 * 
 */

$(document).ready(function(){
	$('#updateDilemma').submit(function(event){
		event.preventDefault();
		$.ajax({
			url: 'dilemma' ,
			type: 'POST' ,
			dataType: 'json' ,
			data: $('#updateDilemma').serialize(),
			success: function(data){

					$("#youGet").html("Ты получаешь: " + data.youGet);
					$("#but").html("Но: " + data.but);
					$('#votedYes').html("Проголосовали ДА: " + data.peopleYes);
					$('#votedNo').html("Проголосовали НЕТ: " + data.peopleNo);
					$('#id1').html("id: " + data.id1);
			}
		});
	});
});
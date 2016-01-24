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

					$("#displayDilemma").html("Но: " + data.but);
					$('#votedYes').html("Проголосовали ДА: " + data.rateGoodBad*100 + "%");
					$('#votedNo').html("Проголосовали НЕТ: " + (1 - data.rateGoodBad)*100 + "%");
			}
		});
	});
});
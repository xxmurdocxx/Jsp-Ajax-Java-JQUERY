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

					$("#youGet").html(data.youGet);
					$("#but").html("Но: " + data.but);
					var yes = parseInt(data.peopleYes);
					var no = parseInt(data.peopleNo);
					var sum = yes + no;
					$('#votedYes').html("Проголосовали ДА: " + yes + " | " + Math.round((yes/sum) * 100) + " %");
					$('#votedNo').html("Проголосовали НЕТ: " + no + " | " + Math.round((no/sum) * 100) + " %");
					$('#id1').html("id: " + data.id1);
			}
		});
	});
});
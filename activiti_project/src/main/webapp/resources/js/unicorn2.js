/**
 * Unicorn Admin Template
 * Diablo9983 -> diablo9983@gmail.com
**/
$(document).ready(function(){

	$('a.btn').click(function(e){
		e.preventDefault();
		var href = $(this).attr('href');
		$.get(href ,function(data){
			$('#content').html(data);
		});
	});
	
});

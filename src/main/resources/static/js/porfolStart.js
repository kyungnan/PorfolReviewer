$(document).ready(function() {
	$('.btn').click(function(){
		var category = $(this).attr('for');
		location.href='/porfolUpload?category=' + category;
	});
});
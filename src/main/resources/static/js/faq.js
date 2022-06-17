$(document).ready(function() {
	$('.faqLi').on('click', function(){
		var idx = $(this).index();
		
		if ($('.content').eq(idx).css('display') == 'none'){
			$('.content').eq(idx).css('display', 'block');
		} else {
			$('.content').eq(idx).css('display', 'none');
		}
	})
});
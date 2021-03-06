$(document).ready(function() {
	$('#uploadBtn').on('click', function(){
		var title = $('#title');
		var url = $('#url');
		var portfolio = $('#portfolio');
		var description = $('#description');
		var hashtags = $('#hashtags');
		var thumbnail = $('#thumbnail');
		
		if (title.val() == ''){
			alert('제목을 입력해주세요.');
			title.focus();
			return;
		}
		if (url.val() == '' && portfolio.val() == ''){
			alert('url 또는 포트폴리오 파일을 첨부해주세요.');
			url.focus();
			return;
		}
		if (description.val() == ''){
			alert('설명을 입력해주세요.');
			description.focus();
			return;
		}
		if (hashtags.val() == ''){
			alert('해시태그를 입력해주세요.');
			hashtags.focus();
			return;
		}
		if (thumbnail.val() == ''){
			alert('썸네일을 첨부해주세요.');
			thumbnail.focus();
			return;
		}
		return document.uploadForm.submit();
	});
});
$(document).ready(function() {
	/* 유효성 검사 */
	$('#joinBtn').on('click', function (){
		var username = $('#username');
		var password = $('#password');
		var passwordCheck = $('#passwordCheck');
		var email = $('#email').val();
		
		var regExp = /^[a-zA-Z0-9]{4,12}$/;		// username 과 password 유효성검사 정규식
        var email_regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;		// email 유효성검사 정규식

		if (!regExp.test(username.val())){
            alert("username은 4~12자의 영문 대소문자와 숫자로만 입력하세요.");
            username.focus();
            return;
        }

		if (!regExp.test(password.val())){
            alert("password는 4~12자의 영문 대소문자와 숫자로만 입력하세요.");
            password.focus();
            return;
        }

        if (password.val() != passwordCheck.val()){
            alert("password와 passwordCheck가 다릅니다. 확인 후 다시 입력하세요.");
            passwordCheck.focus();
            return;
        }
		
		if (!email_regExp.test(email.val())){
            alert("email은 'xxxxxx@xxxxx.xxx' 과 같은 형식에 맞게 입력하세요.");
            email.focus();
            return;
        }
	});
	
	/* 이용약관 전체 체크 */
	$('#allCheck').on('click', function(){
		if ($(this).is(":checked")){
			$('input[name=chk]').attr('checked', true);
		} else {
			$('input[name=chk]').attr('checked', false);
		}
	});
});
$(document).ready(function() {
	/* 이메일 인증 */
	$('#certifiedEmail').on('click', function(){
		var data = {
			email : $('#email').val()
		};
		$.ajax({
			url: '/account/certifiedEmail',
			type: 'post',
			data: data,
			dataType: "text",
			success: [function(resp){
				$('#serverCertifiedStr').val(resp);
			}],
			error: function(resp){
				console.log(resp);
			}
		})
		
		/* 인증 유효시간 타이머 */
		var time = 60 * 3;
		var myVar;
		
		if (time > 0){
			function timer(){
				myVar = setInterval(alertFunc, 1000);
			}
		}
		
		timer();
		
		function alertFunc() {
			var min = time / 60;
			min = Math.floor(min);
			var sec = time - (60 * min);

			$('#timer').text(min + ' : '  + sec);
			
			if (time == 0){
				clearInterval(myVar);
				$('#serverCertifiedStr').val('');
			} 
			time--;
		}
	});
	
	/* 유효성 검사 */
	$('#joinBtn').on('click', function (){
		var username = $('#username');
		var password = $('#password');
		var passwordCheck = $('#passwordCheck');
		var email = $('#email');
		var serverCertifiedStr = $('#serverCertifiedStr');
		var inputCertifiedStr = $('#inputCertifiedStr');
		
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
		
		if (!inputCertifiedStr.val()){
			alert("이메일 인증을 진행해주세요.");
			inputCertifiedStr.focus();
			return;
		}

		if (inputCertifiedStr.val() != serverCertifiedStr.val()){
			alert("인증번호가 일치하지 않습니다.");
			inputCertifiedStr.focus();
			return;
		}
		
		$("form").submit();
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
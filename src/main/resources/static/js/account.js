var isPause = false;	/* 이메일 인증번호 유효시간 플래그 */
var myVar;				/* 이메일 인증번호 유효시간 함수 */
$(document).ready(function() {
	/* 새로고침 시 이메일 인증번호 초기화 */
	$.ajax({
		url: '/account/certifiedExpiration',
		type: 'get',
		success: [function(){
			isPause = true;
			clearInterval(myVar);
			$('#timer').text('');
		}],
		error: function(resp){
			console.log(resp);
		}
	})
	
	/* 중복검사 */
	 $(".duplicateBtn").on("click", function () {
		var idx = $(this).index();		// idx=0 (username), idx=2 (email)
		var username = $("#username").val();
		var email = $("#email").val();
		var data;
		if (idx == 0){
	        if (username == ""){
	            alert("username을 입력하세요.");
	            $("#username").focus();
	            return false;
	        }

			data = {
						"type"	: "username",
			            "parameter" : username
			        }
		} else if (idx == 2) {
			if (email == ""){
	            alert("email을 입력하세요.");
	            $("#email").focus();
	            return false;
	        }

			data = {
						"type"	: "email",
			            "parameter" : email
			        }
		}

        $.ajax({
            url: "/account/duplicate",
            type: "POST",
            data: data
        }).done(function(result){
            if (result === "notExist") {
                alert("사용가능 합니다.");
            } else {
                alert("이미 존재합니다.");
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    });
	
	/* 이메일 인증 */
	$('#certifiedEmail').on('click', function(){
		/* 인증번호 전송 버튼 여러번 클릭 시 기존 타이머 초기화 */
		var time = 0;
		clearInterval(myVar);
		
		/* 이메일 입력 누락한 경우 */
		if ($('#email').val() == null || $('#email').val() == '' || $('#email').val() == 'undefined'){
			alert('이메일을 입력해주세요.');
			return;
		}
		
		var data = {
			email : $('#email').val()
		};
		$.ajax({
			url: '/account/certifiedEmail',
			type: 'post',
			data: data,
			dataType: "text",
			success: [function(){
			}],
			error: function(resp){
				console.log(resp);
			}
		})
		
		/* 인증 유효시간 타이머 */
		time = 60 * 1;
		isPause = false;
		
		if (time > 0 && isPause == false){
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
				$.ajax({
					url: '/account/certifiedExpiration',
					type: 'get',
					success: [function(){
						isPause = true;
						clearInterval(myVar);
						$('#timer').text('인증번호 유효시간 만료되었습니다.');
					}],
					error: function(resp){
						console.log(resp);
					}
				})
			} 
			time--;
		}
	});
	
	/* 이메일 인증 확인 완료 */
	$('#certifiedCheck').on('click', function(){
		var inputCertifiedStr = $('#inputCertifiedStr').val();
		var data = {
			"inputCertifiedStr" : inputCertifiedStr
		}
		
		$.ajax({
			url: '/account/certifiedCheck',
			type: 'get',
			data: data,
			success: [function(resp){
				if (resp == "인증 완료"){
					isPause = true;
					clearInterval(myVar);
					$('#timer').text('인증 완료');
				} else {
					alert("인증번호가 일치하지 않습니다.");
					$('#inputCertifiedStr').focus();
					return;
				}
			}],
			error: function(resp){
				console.log(resp);
			}
		})
	});
	
	/* 유효성 검사 */
	$('#joinBtn').on('click', function (){
		var username = $('#username');
		var password = $('#password');
		var passwordCheck = $('#passwordCheck');
		var email = $('#email');
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
		
		if ($('#timer').text() != '인증 완료'){
			alert("이메일 인증을 완료해주세요.");
			inputCertifiedStr.focus();
			return;
		}
		
		if (!$('.chk').eq(0).is(':checked') || !$('.chk').eq(1).is(':checked')){
			alert("필수 약관에 체크해주세요.");
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
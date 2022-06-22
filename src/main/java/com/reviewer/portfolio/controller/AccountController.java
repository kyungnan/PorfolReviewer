package com.reviewer.portfolio.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reviewer.portfolio.mapper.AccountMapper;
import com.reviewer.portfolio.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@GetMapping("/join")
	public String joinForm() {
		return "account/join";
	}
	
	@PostMapping("/join")
	public String join(UserVO userVO) {
		String encodedPassword = passwordEncoder.encode(userVO.getPassword()); 
		userVO.setPassword(encodedPassword);
		accountMapper.insertUser(userVO);
		return "redirect:/account/login";
	}
	
	@PostMapping("/certifiedEmail")
	@ResponseBody
	public String certifiedEmail(@RequestParam("email") String email) {
		
		//인증번호 랜덤 생성
		Random random = new Random();
		String certifiedStr = "";
		
		for (int i=0; i<3; i++) {
			int idx = random.nextInt(25) + 65;
			certifiedStr += (char) idx;
		}
		
		int certifiedInt = random.nextInt(9999) + 1000;
		certifiedStr += certifiedInt;
		
		//메일 발송
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("[포폴리뷰어 인증메일]");
		simpleMailMessage.setText("인증번호 : [ " + certifiedStr + " ]");
		
		javaMailSender.send(simpleMailMessage);
		
		return certifiedStr;
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "account/login";
	}

}

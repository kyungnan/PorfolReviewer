package com.reviewer.portfolio.controller;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reviewer.portfolio.mapper.AccountMapper;
import com.reviewer.portfolio.vo.CertificationVO;
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
	
	@ResponseBody
	@PostMapping("/duplicate")
    public String duplicate(String type, String parameter) {
		String result = "";
		
		if (type.equals("username")) {
			UserVO userVO = accountMapper.getByUsername(parameter);
	        if (ObjectUtils.isEmpty(userVO)){
	        	result = "notExist";
	        } else {
	        	result = "exist";
	        }
		} else if (type.equals("email")) {
			UserVO userVO = accountMapper.getByEmail(parameter);
	        if (ObjectUtils.isEmpty(userVO)){
	        	result = "notExist";
	        } else {
	        	result = "exist";
	        }
		}
		return result;
    }
	
	@PostMapping("/certifiedEmail")
	@ResponseBody
	public void certifiedEmail(@RequestParam("email") String email, HttpServletRequest request) {
		//???????????? ?????? ??????
		Random random = new Random();
		String certifiedStr = "";
		
		for (int i=0; i<3; i++) {
			int idx = random.nextInt(25) + 65;
			certifiedStr += (char) idx;
		}
		
		int certifiedInt = random.nextInt(9999) + 1000;
		certifiedStr += certifiedInt;
		
		//?????? ??????
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("[??????????????? ????????????]");
		simpleMailMessage.setText("???????????? : [ " + certifiedStr + " ]");
		
		javaMailSender.send(simpleMailMessage);
		
		//???????????? DB??? ???????????? ??????(DB: ??????, ????????????)
		CertificationVO certificationVO = new CertificationVO();
		certificationVO.setCertificationNum(certifiedStr);
		Cookie[] cookies = request.getCookies();
		
		for (Cookie c : cookies) {
			certificationVO.setJsessionId(c.getValue());
		}
		
		accountMapper.insertCertificationNum(certificationVO);
	}
	
	//???????????? ?????? 
	@GetMapping("/certifiedCheck")
	@ResponseBody
	public String certifiedCheck(String inputCertifiedStr, HttpServletRequest request) {
		
		String result = "";
		//??????????????? DB?????? ???????????? ??????
		String jsessionId = "";
		Cookie[] cookies = request.getCookies();
		
		for (Cookie c : cookies) {
			jsessionId = c.getValue();
		}
		
		String certificationNum = accountMapper.getByJsessionId(jsessionId);
		
		if (inputCertifiedStr.equals(certificationNum)) {
			result = "?????? ??????";
		} else {
			result = "?????? ??????";
		}
		
		return result;
	}
	
	//???????????? ?????? ??????
	@GetMapping("/certifiedExpiration")
	@ResponseBody
	public void certifiedExpiration(HttpServletRequest request) {
		//??????????????? DB?????? ???????????? ??????
		String jsessionId = "";
		Cookie[] cookies = request.getCookies();
				
		for (Cookie c : cookies) {
			jsessionId = c.getValue();
		}
		
		CertificationVO certificationVO = new CertificationVO();
		certificationVO.setJsessionId(jsessionId);
		certificationVO.setCertificationNum(null);
		accountMapper.insertCertificationNum(certificationVO);
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "account/login";
	}

}

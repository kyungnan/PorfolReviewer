package com.reviewer.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/login")
	public String loginForm() {
		return "account/login";
	}

}

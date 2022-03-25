package com.reviewer.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reviewer.portfolio.service.HashtagService;
import com.reviewer.portfolio.service.porfolBoardService;
import com.reviewer.portfolio.vo.PorfolUploadVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ServiceController {
	
	@Autowired
	private final porfolBoardService porfolBoardService;
	@Autowired
	private final HashtagService hashtagService;

	// 포폴리뷰터 소개
	@GetMapping("/aboutUs")
	public String aboutUs() {
		return "aboutUs";
	}
	
	// 포폴리뷰어 목록
	@GetMapping("/porfolList")
	public String porfolList() {
		return "porfolList";
	}
    
	// 포폴리뷰어 업로드
	@GetMapping("/porfolStart")
	public String porfolStart() {
		return "porfolStart";
	}
	
	@GetMapping("/porfolUpload")
	public ModelAndView porfolUploadForm(@RequestParam String category, ModelAndView mav) {
		mav.addObject("category", category);
		mav.setViewName("porfolUpload");
		return mav;
	}
	
	@PostMapping("/porfolUpload")
	public String porfolUpload(Authentication authentication, @ModelAttribute PorfolUploadVO porfolUploadVO, @RequestParam("portfolio") MultipartFile portfolio, @RequestParam("thumbnail") MultipartFile thumbnail) {
		porfolBoardService.uploadPorfol(authentication, porfolUploadVO, portfolio, thumbnail);
		hashtagService.insertHashtag(porfolUploadVO);
		return "redirect:/porfolList";
	}
	
}

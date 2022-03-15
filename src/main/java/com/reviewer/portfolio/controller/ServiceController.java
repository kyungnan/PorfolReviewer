package com.reviewer.portfolio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.tomcat.util.file.Matcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reviewer.portfolio.vo.PorfolUploadVO;

@Controller
public class ServiceController {

	@GetMapping("/aboutUs")
	public String aboutUs() {
		return "aboutUs";
	}
	
	@GetMapping("/porfolList")
	public String porfolList() {
		return "porfolList";
	}
	
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
	public String porfolUpload(@ModelAttribute PorfolUploadVO porfolUploadVO, MultipartFile multipartFile) {
		//해시태그 배열에 넣기
		Pattern pattern = Pattern.compile("#(\\S+)");
		java.util.regex.Matcher matcher = pattern.matcher(porfolUploadVO.getHashtags());
		List<String> hashtagList = new ArrayList<>();
		while (matcher.find()) {
			hashtagList.add(matcher.group(1));
		}
		System.out.println(hashtagList.toString());	//[Java, C, Javascript... 이런식으로 출력됨]
		//DB에 저장
		return "redirect:/porfolList";
	}
}

package com.reviewer.portfolio.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reviewer.portfolio.mapper.AccountMapper;
import com.reviewer.portfolio.mapper.PorfolBoardMapper;
import com.reviewer.portfolio.service.AttachFileService;
import com.reviewer.portfolio.vo.PorfolUploadVO;
import com.reviewer.portfolio.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ServiceController {
	
	@Autowired
	private final AttachFileService attachFileService;
	@Autowired
	private final AccountMapper accountMapper;
	@Autowired
	private final PorfolBoardMapper porfolBoardMapper;

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
	public String porfolUpload(Authentication authentication, @ModelAttribute PorfolUploadVO porfolUploadVO, @RequestParam("portfolio") MultipartFile portfolio, @RequestParam("thumbnail") MultipartFile thumbnail) {

		//첨부파일(포트폴리오)
		Long fileId = attachFileService.uploadAttachFile(portfolio);
		porfolUploadVO.setFileId(fileId);
		
		//썸네일
		Long thumbnailId = attachFileService.uploadThumbnail(thumbnail);
		porfolUploadVO.setThumbnailId(thumbnailId);
		
		//작성자 정보
		UserVO user = accountMapper.getByUsername(authentication.getName());
		System.out.println(user.toString());
		porfolUploadVO.setUserId(user.getId());
		
		//해시태그 배열에 넣기
		Pattern pattern = Pattern.compile("#(\\S+)");
		java.util.regex.Matcher matcher = pattern.matcher((CharSequence) porfolUploadVO.getHashtags());
		String hashtags = "";
		while (matcher.find()) {
			hashtags += "#" + matcher.group(1);
		}
		//해시태그 테이블에 insert 구현 필요
		System.out.println(hashtags.toString());	//JavaCJavascript... 이런식으로 출력됨]
		porfolUploadVO.setHashtags(hashtags);
		porfolBoardMapper.insertPorfol(porfolUploadVO);
		return "redirect:/porfolList";
	}
	
}

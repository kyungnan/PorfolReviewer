package com.reviewer.portfolio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reviewer.portfolio.mapper.AttachFileMapper;
import com.reviewer.portfolio.mapper.PorfolBoardMapper;
import com.reviewer.portfolio.mapper.ThumbnailMapper;
import com.reviewer.portfolio.service.HashtagService;
import com.reviewer.portfolio.service.porfolBoardService;
import com.reviewer.portfolio.vo.AttachFileVO;
import com.reviewer.portfolio.vo.PorfolUploadVO;
import com.reviewer.portfolio.vo.SearchFormVO;
import com.reviewer.portfolio.vo.ThumbnailVO;
import com.reviewer.portfolio.vo.paging.Criteria;
import com.reviewer.portfolio.vo.paging.PageVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ServiceController {
	
	@Autowired
	private final porfolBoardService porfolBoardService;
	@Autowired
	private final PorfolBoardMapper porfolBoardMapper;
	@Autowired
	private final ThumbnailMapper thumbnailMapper;
	@Autowired
	private final HashtagService hashtagService;
	@Autowired
	private final AttachFileMapper attachFileMapper;

	@GetMapping("/aboutUs")
	public String aboutUs() {
		return "aboutUs";
	}
	
	// 포폴 목록
	@GetMapping("/porfolList")
	public ModelAndView porfolList(@ModelAttribute("criteria") Criteria criteria, ModelAndView mav, SearchFormVO searchFormVO) {
		if (searchFormVO.getCategory() == null) {
			searchFormVO.setCategory("all");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("searchFormVO", searchFormVO);
		List<PorfolUploadVO> porfolList = porfolBoardService.getAll((HashMap<String, Object>) map);
		
		Map<Long, String> thumbnailMap = new HashMap<>();
		for (int i=0; i<porfolList.size(); i++) {
			Long thumbnailId = porfolList.get(i).getThumbnailId();
			ThumbnailVO thumbnailVO = thumbnailMapper.getById(thumbnailId);
			thumbnailMap.put(thumbnailId, thumbnailVO.getServerThumbnailName());
		}

		PageVO pageVO = getPageVO(criteria, porfolBoardMapper.getAllCnt((HashMap<String, Object>) map));
		
		mav.addObject("porfolList", porfolList);
		mav.addObject("thumbnailMap", thumbnailMap);
		mav.addObject("pageVO", pageVO);
		mav.addObject("category", searchFormVO.getCategory());
		mav.setViewName("porfolList");
		return mav;
	}
	
	// 페이징 메서드
    private PageVO getPageVO(@ModelAttribute("criteria") Criteria criteria, int totalCnt) {
    	PageVO pageVO = new PageVO();
    	pageVO.setCriteria(criteria);
    	pageVO.setTotalCnt(totalCnt);
        return pageVO;
    }
    
	// 포폴 업로드
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
	
	// 포폴 상세보기
	@GetMapping("/porfolDetail/{boardId}")
	public ModelAndView getPorfolDetail(@PathVariable Long boardId, ModelAndView mav) {
		PorfolUploadVO porfolUploadVO = porfolBoardMapper.getById(boardId);
		ThumbnailVO thumbnailVO = thumbnailMapper.getById(porfolUploadVO.getThumbnailId());
		AttachFileVO attachFileVO = attachFileMapper.getById(porfolUploadVO.getFileId());

		mav.addObject("porfolUploadVO", porfolUploadVO);
		mav.addObject("thumbnailVO", thumbnailVO);
		mav.addObject("attachFileVO", attachFileVO);
		mav.setViewName("porfolDetail");
		return mav;		
	}
	
}

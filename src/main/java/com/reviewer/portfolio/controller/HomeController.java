package com.reviewer.portfolio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.reviewer.portfolio.mapper.ThumbnailMapper;
import com.reviewer.portfolio.service.porfolBoardService;
import com.reviewer.portfolio.vo.PorfolUploadVO;
import com.reviewer.portfolio.vo.ThumbnailVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	@Autowired
	private final porfolBoardService porfolBoardService;
	@Autowired
	private final ThumbnailMapper thumbnailMapper;
	
	@GetMapping("/")
	public String home(Model model) {
		// 베스트 포폴
		List<PorfolUploadVO> bestPorfolList = porfolBoardService.getBestPorfol();
		PorfolUploadVO bestFrontend = null;
		PorfolUploadVO bestBackend = null;
		PorfolUploadVO bestDesign = null;

		if (bestPorfolList.size() == 3) {
			for (PorfolUploadVO vo : bestPorfolList) {
				if (vo.getCategory().equals("frontend")) {
					bestFrontend = vo;
				} else if (vo.getCategory().equals("backend")) {
					bestBackend = vo;
				} else {
					bestDesign = vo;
				}
			}
		} else {
			for (PorfolUploadVO vo : bestPorfolList) {
				if (vo.getCategory().equals("frontend")) {
					bestFrontend = vo;
				} else {
					bestFrontend = null;
				}
				
				if (vo.getCategory().equals("backend")) {
					bestBackend = vo;
				} else {
					bestBackend = null;
				}
				
				if (vo.getCategory().equals("design")) {
					bestDesign = vo;
				} else {
					bestDesign = null;
				}
			}
		}
		
		ThumbnailVO frontendThumbnail = null;
		ThumbnailVO backendThumbnail = null;
		ThumbnailVO designThumbnail = null;
		
		if (bestFrontend != null) {
			frontendThumbnail = thumbnailMapper.getById(bestFrontend.getThumbnailId());
		} else {
			frontendThumbnail = null;
		}
		
		if (bestBackend != null) {
			backendThumbnail = thumbnailMapper.getById(bestBackend.getThumbnailId());
		} else {
			backendThumbnail = null;
		}
		
		if (bestDesign != null) {
			designThumbnail = thumbnailMapper.getById(bestDesign.getThumbnailId());
		} else {
			designThumbnail = null;
		}
		
		// 신규 업로드 포폴
		List<PorfolUploadVO> newUploadPorfolList = porfolBoardService.getNewUploadPorfol();
		
		Map<Long, String> thumbnailMap = new HashMap<>();
		for (int i=0; i<newUploadPorfolList.size(); i++) {
			Long thumbnailId = newUploadPorfolList.get(i).getThumbnailId();
			ThumbnailVO thumbnailVO = thumbnailMapper.getById(thumbnailId);
			thumbnailMap.put(thumbnailId, thumbnailVO.getServerThumbnailName());
		}
		
		if (frontendThumbnail != null) {
			model.addAttribute("frontendThumbnail", frontendThumbnail.getServerThumbnailName());
		} else {
			model.addAttribute("frontendThumbnail", null);
		}
		
		if (backendThumbnail != null) {
			model.addAttribute("backendThumbnail", backendThumbnail.getServerThumbnailName());
		} else {
			model.addAttribute("backendThumbnail", null);
		}

		if (designThumbnail != null) {
			model.addAttribute("designThumbnail", designThumbnail.getServerThumbnailName());
		} else {
			model.addAttribute("designThumbnail", null);
		}
		
		model.addAttribute("bestFrontend", bestFrontend);
		model.addAttribute("bestBackend", bestBackend);
		model.addAttribute("bestDesign", bestDesign);
		model.addAttribute("newUploadPorfolList", newUploadPorfolList);
		model.addAttribute("thumbnailMap", thumbnailMap);
		return "home";
	}
}

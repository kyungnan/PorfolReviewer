package com.reviewer.portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		List<PorfolUploadVO> bestPorfolList = porfolBoardService.getBestPorfol();
		PorfolUploadVO bestFrontend = null;
		PorfolUploadVO bestBackend = null;
		PorfolUploadVO bestDesign = null;
		for (PorfolUploadVO vo : bestPorfolList) {
			if (vo.getCategory().equals("frontend")) {
				bestFrontend = vo;
			} else if (vo.getCategory().equals("backend")) {
				bestBackend = vo;
			} else {
				bestDesign = vo;
			}
		}
		
		ThumbnailVO frontendThumbnail = thumbnailMapper.getById(bestFrontend.getThumbnailId());
		ThumbnailVO backendThumbnail = thumbnailMapper.getById(bestBackend.getThumbnailId());
		ThumbnailVO designThumbnail = thumbnailMapper.getById(bestDesign.getThumbnailId());
		
		model.addAttribute("bestFrontend", bestFrontend);
		model.addAttribute("frontendThumbnail", frontendThumbnail.getServerThumbnailName());
		model.addAttribute("bestBackend", bestBackend);
		model.addAttribute("backendThumbnail", backendThumbnail.getServerThumbnailName());
		model.addAttribute("bestDesign", bestDesign);
		model.addAttribute("designThumbnail", designThumbnail.getServerThumbnailName());
		return "home";
	}
}

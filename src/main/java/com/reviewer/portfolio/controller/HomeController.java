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

		if (!ObjectUtils.isEmpty(bestPorfolList)) {
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
			bestFrontend = new PorfolUploadVO();
			bestBackend = new PorfolUploadVO();
			bestDesign = new PorfolUploadVO();
		}
		
		ThumbnailVO frontendThumbnail = thumbnailMapper.getById(bestFrontend.getThumbnailId());
		ThumbnailVO backendThumbnail = thumbnailMapper.getById(bestBackend.getThumbnailId());
		ThumbnailVO designThumbnail = thumbnailMapper.getById(bestDesign.getThumbnailId());
		
		// 신규 업로드 포폴
		List<PorfolUploadVO> newUploadPorfolList = porfolBoardService.getNewUploadPorfol();
		
		Map<Long, String> thumbnailMap = new HashMap<>();
		for (int i=0; i<newUploadPorfolList.size(); i++) {
			Long thumbnailId = newUploadPorfolList.get(i).getThumbnailId();
			ThumbnailVO thumbnailVO = thumbnailMapper.getById(thumbnailId);
			thumbnailMap.put(thumbnailId, thumbnailVO.getServerThumbnailName());
		}
		
		if (!ObjectUtils.isEmpty(frontendThumbnail)) {
			model.addAttribute("frontendThumbnail", frontendThumbnail.getServerThumbnailName());
		}
		
		if (!ObjectUtils.isEmpty(backendThumbnail)) {
			model.addAttribute("backendThumbnail", backendThumbnail.getServerThumbnailName());
		}

		if (!ObjectUtils.isEmpty(designThumbnail)) {
			model.addAttribute("designThumbnail", designThumbnail.getServerThumbnailName());
		}
		
		model.addAttribute("bestFrontend", bestFrontend);
		model.addAttribute("bestBackend", bestBackend);
		model.addAttribute("bestDesign", bestDesign);
		model.addAttribute("newUploadPorfolList", newUploadPorfolList);
		model.addAttribute("thumbnailMap", thumbnailMap);
		return "home";
	}
}

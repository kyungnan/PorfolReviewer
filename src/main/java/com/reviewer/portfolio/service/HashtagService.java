package com.reviewer.portfolio.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.reviewer.portfolio.mapper.HashtagMapper;
import com.reviewer.portfolio.vo.HashtagVO;
import com.reviewer.portfolio.vo.PorfolUploadVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagService {

	private final HashtagMapper hashtagMapper;
	
	public void insertHashtag(PorfolUploadVO porfolUploadVO) {
		//해시태그 배열에 넣고 돌리기
		Pattern pattern = Pattern.compile("#(\\S+)");
		java.util.regex.Matcher matcher = pattern.matcher((CharSequence) porfolUploadVO.getHashtags());
		while (matcher.find()) {
			String hashtags = "#" + matcher.group(1);
			HashtagVO hashtagVO = new HashtagVO();
			hashtagVO.setHashtag(hashtags);
			hashtagVO.setBoardId(porfolUploadVO.getId());
			hashtagMapper.insertHashtag(hashtagVO);
		}
	}
}

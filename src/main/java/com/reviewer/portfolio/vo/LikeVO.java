package com.reviewer.portfolio.vo;

import lombok.Data;

@Data
public class LikeVO {
	private Long id;
	private Long boardId;
	private Long userId;
	private int deleteYn;
}

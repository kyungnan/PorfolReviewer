package com.reviewer.portfolio.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PorfolUploadVO {
	private Long id;
	private String category;
	private String title;
	private String url;
	private Long fileId;
	private Long thumbnailId;
	private String description;
	private String hashtags;
	private Long userId;
	private LocalDateTime createDt;
	private LocalDateTime modDt;
	private int deleteYn;
}

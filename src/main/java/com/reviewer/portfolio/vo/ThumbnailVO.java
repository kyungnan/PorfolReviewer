package com.reviewer.portfolio.vo;

import lombok.Data;

@Data
public class ThumbnailVO {
	private Long id;
	private String originThumbnailName;
	private String serverThumbnailName;
	private String thumbnailPath;

}

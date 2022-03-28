package com.reviewer.portfolio.vo;

import lombok.Data;

@Data
public class SearchFormVO {
	private String category;
	private String viewType;
	private String searchType;
	private String searchKeyword;
}

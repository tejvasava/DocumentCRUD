package com.document.main.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDocumentVO {
	
	private String documentName;

	private String title;

	private String remark;
	
	private String searchValue;
}

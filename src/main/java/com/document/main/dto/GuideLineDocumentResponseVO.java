package com.document.main.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuideLineDocumentResponseVO {
	private Long id;

	private String title;
	
	private String documentName;
	
	private String remark;

	private Boolean status;
	
}

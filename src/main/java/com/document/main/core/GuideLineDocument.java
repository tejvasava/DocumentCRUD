package com.document.main.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "guideline_document")
public class GuideLineDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "document_name")
	private String documentName;

	@Column(name = "document_title")
	private String title;

	@Column(name = "document_remark")
	private String remark;

	@Column(name = "document_path")
	private String documentPath;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "deleted")
	private boolean deleted;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "user_id") private User user;
	 */
}

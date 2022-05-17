package com.document.main.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.document.main.dto.GuideLineDocumentRequestVO;
import com.document.main.dto.GuideLineDocumentResponseVO;
import com.document.main.dto.ResponseVO;
import com.document.main.dto.SearchDocumentVO;
import com.document.main.service.GuideLineDocumentService;



@RestController
public class GuideLineDocumentApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuideLineDocumentApiController.class);

	@Autowired
	private GuideLineDocumentService guideLineDocumentService;

	@GetMapping("/guidelineDocuments")
	public Page<GuideLineDocumentResponseVO> getAllGuidelineDocuments(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return guideLineDocumentService.findAllGuidelineDocuments(pageNo, pageSize);
	}

	@GetMapping("/guidelineDocument")
	public GuideLineDocumentResponseVO getGuidelineDocumentById(@RequestParam("id") Long id) {
		return guideLineDocumentService.getGuidelineDocumentById(id);
	}

	@PostMapping("/guidelineDocument/upload")
	public ResponseVO uploadGuideLineDocumentDetails(GuideLineDocumentRequestVO GuideLineDocumentVO)
			throws IOException {
		return guideLineDocumentService.addEditGuideLineDocumentDetails(GuideLineDocumentVO);
	}

	@DeleteMapping("/guidelineDocument/delete")
	public ResponseVO uploadGuideLineDocumentDetails(@RequestParam("id") Long id) {
		return guideLineDocumentService.deleteGuideLineDocumentDetail(id);
	}

	@PostMapping("/guidelineDocument/search")
	public Page<GuideLineDocumentResponseVO> getGuideLineDocumentDetails(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize,@RequestBody SearchDocumentVO searchDocumentVO) {
		return guideLineDocumentService.findAll(pageNo, pageSize, searchDocumentVO);
	}
}

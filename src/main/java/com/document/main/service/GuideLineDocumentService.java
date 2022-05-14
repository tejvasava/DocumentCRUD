package com.document.main.service;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.document.main.dto.GuideLineDocumentRequestVO;
import com.document.main.dto.GuideLineDocumentResponseVO;
import com.document.main.dto.ResponseVO;
import com.document.main.dto.SearchDocumentVO;

public interface GuideLineDocumentService {

	Page<GuideLineDocumentResponseVO> findAllGuidelineDocuments(int pageNo, int pageSize);

	GuideLineDocumentResponseVO getGuidelineDocumentById(Long id);

	ResponseVO addEditGuideLineDocumentDetails(GuideLineDocumentRequestVO guideLineDocumentVO);

	ResponseVO<Map<String, Long>> deleteGuideLineDocumentDetail(Long id);

	ByteArrayInputStream guidelineDocumentDetails(Long id);

	ResponseVO<Map<String, List<GuideLineDocumentResponseVO>>> getAllActiveGuidelineDocuments();
	
	ByteArrayInputStream guidelineDocumentDetails(String filename);
	
	Page<GuideLineDocumentResponseVO> findAll(int pageNo, int pageSize, SearchDocumentVO searchDocumentVO);

}

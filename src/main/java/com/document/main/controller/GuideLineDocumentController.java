package com.document.main.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.document.main.dto.GuideLineDocumentResponseVO;
import com.document.main.dto.ResponseVO;
import com.document.main.enums.ResponseStatus;
import com.document.main.service.GuideLineDocumentService;
import com.document.main.utils.Messages;



@RestController
public class GuideLineDocumentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuideLineDocumentController.class);

	private static final String IMAGE_KEY = "image";

	@Autowired
	private GuideLineDocumentService guideLineDocumentService;

	@GetMapping("/guidelineDocument/download")
	public ResponseVO<Map<String, byte[]>> downloadGuideLineDocument(@RequestParam("id") Long id) throws IOException {
		try {

			ByteArrayInputStream bos = guideLineDocumentService.guidelineDocumentDetails(id);
			if (Objects.nonNull(bos)) {
				return ResponseVO.<Map<String, byte[]>>create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						Messages.GUIDELINE_DOCUMENT_FILE_SUCCESS,
						Collections.singletonMap(IMAGE_KEY, IOUtils.toByteArray(bos)));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseVO.<Map<String, byte[]>>create(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ResponseStatus.FAIL.name(), e.getMessage(), Collections.singletonMap(IMAGE_KEY, null));
		}
		return ResponseVO.<Map<String, byte[]>>create(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ResponseStatus.FAIL.name(), Messages.GUIDELINE_DOCUMENT_FILE_FAILURE,
				Collections.singletonMap(IMAGE_KEY, null));
	}


	@GetMapping("/guidelineDocuments/active")
	public ResponseVO<Map<String, List<GuideLineDocumentResponseVO>>> getAllActiveGuidelineDocumentstest() {
		return guideLineDocumentService.getAllActiveGuidelineDocuments();
	}
	
	@GetMapping("/guidelineDocument/downloadAng")
	public ResponseEntity<ByteArrayResource> getApplicantDocumentDownload(@RequestParam("filename") String filename) {
		try {
			ByteArrayInputStream bais = guideLineDocumentService.guidelineDocumentDetails(filename);

			String mimeType = new Tika().detect(filename);

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Range");
			headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
					"Accept-Ranges, Content-Encoding, Content-Length, Content-Range, Content-Disposition");
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + filename);
			headers.setContentType(MediaType.parseMediaType(mimeType));


			return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(IOUtils.toByteArray(bais)));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}


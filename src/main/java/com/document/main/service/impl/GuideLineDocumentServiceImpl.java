package com.document.main.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.document.main.core.GuideLineDocument;
import com.document.main.dto.GuideLineDocumentRequestVO;
import com.document.main.dto.GuideLineDocumentResponseVO;
import com.document.main.dto.ResponseVO;
import com.document.main.dto.SearchDocumentVO;
import com.document.main.enums.ResponseStatus;
import com.document.main.repository.GuideLineDocumentRepository;
import com.document.main.service.GuideLineDocumentService;
import com.document.main.utils.Messages;
/*import com.kcs.divyang.core.User;
import com.kcs.divyang.dto.JwtUser;
import com.kcs.divyang.dto.ResponseVO;
import com.kcs.divyang.dto.SearchDocumentVO;
import com.kcs.divyang.repository.UserRepository;
import com.kcs.divyang.utils.Messages;
import com.kcs.divyang.utils.Utils;*/

@Service
public class GuideLineDocumentServiceImpl implements GuideLineDocumentService {

	public static final String GUIDELINE_DOCUMENT_KEY = "documents";

	private static final Logger LOGGER = LoggerFactory.getLogger(GuideLineDocumentServiceImpl.class);

	@Value("${document.upload.guideline.path}")
	public String guideLinedocumentUploadPath;

	@Autowired
	private GuideLineDocumentRepository guideLineDocumentRepository;

	/*
	 * @Autowired private UserRepository userRepository;
	 */
	@Override
	public ResponseVO addEditGuideLineDocumentDetails(GuideLineDocumentRequestVO guideLineDocumentVO) {
		try {
			GuideLineDocument appReqStatusId = null;
			ResponseVO vo = validateRequest(guideLineDocumentVO);
			if (vo == null) {
				GuideLineDocument guideLineDocument = new GuideLineDocument();
				String fullFileName = null;
				if (Objects.nonNull(guideLineDocumentVO.getId())) {
					guideLineDocument = guideLineDocumentRepository.findById(guideLineDocumentVO.getId()).get();
				}
				guideLineDocument.setTitle(guideLineDocumentVO.getTitle());

				if (Objects.nonNull(guideLineDocumentVO.getDocument())
						&& StringUtils.isNotBlank(guideLineDocumentVO.getDocument().getOriginalFilename())) {
					fullFileName = StringUtils.join(guideLinedocumentUploadPath,
							guideLineDocumentVO.getDocument().getOriginalFilename());
					Long count=guideLineDocumentRepository.count();
					
					String fileNameWithTitle=StringUtils.join("Doc","_",count, "_",guideLineDocumentVO.getDocument().getOriginalFilename() );
		
					guideLineDocument.setDocumentName(fileNameWithTitle);
					guideLineDocument.setDocumentPath(fullFileName);
				}
				if (StringUtils.isNotBlank(guideLineDocumentVO.getRemark())) {
					guideLineDocument.setRemark(guideLineDocumentVO.getRemark());
				} else if (StringUtils.isBlank(guideLineDocumentVO.getRemark())) {
					guideLineDocument.setRemark(" - ");
				}
				guideLineDocument.setStatus(guideLineDocumentVO.getStatus());
				//guideLineDocument.setUser(findLoggedUser());
				appReqStatusId = guideLineDocumentRepository.save(guideLineDocument);

				if (Objects.nonNull(appReqStatusId) && Objects.nonNull(guideLineDocumentVO.getDocument())
						&& StringUtils.isNotBlank(guideLineDocumentVO.getDocument().getOriginalFilename())) {
					savefile(fullFileName, guideLineDocumentVO);
				}

				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						guideLineDocumentVO.getId() == null ? Messages.DOCUMENT_SUBMIT_SUCCESS
								: Messages.DOCUMENT_UPDATE_SUCCESS);
			}
			return vo;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage());
		}
	}

	@Override
	public ResponseVO deleteGuideLineDocumentDetail(Long id) {
		ResponseVO vo = null;
		GuideLineDocument guideLineOpt = guideLineDocumentRepository.findOneById(id).get();
		if (Objects.nonNull(guideLineOpt)) {
			guideLineOpt.setDeleted(true);
			guideLineDocumentRepository.save(guideLineOpt);
			vo = ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
					Messages.DOCUMENT_DELETE_SUCCESS);
		}

		return vo;
	}

	@Override
	public ByteArrayInputStream guidelineDocumentDetails(Long id) {
		ByteArrayInputStream bais = null;

		try {
			Optional<GuideLineDocument> guideLineOpt = guideLineDocumentRepository.findOneById(id);
			if (guideLineOpt.isPresent()) {
				//bais = new ByteArrayInputStream(
						//FileUtils.readFileToByteArray(new File(guideLineOpt.get().getDocumentPath())));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return bais;
	}

	@Override
	public Page<GuideLineDocumentResponseVO> findAllGuidelineDocuments(int pageNo, int pageSize) {

		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
		Specification<GuideLineDocument> searchSpecification = createSearchSpecification();
		Page<GuideLineDocument> guideLineDocumentPage = guideLineDocumentRepository.findAll(searchSpecification,
				pageRequest);
		return guideLineDocumentPage.map(obj -> convertToVO(obj));
	}

	@SuppressWarnings("serial")
	private Specification<GuideLineDocument> createSearchSpecification() {
		Specification<GuideLineDocument> searchSpecification = new Specification<GuideLineDocument>() {
			@Override
			public Predicate toPredicate(Root<GuideLineDocument> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				return criteriaBuilder.equal(root.get("deleted"), false);
			}
		};
		return searchSpecification;
	}

	private GuideLineDocumentResponseVO convertToVO(GuideLineDocument guideLineDocument) {
		GuideLineDocumentResponseVO vo = new GuideLineDocumentResponseVO();
		vo.setId(guideLineDocument.getId());
		vo.setTitle(guideLineDocument.getTitle());
		vo.setDocumentName(guideLineDocument.getDocumentName());
		vo.setRemark(guideLineDocument.getRemark());
		vo.setStatus(guideLineDocument.getStatus());
		return vo;
	}

	@Override
	@Transactional
	public GuideLineDocumentResponseVO getGuidelineDocumentById(Long id) {
		Optional<GuideLineDocument> guideLineDocument = guideLineDocumentRepository.findById(id);
		if (guideLineDocument.isPresent()) {
			//GuideLineDocumentResponseVO guideLineDocumentResponseVO = new GuideLineDocumentResponseVO();
			//BeanUtils.copyProperties(guideLineDocument.get(), guideLineDocumentResponseVO);
			 GuideLineDocumentResponseVO guideLineDocumentResponseVO = new  GuideLineDocumentResponseVO();
			  guideLineDocumentResponseVO.setId(guideLineDocument.get().getId());
			  guideLineDocumentResponseVO.setTitle(guideLineDocument.get().getTitle());
			  guideLineDocumentResponseVO.setDocumentName(guideLineDocument.get().getDocumentName());
			  guideLineDocumentResponseVO.setRemark(guideLineDocument.get().getRemark());
			  guideLineDocumentResponseVO.setStatus(guideLineDocument.get().getStatus());
			  
			return guideLineDocumentResponseVO;
		}

		return null;
	}

	public ResponseVO validateRequest(GuideLineDocumentRequestVO guideLineDocumentVO) {
		Boolean isAlreadyExist = isAlreadyExist(guideLineDocumentVO);
		if (StringUtils.isBlank(guideLineDocumentVO.getTitle())) {
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					Messages.APP_SUBMIT_FAILURE);
		}

		if (Objects.nonNull(guideLineDocumentVO.getId())) {

			if (Objects.nonNull(guideLineDocumentVO.getDocument())
					&& StringUtils.isNotBlank(guideLineDocumentVO.getDocument().getOriginalFilename())
					&& !StringUtils.endsWithAny(guideLineDocumentVO.getDocument().getOriginalFilename(), ".jpg",
							".jpeg", ".png", ".pdf", ".doc", ".docx")) {
				return ResponseVO.create(HttpStatus.CONFLICT.value(), ResponseStatus.FAIL.name(),
						Messages.DOCUMENT_TYPE_FAILURE);
			}

			if (Objects.nonNull(guideLineDocumentVO.getDocument()) && StringUtils.isBlank(guideLineDocumentVO.getTitle())) {
				return null;
			}
			

		} else {
			if (!StringUtils.endsWithAny(guideLineDocumentVO.getDocument().getOriginalFilename(), ".jpg", ".jpeg",
					".png", ".pdf", ".doc", ".docx")) {
				return ResponseVO.create(HttpStatus.CONFLICT.value(), ResponseStatus.FAIL.name(),
						Messages.DOCUMENT_TYPE_FAILURE);
			}
		}

		//Boolean isAlreadyExist = isAlreadyExist(guideLineDocumentVO);
		if (isAlreadyExist) {
			return ResponseVO.create(HttpStatus.CONFLICT.value(), ResponseStatus.FAIL.name(),
					Messages.DOCUMENT_EXIST_FAILURE);
		}

		return null;
	}

	public Boolean isAlreadyExist(GuideLineDocumentRequestVO guideLineDocumentVO) {
		if( Objects.isNull(guideLineDocumentVO.getId()))
		{
			if (StringUtils.isNotBlank(guideLineDocumentVO.getTitle())
					&& StringUtils.isNotBlank(guideLineDocumentVO.getDocument().getOriginalFilename())
					&& Objects.isNull(guideLineDocumentVO.getId())) {

				/*Optional<GuideLineDocument> docOpt = guideLineDocumentRepository.isExistByTitleAndDocumentName(
						guideLineDocumentVO.getTitle(), guideLineDocumentVO.getDocument().getOriginalFilename());
				*/
				List<GuideLineDocument> docOpt = guideLineDocumentRepository.findByTitle(
						guideLineDocumentVO.getTitle());
				
				if (!docOpt.isEmpty()) {
					return Boolean.TRUE;
				}
			} else if (StringUtils.isNotBlank(guideLineDocumentVO.getTitle())
					&& StringUtils.isNotBlank(guideLineDocumentVO.getDocument().getOriginalFilename())
					&& Objects.nonNull(guideLineDocumentVO.getId())) {
				return Boolean.FALSE;

			}
		}
		
		if ( Objects.nonNull(guideLineDocumentVO.getId()))
		{
			List<GuideLineDocument> listTitle = guideLineDocumentRepository.findByTitle(
					guideLineDocumentVO.getTitle());
		
			for (GuideLineDocument GuideLineDocument : listTitle) {
				if(GuideLineDocument.getId()!=guideLineDocumentVO.getId())
				{
					if(GuideLineDocument.getTitle().equals(guideLineDocumentVO.getTitle()))
					{
						return Boolean.TRUE;
					}
				}
			}
		}
		return Boolean.FALSE;
	}

	private void savefile(String fullFileName, GuideLineDocumentRequestVO guideLineDocumentVO) {
		try {
			FileUtils.writeByteArrayToFile(new File(fullFileName), guideLineDocumentVO.getDocument().getBytes());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/*
	 * private User findLoggedUser() { User loggedUser = null; JwtUser jwtUser =
	 * Utils.getUserByToken(); if (Objects.nonNull(jwtUser) &&
	 * StringUtils.isNotBlank(jwtUser.getRole())) { loggedUser =
	 * userRepository.findById(jwtUser.getId()).orElse(null); } return loggedUser; }
	 */
	@SuppressWarnings("serial")
	private Specification<GuideLineDocument> createSearchClientSpecification() {
		Specification<GuideLineDocument> searchSpecification = new Specification<GuideLineDocument>() {
			@Override
			public Predicate toPredicate(Root<GuideLineDocument> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				Predicate predicateForDeletedDocument = criteriaBuilder.equal(root.get("deleted"), false);

				Predicate predicateForStatusDocument = criteriaBuilder.equal(root.get("status"), true);

				query.orderBy(criteriaBuilder.desc(root.get("id")));
				
				return criteriaBuilder.and(predicateForDeletedDocument, predicateForStatusDocument);
			}
		};
		return searchSpecification;
	}

	@Override
	public ResponseVO<Map<String, List<GuideLineDocumentResponseVO>>> getAllActiveGuidelineDocuments() {
		List<GuideLineDocumentResponseVO> documentVOs = new ArrayList<GuideLineDocumentResponseVO>();
		Specification<GuideLineDocument> searchSpecification = createSearchClientSpecification();
		List<GuideLineDocument> guideLineDocumentPage = guideLineDocumentRepository.findAll(searchSpecification);

		if (!CollectionUtils.isEmpty(guideLineDocumentPage)) {
			documentVOs = guideLineDocumentPage.stream().map(obj -> convertToVO(obj)).collect(Collectors.toList());

			return ResponseVO.<Map<String, List<GuideLineDocumentResponseVO>>>create(HttpStatus.OK.value(),
					ResponseStatus.SUCCESS.name(), StringUtils.EMPTY,
					Collections.singletonMap(GUIDELINE_DOCUMENT_KEY, documentVOs));

		} else {

			return ResponseVO.<Map<String, List<GuideLineDocumentResponseVO>>>create(HttpStatus.NO_CONTENT.value(),
					ResponseStatus.FAIL.name(), Messages.GUIDELINE_DOCUMENT_LIST_FAIL,
					Collections.singletonMap(GUIDELINE_DOCUMENT_KEY, null));
		}
	}

	@Override
	public ByteArrayInputStream guidelineDocumentDetails(String filename) {
		ByteArrayInputStream bais = null;

		try {
			String fullFileName = StringUtils
					.join(StringUtils.appendIfMissing(guideLinedocumentUploadPath, File.separator), filename);

			bais = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(fullFileName)));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return bais;
	}

	@Override
	public Page<GuideLineDocumentResponseVO> findAll(int pageNo, int pageSize, SearchDocumentVO searchDocumentVO) {
		
		Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("title").descending());
		
		Specification<GuideLineDocument> searchSpecification = createSearchSpecification(searchDocumentVO);
		
		
		Page<GuideLineDocument> guideLineDocumentPage = guideLineDocumentRepository.findAll(searchSpecification, pageRequest);
		
		
		return guideLineDocumentPage.map(obj -> convertToVO(obj));
	}
	
	@SuppressWarnings("serial")
	private Specification<GuideLineDocument> createSearchSpecification(SearchDocumentVO searchDocumentVO) {
		Specification<GuideLineDocument> searchSpecification = new Specification<GuideLineDocument>() {
		
			@Override
			public Predicate toPredicate(Root<GuideLineDocument> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicates = new ArrayList<>();
				if(Objects.nonNull(searchDocumentVO.getDocumentName())){
		            predicates.add(
		                    criteriaBuilder.like(root.get("documentName"),
		                            "%" + searchDocumentVO.getDocumentName() + "%")
		            );
		        }
		        if(Objects.nonNull(searchDocumentVO.getTitle())){
		            predicates.add(
		                    criteriaBuilder.like(root.get("title"),
		                            "%" + searchDocumentVO.getTitle() + "%")
		            );
		        }
		        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
			
		};
		return  searchSpecification;
	}

	
}

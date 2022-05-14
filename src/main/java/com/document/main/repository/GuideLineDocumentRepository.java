package com.document.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.document.main.core.GuideLineDocument;

@Repository
public interface GuideLineDocumentRepository
		extends JpaRepository<GuideLineDocument, Long>, JpaSpecificationExecutor<GuideLineDocument> {

	Optional<GuideLineDocument> findOneById(Long Id);

	@Query("SELECT acs FROM GuideLineDocument acs WHERE acs.title = :title AND acs.deleted = false")
	List<GuideLineDocument> findByTitle(@Param("title") String title);
}

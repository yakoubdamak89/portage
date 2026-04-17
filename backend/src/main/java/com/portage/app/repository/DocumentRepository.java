package com.portage.app.repository;

import com.portage.app.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByConsultantId(Long consultantId);
    List<Document> findByConsultantIdAndCategory(Long consultantId, String category);
}

package com.portage.app.service;

import com.portage.app.dto.request.ExpenseReportRequest;
import com.portage.app.dto.response.ExpenseReportResponse;

import java.util.List;

public interface ExpenseReportService {
    ExpenseReportResponse create(ExpenseReportRequest request);
    ExpenseReportResponse findById(Long id);
    List<ExpenseReportResponse> findAll();
    List<ExpenseReportResponse> findByConsultant(Long consultantId);
    ExpenseReportResponse update(Long id, ExpenseReportRequest request);
    ExpenseReportResponse submit(Long id);
    ExpenseReportResponse validate(Long id, Long validatedByUserId);
    ExpenseReportResponse reject(Long id);
    void delete(Long id);
}

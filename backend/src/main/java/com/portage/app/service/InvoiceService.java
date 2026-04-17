package com.portage.app.service;

import com.portage.app.dto.request.InvoiceRequest;
import com.portage.app.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    InvoiceResponse create(InvoiceRequest request);
    InvoiceResponse findById(Long id);
    List<InvoiceResponse> findAll();
    List<InvoiceResponse> findByConsultant(Long consultantId);
    InvoiceResponse update(Long id, InvoiceRequest request);
    InvoiceResponse generateFromTimesheet(Long timesheetId);
    InvoiceResponse send(Long id);
    InvoiceResponse markPaid(Long id);
    void delete(Long id);
}

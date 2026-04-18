package com.portage.app.service;

import com.portage.app.dto.request.PayslipRequest;
import com.portage.app.dto.response.PayslipResponse;

import java.util.List;

public interface PayslipService {
    PayslipResponse create(PayslipRequest request);
    PayslipResponse findById(Long id);
    List<PayslipResponse> findAll();
    List<PayslipResponse> findByConsultant(Long consultantId);
    PayslipResponse update(Long id, PayslipRequest request);
    PayslipResponse generate(Long consultantId, int month, int year);
    void delete(Long id);
}

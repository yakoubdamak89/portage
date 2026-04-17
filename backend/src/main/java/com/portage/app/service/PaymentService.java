package com.portage.app.service;

import com.portage.app.dto.request.PaymentRequest;
import com.portage.app.dto.response.PaymentResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    PaymentResponse create(PaymentRequest request);
    PaymentResponse findById(Long id);
    List<PaymentResponse> findAll();
    List<PaymentResponse> findByInvoice(Long invoiceId);
    PaymentResponse recordPayment(Long invoiceId, BigDecimal amount, String reference);
    void delete(Long id);
}

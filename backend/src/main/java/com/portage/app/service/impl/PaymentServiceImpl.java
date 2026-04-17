package com.portage.app.service.impl;

import com.portage.app.domain.entity.Invoice;
import com.portage.app.domain.entity.Payment;
import com.portage.app.domain.enums.InvoiceStatus;
import com.portage.app.dto.request.PaymentRequest;
import com.portage.app.dto.response.PaymentResponse;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.PaymentMapper;
import com.portage.app.repository.InvoiceRepository;
import com.portage.app.repository.PaymentRepository;
import com.portage.app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper mapper;

    @Override
    public PaymentResponse create(PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", request.getInvoiceId()));

        Payment payment = Payment.builder()
                .invoice(invoice)
                .amount(request.getAmount())
                .paymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now())
                .reference(request.getReference())
                .notes(request.getNotes())
                .build();

        payment = paymentRepository.save(payment);

        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        invoiceRepository.save(invoice);

        return mapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse findById(Long id) {
        return mapper.toResponse(paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        return mapper.toResponseList(paymentRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> findByInvoice(Long invoiceId) {
        return mapper.toResponseList(paymentRepository.findByInvoiceId(invoiceId));
    }

    @Override
    public PaymentResponse recordPayment(Long invoiceId, BigDecimal amount, String reference) {
        PaymentRequest request = new PaymentRequest();
        request.setInvoiceId(invoiceId);
        request.setAmount(amount);
        request.setReference(reference);
        request.setPaymentDate(LocalDate.now());
        return create(request);
    }

    @Override
    public void delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment", id);
        }
        paymentRepository.deleteById(id);
    }
}

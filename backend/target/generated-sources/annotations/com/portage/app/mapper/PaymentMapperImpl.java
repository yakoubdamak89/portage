package com.portage.app.mapper;

import com.portage.app.domain.entity.Invoice;
import com.portage.app.domain.entity.Payment;
import com.portage.app.dto.response.PaymentResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T23:32:19+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponse.PaymentResponseBuilder paymentResponse = PaymentResponse.builder();

        paymentResponse.invoiceId( paymentInvoiceId( payment ) );
        paymentResponse.invoiceNumber( paymentInvoiceInvoiceNumber( payment ) );
        paymentResponse.id( payment.getId() );
        paymentResponse.amount( payment.getAmount() );
        paymentResponse.paymentDate( payment.getPaymentDate() );
        paymentResponse.reference( payment.getReference() );
        paymentResponse.notes( payment.getNotes() );
        paymentResponse.createdAt( payment.getCreatedAt() );

        return paymentResponse.build();
    }

    @Override
    public List<PaymentResponse> toResponseList(List<Payment> payments) {
        if ( payments == null ) {
            return null;
        }

        List<PaymentResponse> list = new ArrayList<PaymentResponse>( payments.size() );
        for ( Payment payment : payments ) {
            list.add( toResponse( payment ) );
        }

        return list;
    }

    private Long paymentInvoiceId(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Invoice invoice = payment.getInvoice();
        if ( invoice == null ) {
            return null;
        }
        Long id = invoice.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String paymentInvoiceInvoiceNumber(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Invoice invoice = payment.getInvoice();
        if ( invoice == null ) {
            return null;
        }
        String invoiceNumber = invoice.getInvoiceNumber();
        if ( invoiceNumber == null ) {
            return null;
        }
        return invoiceNumber;
    }
}

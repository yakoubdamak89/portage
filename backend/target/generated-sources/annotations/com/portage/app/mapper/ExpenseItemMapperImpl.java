package com.portage.app.mapper;

import com.portage.app.domain.entity.ExpenseItem;
import com.portage.app.domain.entity.ExpenseReport;
import com.portage.app.dto.response.ExpenseItemResponse;
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
public class ExpenseItemMapperImpl implements ExpenseItemMapper {

    @Override
    public ExpenseItemResponse toResponse(ExpenseItem item) {
        if ( item == null ) {
            return null;
        }

        ExpenseItemResponse.ExpenseItemResponseBuilder expenseItemResponse = ExpenseItemResponse.builder();

        expenseItemResponse.expenseReportId( itemExpenseReportId( item ) );
        expenseItemResponse.id( item.getId() );
        expenseItemResponse.date( item.getDate() );
        expenseItemResponse.category( item.getCategory() );
        expenseItemResponse.description( item.getDescription() );
        expenseItemResponse.amount( item.getAmount() );
        expenseItemResponse.vatAmount( item.getVatAmount() );
        expenseItemResponse.receiptPath( item.getReceiptPath() );
        expenseItemResponse.createdAt( item.getCreatedAt() );

        return expenseItemResponse.build();
    }

    @Override
    public List<ExpenseItemResponse> toResponseList(List<ExpenseItem> items) {
        if ( items == null ) {
            return null;
        }

        List<ExpenseItemResponse> list = new ArrayList<ExpenseItemResponse>( items.size() );
        for ( ExpenseItem expenseItem : items ) {
            list.add( toResponse( expenseItem ) );
        }

        return list;
    }

    private Long itemExpenseReportId(ExpenseItem expenseItem) {
        if ( expenseItem == null ) {
            return null;
        }
        ExpenseReport expenseReport = expenseItem.getExpenseReport();
        if ( expenseReport == null ) {
            return null;
        }
        Long id = expenseReport.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

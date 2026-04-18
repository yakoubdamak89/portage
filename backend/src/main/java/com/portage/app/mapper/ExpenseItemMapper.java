package com.portage.app.mapper;

import com.portage.app.domain.entity.ExpenseItem;
import com.portage.app.dto.response.ExpenseItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseItemMapper {

    @Mapping(target = "expenseReportId", source = "expenseReport.id")
    ExpenseItemResponse toResponse(ExpenseItem item);

    List<ExpenseItemResponse> toResponseList(List<ExpenseItem> items);
}

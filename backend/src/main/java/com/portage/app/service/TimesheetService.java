package com.portage.app.service;

import com.portage.app.dto.request.TimesheetRequest;
import com.portage.app.dto.response.TimesheetResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TimesheetService {
    TimesheetResponse create(TimesheetRequest request);
    TimesheetResponse findById(Long id);
    List<TimesheetResponse> findAll();
    List<TimesheetResponse> findByConsultant(Long consultantId);
    TimesheetResponse update(Long id, TimesheetRequest request);
    TimesheetResponse submit(Long id);
    TimesheetResponse validate(Long id, Long validatedByUserId);
    TimesheetResponse reject(Long id, String comments);
    BigDecimal calculateCA(Long consultantId, int month, int year);
    void delete(Long id);
}

package com.emp.management.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.emp.management.dto.request.LeaveRequestDTO;
import com.emp.management.dto.response.LeaveResponse;
import com.emp.management.entities.Employee;
import com.emp.management.entities.LeaveRequest;
import com.emp.management.entities.LeaveStatus;
import com.emp.management.exceptions.ResourceNotFoundExceptions;
import com.emp.management.mapper.LeaveRequestMapper;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.repository.LeaveRequestRepository;
import com.emp.management.service.LeaveRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

        private final LeaveRequestRepository leaveRequestRepository;

        private final EmployeeRepository employeeRepository;

        @Override
        @PreAuthorize("hasRole('EMPLOYEE')")
        public LeaveResponse applyLeave(LeaveRequestDTO requestDTO) {
                Employee employee = this.employeeRepository.findById(requestDTO.getEmployeeId())
                                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));

                LeaveRequest entity = LeaveRequestMapper.toEntity(requestDTO, employee);
                entity.setStatus(LeaveStatus.PENDING);
                entity.setEmployee(employee);
                LeaveRequest leaveRequestSaved = this.leaveRequestRepository.save(entity);
                return LeaveRequestMapper.toResponse(leaveRequestSaved);
        }

        @Override
        @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or " +
                        "(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
        public List<LeaveResponse> getLeavesByEmployee(String employeeId) {
                return this.leaveRequestRepository.findByEmployee_EmployeeId(employeeId)
                                .stream()
                                .map(LeaveRequestMapper::toResponse)
                                .collect(
                                                Collectors.toList());
        }

        @Override
        @PreAuthorize("hasAnyRole('ADMIN','HR')")
        public List<LeaveResponse> getLeavesByStatus(LeaveStatus status) {
                return this.leaveRequestRepository.findByStatus(status)
                                .stream()
                                .map(LeaveRequestMapper::toResponse)
                                .collect(
                                                Collectors.toList());
        }

        @Override
        @PreAuthorize("hasAnyRole('ADMIN','HR')")
        public LeaveResponse approveLeave(String leaveId) {
                LeaveRequest leaveRequest = this.leaveRequestRepository.findById(leaveId)
                                .orElseThrow(() -> new ResourceNotFoundExceptions("Leave request not found !"));
                leaveRequest.setStatus(LeaveStatus.APPROVED);
                LeaveRequest leaveRequestApproved = this.leaveRequestRepository.save(leaveRequest);
                return LeaveRequestMapper.toResponse(leaveRequestApproved);
        }

        @Override
        @PreAuthorize("hasAnyRole('ADMIN','HR')")
        public LeaveResponse rejectLeave(String leaveId) {
                LeaveRequest leaveRequest = this.leaveRequestRepository.findById(leaveId)
                                .orElseThrow(() -> new ResourceNotFoundExceptions("Leave Request not found !"));

                leaveRequest.setStatus(LeaveStatus.REJECTED);
                LeaveRequest leaveRequestRejected = this.leaveRequestRepository.save(leaveRequest);
                return LeaveRequestMapper.toResponse(leaveRequestRejected);
        }

}

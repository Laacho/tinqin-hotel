package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportInput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportOperation;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemRepostOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemReportOperationProcessor implements SystemReportOperation {

    @Override
    public Either<ErrorWrapper, SystemRepostOutput> process(SystemReportInput input) {



        return null;
    }
}

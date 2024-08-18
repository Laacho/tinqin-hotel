package com.tinqinacademy.hotel.core.services.implementations.systemImpl;

import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.Data;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportInput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportOperation;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemRepostOutput;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.core.services.specifications.SpecificationUtils;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.repository.interfaces.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tinqinacademy.hotel.core.services.specifications.GuestSpecification.*;

@Service
@Slf4j
public class SystemReportOperationProcessor extends BaseOperationProcessor implements SystemReportOperation {
    private final GuestRepository guestRepository;
    private final ConversionService conversionService;

    public SystemReportOperationProcessor(Validator validator, ErrorHandler errorHandler, GuestRepository guestRepository, ConversionService conversionService) {
        super(validator, errorHandler);
        this.guestRepository = guestRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<ErrorWrapper, SystemRepostOutput> process(SystemReportInput input) {
        log.info("Start systemReportOperation input: {}", input);

        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                    List<Specification<Guest>> predicates = getSpecifications(input);

                    Specification<Guest> specification = SpecificationUtils.combineSpecifications(predicates);

                    Set<Guest> specifiedGuests = new HashSet<>(guestRepository.findAll(specification));

                    List<Guest> allGuests = guestRepository.findByDateRangeAndRoomNumber(
                            input.getStartDate(), input.getEndDate(), input.getRoomNumber()
                    );


                    List<Guest> filteredGuests = allGuests.stream()
                            .filter(specifiedGuests::contains)
                            .toList();

                    List<Data> guestInfo = filteredGuests.stream()
                            .map(guest -> conversionService.convert(guest, Data.class))
                            .toList();

                    SystemRepostOutput output = outputBuilder(guestInfo);

                    log.info("End getRegisterInfo output: {}", output);
                    return output;
                }).toEither()
                .mapLeft(errorHandler::handleError));
    }

    private SystemRepostOutput outputBuilder(List<Data> guestInfo) {
        return SystemRepostOutput.builder()
                .data(guestInfo)
                .build();
    }

    private @NotNull List<Specification<Guest>> getSpecifications(SystemReportInput input) {
        return new ArrayList<>() {{
            add(guestHasFirstName(input.getFirstName()));
            add(guestHasLastName(input.getLastName()));
            add(guestHasPhoneNumber(input.getPhoneNumber()));
            add(guestHasIdCardNumber(input.getIdCardNumber()));
            add(guestHasIdCardValidity(input.getIdCardValidity()));
            add(guestHasIdCardIssueDate(input.getIdCardIssueDate()));
            add(guestHasIdCardIssueAuthority(input.getIdCardIssueAuthority()));
            add(guestHasBirthdate(input.getBirthdate()));
        }};
    }
}

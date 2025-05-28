package com.internship.paybycard.paymentprocess.authentication.domain.mapper;

import com.internship.paybycard.paymentprocess.authentication.core.domain.mapper.RegistrationDetailsMapper;
import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import com.internship.paybycard.paymentprocess.authentication.domain.dto.RegistrationDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegistrationDetailsMapperImpl implements RegistrationDetailsMapper {
    @Override
    public RegistrationDetailsImpl toRegistrationDetails(RegistrationCommand command) {
        log.debug("mapping registration command toRegistrationDetails username={}", command.getUsername());
        RegistrationDetailsImpl registrationDetails = new RegistrationDetailsImpl();
        registrationDetails.setUsername(command.getUsername());
        registrationDetails.setEmail(command.getEmail());
        return registrationDetails;
    }
}

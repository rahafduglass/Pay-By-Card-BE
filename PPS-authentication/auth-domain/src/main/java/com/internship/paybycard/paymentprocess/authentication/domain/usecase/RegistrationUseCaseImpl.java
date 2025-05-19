package com.internship.paybycard.paymentprocess.authentication.domain.usecase;


import com.internship.paybycard.paymentprocess.authentication.core.domain.usecase.RegistrationUseCase;
import com.internship.paybycard.paymentprocess.authentication.core.persistence.SysUserDao;
import com.internship.paybycard.paymentprocess.authentication.core.presentation.command.RegistrationCommand;
import com.internship.paybycard.paymentprocess.authentication.domain.dto.RegistrationDetailsImpl;
import com.internship.paybycard.paymentprocess.authentication.domain.mapper.RegistrationDetailsMapper;
import com.internship.paybycard.paymentprocess.core.domain.result.ErrorCode;
import com.internship.paybycard.paymentprocess.core.domain.result.Result;
import com.internship.paybycard.paymentprocess.core.domain.result.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class RegistrationUseCaseImpl implements RegistrationUseCase {
    private final PasswordEncoder passwordEncoder;
    private final SysUserDao sysUserDao;
    private final RegistrationDetailsMapper registrationDetailsMapper;

    @Override
    public Result<Void> register(RegistrationCommand command) {
        try {
            log.info("Registering user {}", command.getUsername());
            log.debug("mapping command with username= {} to registration details", command.getUsername());
            RegistrationDetailsImpl registrationDetails = registrationDetailsMapper.toRegistrationDetails(command);
            registrationDetails.setPassword(passwordEncoder.encode(command.getPassword()));
            log.info("saving user with username = {} to database via SysUserDao", command.getUsername());
            sysUserDao.save(registrationDetails);
            return new Result<>(Status.ACT, ErrorCode.SUCCESS, null);
        } catch (Exception e) {
            log.error("unexpected error occurred while registering user: {}", e.getMessage(), e);
            return new Result<>(Status.RJC, ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }
}
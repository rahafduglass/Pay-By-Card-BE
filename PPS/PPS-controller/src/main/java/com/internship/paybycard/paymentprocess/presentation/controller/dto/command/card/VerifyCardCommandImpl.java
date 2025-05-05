package com.internship.paybycard.paymentprocess.presentation.controller.dto.command.card;

import com.internship.paybycard.paymentprocess.core.domain.dto.command.card.VerifyCardCommand;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class VerifyCardCommandImpl implements VerifyCardCommand {

    private String cardNumber;
    private String CVV;
    private LocalDate expiryDate;
}

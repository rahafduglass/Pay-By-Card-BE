package com.internship.paybycard.paymentprocess.presentation.controller.dto.command.card;

import com.internship.paybycard.paymentprocess.core.domain.dto.card.command.VerifyCardCommand;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
//todo Add validation rules (Mandatory/Optional | Maximum Length as well)

public class VerifyCardCommandImpl implements VerifyCardCommand {

    private String cardNumber;
    private String CVV;
    private LocalDate expiryDate;
}

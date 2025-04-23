package com.internship.paybycard.configuration.domain;

import com.internship.paybycard.core.logger.CardServiceLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardServiceLoggerImpl implements CardServiceLogger {

    private final org.slf4j.Logger LOGGER= LoggerFactory.getLogger(CardServiceLoggerImpl.class);

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }

    @Override
    public void debug(String message) {
        LOGGER.debug(message);
    }
}

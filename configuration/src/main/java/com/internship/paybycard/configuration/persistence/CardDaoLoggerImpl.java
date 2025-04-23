package com.internship.paybycard.configuration.persistence;

import com.internship.paybycard.core.logger.CardDaoLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardDaoLoggerImpl implements CardDaoLogger {
    private final Logger LOGGER= LoggerFactory.getLogger(CardDaoLoggerImpl.class);

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

package com.internship.paybycard.paymentprocess.integration.cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cms.api")
@Data
public class CmsApiProperties {

    private String baseUrl;

}

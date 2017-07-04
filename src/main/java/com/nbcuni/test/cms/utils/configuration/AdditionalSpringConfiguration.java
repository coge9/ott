package com.nbcuni.test.cms.utils.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dzianis_Kulesh on 4/19/2017.
 */
@Configuration
public class AdditionalSpringConfiguration {

    @Bean
    public static AddExtraCapabilitiesPostProcessor myBeanPostProcessor() {
        return new AddExtraCapabilitiesPostProcessor();
    }

}

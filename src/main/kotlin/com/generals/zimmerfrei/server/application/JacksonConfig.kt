package com.generals.zimmerfrei.server.application

import com.fasterxml.jackson.datatype.threetenbp.ThreeTenModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun threeTenModule(): ThreeTenModule = ThreeTenModule()
}
package ru.rus.crud.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("ru.rus.crud")
@EntityScan("ru.rus.crud.domain.entity")
@EnableJpaRepositories("ru.rus.crud.repository")
public class ConfigBean {
}

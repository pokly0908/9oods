package com.kuku9.goods.global.config;

import com.querydsl.jpa.impl.*;
import jakarta.persistence.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}

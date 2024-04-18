package com.kuku9.goods.domain.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {

    private String clientId;

    private String clientSecret;

    private String redirectUrl;

    private String apiUrl;

    private String authUrl;


}

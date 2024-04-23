package com.kuku9.goods.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuku9.goods.domain.auth.config.KakaoProperties;
import com.kuku9.goods.domain.auth.dto.KakaoUserInfo;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.repository.UserRepository;
import com.kuku9.goods.global.security.jwt.JwtUtil;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KakaoProperties kakaoProperties;

    @Transactional
    public String login(String code) throws JsonProcessingException {
        String kakaoAccessToken = getKakaoAccessToken(code);
        KakaoUserInfo kakaoUserInfo = getkakaoUserInfo(kakaoAccessToken);
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
        return jwtUtil.createAccessToken(kakaoUser.getEmail(), kakaoUser.getRole());
    }

    public synchronized String getKakaoAccessToken(String code) throws JsonProcessingException {
        URI uri = UriComponentsBuilder.fromUriString(kakaoProperties.getAuthUrl() + "/oauth/token").encode().build().toUri();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoProperties.getClientId());
        body.add("redirect_uri", kakaoProperties.getRedirectUrl());
        body.add("code", code);
        body.add("client_secret", kakaoProperties.getClientSecret());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        ResponseEntity<String> response = restTemplate.exchange(
            uri,
            HttpMethod.POST,
            new HttpEntity<>(body, headers),
            String.class
        );

        String responseBody = response.getBody();

        if (responseBody == null) {
            throw new RuntimeException("Kakao response body is null");
        }

        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);

        if (jsonNode.has("error")) {
            String error = jsonNode.get("error").asText();
            String errorDescription = jsonNode.get("error_description").asText();
            throw new RuntimeException("Failed to get Kakao access token. Error: " + error + ", Description: " + errorDescription);
        }

        if (jsonNode.get("access_token") == null) {
            throw new RuntimeException("Failed to get Kakao access token. Response: " + responseBody);
        }

        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfo getkakaoUserInfo(String kakaoAccessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/json;charset=utf-8");

        ResponseEntity<String> response = restTemplate.exchange(
            kakaoProperties.getApiUrl() + "/v2/user/me",
            HttpMethod.GET,
            new HttpEntity<>(headers),
            String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long kakaoId = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();
        return KakaoUserInfo.from(kakaoId, nickname, email);
    }

    private User registerKakaoUserIfNeeded(KakaoUserInfo kakaoUserInfo) {
        Long kakaoId = kakaoUserInfo.getKakaoId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

        if (kakaoUser == null) {
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);
                String email = kakaoUserInfo.getEmail();
                kakaoUser = User.fromKakao(email, encodedPassword, kakaoUserInfo.getNickname(), kakaoId);
            }
            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }
}

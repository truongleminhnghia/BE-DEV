package org.project.jwtsecurity_spring.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.project.jwtsecurity_spring.configs.CustomerUserDetail;
import org.project.jwtsecurity_spring.dtos.enums.EnumRoleName;
import org.project.jwtsecurity_spring.dtos.enums.Type_Login;
import org.project.jwtsecurity_spring.dtos.requests.Oauth2Request;
import org.project.jwtsecurity_spring.entities.Role;
import org.project.jwtsecurity_spring.entities.User;
import org.project.jwtsecurity_spring.jwt.JwtService;
import org.project.jwtsecurity_spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@Service
public class Oauth2Service implements IOauth2Service {

    // GOOGLE
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientIdGoogle;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecretGoogle;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUriGoogle;
    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String scopesGoogle;
    @Value("${flow.name}")
    private String flowNameGoogle;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfo;

    @Autowired private UserService userService;
    @Autowired private TokenService tokenService;
    @Autowired private RoleService roleService;
    @Autowired
    private JwtService jwtService;
    @Autowired private UserRepository userRepository;

    @Override
    public String generateUrl(String type) throws UnsupportedEncodingException {
        if (type.equals(Type_Login.GOOGLE.name())) {
            String state = UUID.randomUUID().toString();
            String nonce = UUID.randomUUID().toString();
            String encodeState = URLEncoder.encode(state, "UTF-8");
            String encodeNonce = URLEncoder.encode(nonce, "UTF-8");

            String url = "https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?" +
                    "response_type=code&" +
                    "client_id=" + clientIdGoogle + "&" +
                    "scope=" + scopesGoogle + "&" +
                    "state=" + encodeState + "&" +
                    "nonce=" + encodeNonce + "&" +
                    "redirect_uri=" + redirectUriGoogle + "&" + flowNameGoogle;
            return url;
        }
        return null;
    }

    @Override
    public Map<String, Object> authenticateAndFetchProfile(String code, String type) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String asscessToken;
         switch (type.trim().toUpperCase()) {
             case "GOOGLE":
                 asscessToken = new GoogleAuthorizationCodeTokenRequest(
                         new NetHttpTransport(), new GsonFactory(),
                         clientIdGoogle,
                         clientSecretGoogle,
                         code,
                         redirectUriGoogle
                 ).execute().getAccessToken();
                 restTemplate.getInterceptors().add((request, body, execution) -> {
                     request.getHeaders().set("Authorization", "Bearer " + asscessToken);
                     return execution.execute(request, body);
                 });
                 return new ObjectMapper().readValue(
                         restTemplate.getForEntity(googleUserInfo, String.class).getBody(),
                         new TypeReference<>() {});
             case "FACEBOOK":
                 throw new UnsupportedOperationException("Facebook authentication not implemented yet.");
             default:
                 throw new IllegalArgumentException("Unsupported authentication type: " + type);
         }
    }

    @Override
    public String loginOauth2(Oauth2Request request) {
        boolean result = userRepository.existsByEmail(request.getEmail());
        Role role = roleService.getRole(EnumRoleName.ROLE_USER);
        if (!result) {
            User user = User.builder()
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .firstName(request.getFullName())
                    .phone(request.getPhoneNumber())
                    .avatar(request.getAvatar())
                    .role(role)
                    .build();
            userRepository.save(user);
            CustomerUserDetail customerUserDetail = CustomerUserDetail.mapUserToUserDetail(user);
            String tokeRes = jwtService.generateToken(customerUserDetail);
            return tokeRes;
        } else {
            User user = userRepository.findByEmail(request.getEmail());
            CustomerUserDetail customerUserDetail = CustomerUserDetail.mapUserToUserDetail(user);
            String tokeRes = jwtService.generateToken(customerUserDetail);
            return tokeRes;
        }
    }
}

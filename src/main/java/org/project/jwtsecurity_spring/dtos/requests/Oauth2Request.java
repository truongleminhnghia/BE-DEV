package org.project.jwtsecurity_spring.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Oauth2Request {

    private String fullName;
    private String googleAccountId;
    private String facebookAccountId;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatar;
}

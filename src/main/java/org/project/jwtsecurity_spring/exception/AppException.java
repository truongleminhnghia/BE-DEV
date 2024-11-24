package org.project.jwtsecurity_spring.exception;

import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.ErrorCode;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppException extends RuntimeException{
    private ErrorCode errorCode;

//    public AppException(ErrorCode errorCode) {
//        super(errorCode.getMessage());
//        this.errorCode = errorCode;
//    }
}

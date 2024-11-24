package org.project.jwtsecurity_spring.dtos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public enum ErrorCode {
    USER_EXISTED(HttpStatus.BAD_REQUEST, "User existed"),
    USER_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "User does not exist"),
    USERID_NULL_OR_EMPTY(HttpStatus.BAD_REQUEST, "User id is null or empty"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "Password must be greater than 8 characters"),
    PASSWORD_NOT_NULL(HttpStatus.BAD_REQUEST, "Password must not be null"),
    PASSWORD_NOT_BLANK(HttpStatus.BAD_REQUEST, "Password must not be blank"),
    EMAIL_NOT_NULL(HttpStatus.BAD_REQUEST, "Email must not be null"),
    EMAIL_NOT_BLANK(HttpStatus.BAD_REQUEST, "Email must not be blank"),
    EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "Email must contain at least one special character"),
    PRODUCT_NOT_EXIST(HttpStatus.NOT_FOUND, "Product do not exists"),
    PRODUCT_NAME_NOT_BLANK(HttpStatus.BAD_REQUEST, "Product name must not be blank"),
    PRODUCT_NAME_NOT_NULL(HttpStatus.BAD_REQUEST, "Product name must not be null"),
    PRICE_NOT_NULL(HttpStatus.BAD_REQUEST, "Price must not be null"),
    PRICE_NOT_BLANK(HttpStatus.BAD_REQUEST, "Price must not be blank"),
    PRICE_INVALID(HttpStatus.BAD_REQUEST, "Price must be greater than or equal to 0"),
    QUANTITY_NOT_NULL(HttpStatus.BAD_REQUEST, "Quantity must not be null"),
    QUANTITY_NOT_BLANK(HttpStatus.BAD_REQUEST, "Quantity must not be blank"),
    QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0"),
    USERNAME_OR_PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "Username or password invalid"),
    ORDER_DO_NOT_EXIST(HttpStatus.NOT_FOUND, "Order do not exists"),
    ROLE_NOT_NULL(HttpStatus.BAD_REQUEST, "role name not null"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    LIST_EMPTY(HttpStatus.BAD_REQUEST, "List is empty"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Token invalid"),
    INVALID_PRINCIPAL(HttpStatus.BAD_REQUEST, "INVALID_PRINCIPAL");

    private HttpStatus code;
    private String message;

}

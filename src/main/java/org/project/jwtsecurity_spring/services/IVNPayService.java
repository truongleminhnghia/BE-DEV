package org.project.jwtsecurity_spring.services;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface IVNPayService {

    public String getVNPay(long total, HttpServletRequest request, String orderId) throws UnsupportedEncodingException;
}

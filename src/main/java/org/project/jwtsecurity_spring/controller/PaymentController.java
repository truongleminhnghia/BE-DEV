package org.project.jwtsecurity_spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.project.jwtsecurity_spring.dtos.enums.EnumPaymentMethod;
import org.project.jwtsecurity_spring.dtos.requests.PaymentRequest;
import org.project.jwtsecurity_spring.dtos.responses.ApiResponse;
import org.project.jwtsecurity_spring.dtos.responses.TransactionStatusRes;
import org.project.jwtsecurity_spring.entities.Order;
import org.project.jwtsecurity_spring.services.IOrderService;
import org.project.jwtsecurity_spring.services.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired private IOrderService orderService;
    @Autowired private VNPayService VNPayService;

    @PostMapping("/create-payment")
    public ResponseEntity<ApiResponse> createPayment(@RequestBody PaymentRequest paymentRequest, HttpServletRequest req) {
        try {
            Order order = orderService.getById(paymentRequest.getOrderId());
            if (paymentRequest.getPaymentMethod().equals(EnumPaymentMethod.VNPAY)) {
                long amount = (long) (order.getTotalPrice() * 100);
                String link = VNPayService.getVNPay(amount,req, order.getId());
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "success", link));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, e.getMessage(), null));
        }
        return null;
    }

    @GetMapping("/payment-info")
    public ResponseEntity<?> transction(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_ResponseCode") String resCode
    ) {
        if (resCode.equals("00")) {
            return ResponseEntity.status(HttpStatus.OK).body(new TransactionStatusRes("OK", "Success", ""));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionStatusRes("BAD", "FAILED", ""));
        }
    }

}

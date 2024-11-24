package org.project.jwtsecurity_spring.services;

import jakarta.servlet.http.HttpServletRequest;
import org.project.jwtsecurity_spring.configs.VNPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService implements IVNPayService {

    @Value("${vnpay.vnp_PayUrl}")
    private String vnp_PayUrl_Va;
    @Value("${vnpay.vnp_ReturnUrl}")
    private String vnp_ReturnUrl_Va;
    @Value("${vnpay.vnp_TmnCode}")
    private String vnp_TmnCode_Va;
    @Value("${vnpay.secretKey}")
    private String secretKey_Va;
    @Value("${vnpay.vnp_ApiUrl}")
    private String vnp_ApiUrl_Va;
    @Value("${vnpay.vnp_BankCode}")
    private String vnp_BankCode_Va;
    @Value("${vnpay.vnp_OrderType}")
    private String vnp_OrderType_Va;
    @Value("${vnpay.vnp_Command}")
    private String vnp_Command_Va;
    @Value("${vnpay.vnp_Version}")
    private String vnp_Version_Va;
    @Value("${vnpay.currency}")
    private String vnp_Currency_Va;
    @Value("${vnpay.timeZone}")
    private String vnp_TimeZone_Va;
    @Value("${vnpay.formatDate}")
    private String vnp_FormatDate_Va;
    @Override
    public String getVNPay(long total, HttpServletRequest request, String orderId) throws UnsupportedEncodingException {
        String vnp_Version = vnp_Version_Va;
        String vnp_Command = vnp_Command_Va;
        String orderType = vnp_OrderType_Va;
        String bankCode = vnp_BankCode_Va;
        long amount = total;
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_TmnCode = vnp_TmnCode_Va;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", vnp_Currency_Va);
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", orderId);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + orderId);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl_Va);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone(vnp_TimeZone_Va));
        SimpleDateFormat formatter = new SimpleDateFormat(vnp_FormatDate_Va);
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data (chèn chuỗi với append để tạo thành chuỗi )
                hashData.append(fieldName);                                                             // lấy ra field name
                hashData.append('=');                                                                   // gán dấu =
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));   // gán giá trị cho field
                // hashData : vnp_Amount=10000000
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));       // tạo ra query tên là field
                query.append('=');                                                                      // gán dấu =
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));      // gán giá trị cho query
                // query : vnp_Amount=10000000
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(secretKey_Va, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnp_PayUrl_Va + "?" + queryUrl;
        return paymentUrl;
    }
}

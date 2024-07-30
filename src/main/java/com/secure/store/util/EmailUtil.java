package com.secure.store.util;

import com.secure.store.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public class EmailUtil {
    public static String buildEmailContent(HttpServletRequest httpServletRequest, User user, String token) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Password Reset</title>\n" +
                "    <style>\n" +
                "        .container {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #f4f4f4;\n" +
                "            border: 1px solid #ddd;\n" +
                "            border-radius: 10px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            margin: 20px 0;\n" +
                "            background-color: #007bff;\n" +
                "            color: white;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        .button:hover {\n" +
                "            background-color: #0056b3;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 12px;\n" +
                "            color: #555;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h2>Password Reset Request</h2>\n" +
                "        <p>Hello "+user.getFirstName()+",</p>\n" +
                "        <p>We received a request to reset your password. Click the button below to reset your password:</p>\n" +
                "        <a href=\"" + TokenUtil.getApplicationUrl(httpServletRequest) + "/reset/password/change?token="+token+"\" class=\"button\">Reset Password</a>\n" +
                "        <p>If you did not request a password reset, please ignore this email or contact support if you have questions.</p>\n" +
                "        <p>Thank you,<br>The SecureStore Team</p>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>If you're having trouble clicking the \"Reset Password\" button, copy and paste the URL below into your web browser:</p>\n" +
                "            <p>" + TokenUtil.getApplicationUrl(httpServletRequest) + "/reset/password/change?token="+token + "</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}

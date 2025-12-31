package com.hihat.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ViewModelAdvice {

    @ModelAttribute
    public void addBaseUrl(Model model, HttpServletRequest request) {
        if (request == null) {
            model.addAttribute("baseUrl", "");
            model.addAttribute("requestPath", "");
            return;
        }

        String scheme = firstForwardedValue(request, "X-Forwarded-Proto");
        if (scheme == null || scheme.isBlank()) {
            scheme = request.getScheme();
        }

        String host = firstForwardedValue(request, "X-Forwarded-Host");
        if (host == null || host.isBlank()) {
            host = request.getServerName();
        }
        boolean hostHasPort = host.contains(":");

        int port = request.getServerPort();
        String forwardedPort = firstForwardedValue(request, "X-Forwarded-Port");
        if (forwardedPort != null && !forwardedPort.isBlank()) {
            try {
                port = Integer.parseInt(forwardedPort);
            } catch (NumberFormatException ignored) {
                // Fall back to server port.
            }
        }

        String portSuffix = (!hostHasPort && port != 80 && port != 443) ? ":" + port : "";
        model.addAttribute("baseUrl", scheme + "://" + host + portSuffix);
        model.addAttribute("requestPath", request.getRequestURI());
    }

    private String firstForwardedValue(HttpServletRequest request, String headerName) {
        String value = request.getHeader(headerName);
        if (value == null) {
            return null;
        }
        int commaIndex = value.indexOf(',');
        return (commaIndex >= 0) ? value.substring(0, commaIndex).trim() : value.trim();
    }
}

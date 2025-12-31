package com.hihat.blog.controller;

import com.hihat.blog.domain.Article;
import com.hihat.blog.repository.BlogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeoController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private final BlogRepository blogRepository;

    @GetMapping(value = "/robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String robots(HttpServletRequest request) {
        String baseUrl = resolveBaseUrl(request);
        return "User-agent: *\n" +
                "Allow: /\n" +
                "Sitemap: " + baseUrl + "/sitemap.xml\n";
    }

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String sitemap(HttpServletRequest request) {
        String baseUrl = resolveBaseUrl(request);
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        appendUrl(xml, baseUrl + "/", null);
        appendUrl(xml, baseUrl + "/articles?type=" + encodeParam("이론정리"), null);
        appendUrl(xml, baseUrl + "/articles?type=" + encodeParam("문제풀이"), null);

        List<Article> articles = blogRepository.findAll();
        for (Article article : articles) {
            LocalDateTime lastmod = article.getUpdatedAt() != null ? article.getUpdatedAt() : article.getCreatedAt();
            appendUrl(xml, baseUrl + "/articles/" + article.getId(), lastmod);
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    private void appendUrl(StringBuilder xml, String loc, LocalDateTime lastmod) {
        xml.append("<url>");
        xml.append("<loc>").append(escapeXml(loc)).append("</loc>");
        if (lastmod != null) {
            xml.append("<lastmod>").append(lastmod.format(DATE_FORMAT)).append("</lastmod>");
        }
        xml.append("</url>");
    }

    private String resolveBaseUrl(HttpServletRequest request) {
        String scheme = headerValue(request, "X-Forwarded-Proto");
        String host = headerValue(request, "X-Forwarded-Host");

        if (scheme == null || scheme.isBlank()) {
            scheme = request.getScheme();
        }

        if (host == null || host.isBlank()) {
            host = request.getHeader("Host");
        }

        if (host == null || host.isBlank()) {
            host = request.getServerName();
            int port = request.getServerPort();
            if (("http".equalsIgnoreCase(scheme) && port != 80) || ("https".equalsIgnoreCase(scheme) && port != 443)) {
                host = host + ":" + port;
            }
        }

        return scheme + "://" + host;
    }

    private String encodeParam(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String escapeXml(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private String headerValue(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        return value == null ? null : value.split(",")[0].trim();
    }
}

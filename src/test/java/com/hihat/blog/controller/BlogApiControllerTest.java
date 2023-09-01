package com.hihat.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hihat.blog.domain.Article;
import com.hihat.blog.domain.User;
import com.hihat.blog.dto.AddArticleRequest;
import com.hihat.blog.dto.GetArticleRequest;
import com.hihat.blog.dto.UpdateArticleRequest;
import com.hihat.blog.repository.BlogRepository;
import com.hihat.blog.repository.RefreshTokenRepository;
import com.hihat.blog.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc   // MockMvc 생성 및 자동 구성
@ActiveProfiles({"test"})
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;    // 직렬화, 역직렬화를 위한 클래스 (Jackson 라이브러리)

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    User user;

    @BeforeEach
    public void setSecurityContext() {
        user = User.builder()
                .email("user@email.com")
                .password("test")
                .nickname("user")
                .build();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return;
        }
        user = userRepository.save(user);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void after() {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            userRepository.delete(user);
        }
        if (refreshTokenRepository.findByUserId(user.getId()).isPresent()) {
            refreshTokenRepository.deleteByUserId(user.getId());
        }
    }



    @Test
    @DisplayName("addArticle : 블로그 글 추가")
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final String type = "type";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content, type);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAllByAuthor(principal.getName());

        assertThat(articles.size()).isEqualTo(1);   // 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

        removeDefaultArticle(articles.get(0));
    }

    @Test
    @DisplayName("findAllArticles : 블로그 글 전체 목록 조회")
    public void findAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        Article savedArticle = createDefaultArticle();
        final String type = "type";
        final GetArticleRequest userRequest = new GetArticleRequest(type);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        ResultActions result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()))
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()));

        removeDefaultArticle(savedArticle);
    }

    @Test
    @DisplayName("findArticle : 블로그 글 조회")
    public void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        ResultActions result = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()))
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()));

        removeDefaultArticle(savedArticle);
    }

    @Test
    @DisplayName("deleteArticle : 블로그 글 삭제")
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        ResultActions result = mockMvc.perform(delete(url, savedArticle.getId()));

        // then
        result.andExpect(status().isOk());

        Optional<Article> article = blogRepository.findById(savedArticle.getId());
        assertThat(article).isEmpty();
    }

    @Test
    @DisplayName("updateArticle : 블로그 글 수정")
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        final String newTitle = "new title";
        final String newContent = "new content";
        final String newType = "new type";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent, newType);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

        removeDefaultArticle(savedArticle);
    }

    private Article createDefaultArticle() {
        return blogRepository.save(Article.builder()
                .title("title")
                .author(user.getUsername())
                .content("content")
                .type("type")
                .build());
    }

    private void removeDefaultArticle(Article articles) {
        blogRepository.deleteById(articles.getId());
    }
}
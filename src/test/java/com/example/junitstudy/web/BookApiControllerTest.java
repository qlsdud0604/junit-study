package com.example.junitstudy.web;

import com.example.junitstudy.dto.request.BookSaveReqDto;
import com.example.junitstudy.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)   // 랜덤 포트를 이용한 통합 테스트
public class BookApiControllerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    TestRestTemplate rt;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @DisplayName("책 저장 기능 통합 테스트")
    @Test
    public void saveBookTest() throws Exception {
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("테스트 제목");
        bookSaveReqDto.setAuthor("테스트 작가");

        String body = om.writeValueAsString(bookSaveReqDto);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange(
                "/api/v1/book",
                HttpMethod.POST,
                request,
                String.class
        );

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo(bookSaveReqDto.getTitle());
        assertThat(author).isEqualTo(bookSaveReqDto.getAuthor());
    }

    @DisplayName("책 목록 조회 기능 통합 테스트")
    @Test
    public void selectBookList() {
        // given


        // when

        // then
    }

}

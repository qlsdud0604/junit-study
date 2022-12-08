package com.example.junitstudy.web;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.dto.request.BookSaveReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)   // 랜덤 포트를 이용한 통합 테스트
public class BookApiControllerTest {

    private static final Integer SUCCESS_CODE = 1;
    private static final String SUCCESS_SELECT_BOOK_LIST_MSG = "책 목록 조회 성공";
    private static final String SUCCESS_DELETE_BOOK_MSG = "책 삭제 성공";
    private static final String EXPECTED_BOOK_TITLE = "테스트 책 제목";
    private static final String EXPECTED_BOOK_AUTHOR = "테스트 책 작가";

    @Autowired
    private BookRepository bookRepository;

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

    @BeforeEach
    public void saveBookBeforeTest() {
        String title = EXPECTED_BOOK_TITLE;
        String author = EXPECTED_BOOK_AUTHOR;
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
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
    public void selectBookListTest() {
        // given

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange(
                "/api/v1/book",
                HttpMethod.GET,
                request,
                String.class
        );

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String title = dc.read("$.body.items[0].title");
        String author = dc.read("$.body.items[0].author");

        assertThat(code).isEqualTo(SUCCESS_CODE);
        assertThat(msg).isEqualTo(SUCCESS_SELECT_BOOK_LIST_MSG);
        assertThat(title).isEqualTo(EXPECTED_BOOK_TITLE);
        assertThat(author).isEqualTo(EXPECTED_BOOK_AUTHOR);
    }

    @Sql("classpath:db/tableInit.sql")
    @DisplayName("책 조회 기능 통합 테스트")
    @Test
    public void selectBookTest() {
        // given
        Long id = 1L;

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange(
                "/api/v1/book/" + id,
                HttpMethod.GET,
                request,
                String.class
        );

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo(EXPECTED_BOOK_TITLE);
        assertThat(author).isEqualTo(EXPECTED_BOOK_AUTHOR);
    }

    @Sql("classpath:db/tableInit.sql")
    @DisplayName("책 삭제 기능 통합 테스트")
    @Test
    public void deleteBookTest() {
        // given
        Long id = 1L;

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange(
                "/api/v1/book/" + id,
                HttpMethod.DELETE,
                request,
                String.class
        );

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");

        assertThat(code).isEqualTo(SUCCESS_CODE);
        assertThat(msg).isEqualTo(SUCCESS_DELETE_BOOK_MSG);
    }


    @Sql("classpath:db/tableInit.sql")
    @DisplayName("책 수정 기능 통합 테스트")
    @Test
    public void updateBookTest() throws Exception {
        // given
        Long id = 1L;

        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("제목 수정");
        bookSaveReqDto.setAuthor("작가 수정");

        String body = om.writeValueAsString(bookSaveReqDto);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange(
                "/api/v1/book/" + id,
                HttpMethod.PUT,
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
}

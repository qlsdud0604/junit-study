package com.example.junitstudy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest   // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("책 등록 테스트")
    @Test
    public void saveBookTest() {
        // given
        String title = "테스트 책 제목";
        String author = "테스트 책 작가";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when
        Book bookPS = bookRepository.save(book);

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    @DisplayName("책 목록 조회 테스트")
    @Test
    public void selectBookListTest() {
        // given
        String title = "테스트 책 제목";
        String author = "테스트 책 작가";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);

        // when
        List<Book> booksPS = bookRepository.findAll();

        // then
        assertEquals("테스트 책 제목", booksPS.get(0).getTitle());
        assertEquals("테스트 책 작가", booksPS.get(0).getAuthor());
    }

    @DisplayName("책 조회 테스트")
    @Test
    public void selectBook() {
        // given
        String title = "테스트 책 제목";
        String author = "테스트 책 작가";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals("테스트 책 제목", bookPS.getTitle());
        assertEquals("테스트 책 작가", bookPS.getAuthor());
    }

}

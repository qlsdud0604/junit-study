package com.example.junitstudy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

}

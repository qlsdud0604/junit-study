package com.example.junitstudy.service;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.dto.BookRespDto;
import com.example.junitstudy.dto.BookSaveReqDto;
import com.example.junitstudy.util.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @DisplayName("책 등록 테스트")
    @Test
    public void saveBookTest() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("책 등록 테스트 제목");
        dto.setAuthor("책 등록 테스트 작가");

        // stub
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.sender()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.saveBook(dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @DisplayName("책 목록 조회 테스트")
    @Test
    public void selectBookListTest() {
        // given

        // stub
        List<Book> books = Arrays.asList(
                new Book(1L, "책 목록 조회 테스트 제목01", "책 목록 조회 테스트 작가01"),
                new Book(2L, "책 목록 조회 테스트 제목02", "책 목록 조회 테스트 작가02")
        );
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookRespDto> bookRespDtoList = bookService.selectBookList();

        // then
        assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo(books.get(0).getTitle());
        assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
        assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo(books.get(1).getTitle());
        assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo(books.get(1).getAuthor());
    }
}

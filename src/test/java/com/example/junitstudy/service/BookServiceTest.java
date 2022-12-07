package com.example.junitstudy.service;

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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());

    }
}

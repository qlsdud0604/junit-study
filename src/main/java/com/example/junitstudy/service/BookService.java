package com.example.junitstudy.service;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.dto.BookRespDto;
import com.example.junitstudy.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto saveBook(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookRespDto().toDto(bookPS);
    }
}

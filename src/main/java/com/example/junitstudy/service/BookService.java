package com.example.junitstudy.service;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.dto.BookRespDto;
import com.example.junitstudy.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto saveBook(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookRespDto().toDto(bookPS);
    }

    public List<BookRespDto> selectBookList() {
        return bookRepository.findAll().stream()
                .map(bookPS -> new BookRespDto().toDto(bookPS))
                .collect(Collectors.toList());
    }

    public BookRespDto selectBook(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            return new BookRespDto().toDto(bookOP.get());
        } else {
            throw new RuntimeException("책이 존재하지 않습니다.");
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

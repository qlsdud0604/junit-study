package com.example.junitstudy.service;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.domain.BookRepository;
import com.example.junitstudy.dto.response.BookListRespDto;
import com.example.junitstudy.dto.response.BookRespDto;
import com.example.junitstudy.dto.request.BookSaveReqDto;
import com.example.junitstudy.util.MailSender;
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
    private final MailSender mailSender;

    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto saveBook(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        if (bookPS != null) {
            if (!mailSender.sender()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return bookPS.toDto();
    }

    public BookListRespDto selectBookList() {
        return BookListRespDto.builder()
                .items(
                        bookRepository.findAll().stream()
                        .map(bookPS -> bookPS.toDto())
                        .collect(Collectors.toList())
                )
                .build();
    }

    public BookRespDto selectBook(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            return bookOP.get().toDto();
        } else {
            throw new RuntimeException("책이 존재하지 않습니다.");
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public BookRespDto modifyBook(Long id, BookSaveReqDto dto) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else {
            throw new RuntimeException("책이 존재하지 않습니다.");
        }
    }
}

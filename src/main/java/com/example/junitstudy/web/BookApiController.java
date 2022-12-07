package com.example.junitstudy.web;

import com.example.junitstudy.domain.Book;
import com.example.junitstudy.dto.response.BookListRespDto;
import com.example.junitstudy.dto.response.BookRespDto;
import com.example.junitstudy.dto.request.BookSaveReqDto;
import com.example.junitstudy.dto.response.CMRepsDto;
import com.example.junitstudy.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto reqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }

        BookRespDto bookRespDto = bookService.saveBook(reqDto);

        return new ResponseEntity<>(
                CMRepsDto.builder()
                        .code(1)
                        .msg("책 저장 성공")
                        .body(bookRespDto)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListRespDto bookListRespDto = bookService.selectBookList();
        return new ResponseEntity<>(
                CMRepsDto.builder()
                        .code(1)
                        .msg("책 목록 조회 성공")
                        .body(bookListRespDto)
                        .build(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<?> getBook() {
        return null;
    }

    public ResponseEntity<?> deleteBook() {
        return null;
    }

    public ResponseEntity<?> updateBook() {
        return null;
    }
}

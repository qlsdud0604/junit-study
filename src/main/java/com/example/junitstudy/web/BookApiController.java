package com.example.junitstudy.web;

import com.example.junitstudy.dto.response.BookRespDto;
import com.example.junitstudy.dto.request.BookSaveReqDto;
import com.example.junitstudy.dto.response.CMRepsDto;
import com.example.junitstudy.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody BookSaveReqDto reqDto) {
        BookRespDto bookRespDto = bookService.saveBook(reqDto);
        CMRepsDto<?> cmRepsDto = CMRepsDto.builder()
                .code(1)
                .msg("Book 저장 성공")
                .body(bookRespDto)
                .build();
        return new ResponseEntity<>(cmRepsDto, HttpStatus.CREATED);   // 201 - insert
    }

    public ResponseEntity<?> getBookList() {
        return null;
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

package com.example.junitstudy.web;

import com.example.junitstudy.dto.response.BookListRespDto;
import com.example.junitstudy.dto.response.BookRespDto;
import com.example.junitstudy.dto.request.BookSaveReqDto;
import com.example.junitstudy.dto.response.CMRepsDto;
import com.example.junitstudy.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto reqDto, BindingResult bindingResult) {
        checkValidationError(bindingResult);

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

    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        BookRespDto bookRespDto = bookService.selectBook(id);
        return new ResponseEntity<>(
                CMRepsDto.builder()
                        .code(1)
                        .msg("책 조회 성공")
                        .body(bookRespDto)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(
                CMRepsDto.builder()
                        .code(1)
                        .msg("책 삭제 성공")
                        .body(null)
                        .build(),
                HttpStatus.OK
        );
    }

    private void checkValidationError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
    }
}

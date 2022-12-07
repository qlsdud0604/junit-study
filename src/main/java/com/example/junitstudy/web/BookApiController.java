package com.example.junitstudy.web;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
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
                        .msg("글 저장 성공")
                        .body(bookRespDto)
                        .build(), 
                HttpStatus.CREATED
        );
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

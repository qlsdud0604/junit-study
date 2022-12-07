package com.example.junitstudy.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMRepsDto<T> {
    private Integer code;
    private String msg;
    private T body;

    @Builder
    public CMRepsDto(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}

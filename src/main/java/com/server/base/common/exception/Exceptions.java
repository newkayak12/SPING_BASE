package com.server.base.common.exception;

import lombok.Getter;

@Getter
public enum Exceptions {
//    TOKEN_EXCEPTIONS
    INVALID_TOKEN(-999,"비정상적인 접근입니다."),
    TOKEN_EXPIRED(-998, "접근 권한이 만료되었습니다."),
    INVALID_ACCESS(-998, "잘못된 접근입니다."),

//    USER_EXCEPTIONS
    NO_DATA(-1, "아이디 혹은 비밀번호가 잘못됐습니다.");



    private Integer code;
    private String msg;
    Exceptions(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}

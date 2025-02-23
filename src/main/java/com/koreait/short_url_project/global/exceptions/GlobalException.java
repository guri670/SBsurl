package com.koreait.short_url_project.global.exceptions;

import com.koreait.short_url_project.global.rsData.RsData;
import com.koreait.short_url_project.standard.dto.Empty;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final RsData<Empty> rsData;
    public GlobalException() {
        this("400-0", "에러");
    }
    public GlobalException(String msg) {
        this("400-0", msg);
    }
    public GlobalException(String resultCode, String msg) {
        super("resultCode=" + resultCode + ",msg=" + msg);
        this.rsData = RsData.of(resultCode, msg);
    }
    public static class E404 extends GlobalException {
        public E404() {
            super("404-0", "데이터를 찾을 수 없습니다.");
        }
    }
}
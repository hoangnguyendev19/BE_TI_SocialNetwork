package com.tma.demo.common;


import lombok.Getter;


@Getter
public enum SubjectOTP {
    OTP_SUBJECT("Verify OTP"),;
    private final String message;
    SubjectOTP(String s) {
        this.message = s;
    }
}

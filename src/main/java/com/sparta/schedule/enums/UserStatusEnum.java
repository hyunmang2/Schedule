package com.sparta.schedule.enums;
import lombok.Getter;

@Getter
public enum UserStatusEnum {

    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String status;

    UserStatusEnum(String status) {
        this.status = status;
    }

    private class Authority {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }

}

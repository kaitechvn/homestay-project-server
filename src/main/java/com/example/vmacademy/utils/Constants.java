package com.example.vmacademy.utils;

public final class Constants {

    public final class ErrorCode {

        // Validation user
        public static final String USER_BLANK = "A001";
        public static final String PASSWORD_BLANK = "A002";
        public static final String PASSWORD_INVALID = "A003";
        public static final String EMAIL_BLANK = "A004";
        public static final String EMAIL_INVALID = "A005";
        public static final String PHONE_BLANK = "A006";
        public static final String PHONE_INVALID = "A007";
        public static final String DOB_BLANK = "A008";
        public static final String USER_NOT_FOUND = "A009";
        public static final String USER_EXISTED = "A010";
        public static final String EMAIL_EXISTED = "A011";

        // Authenticate
        public static final String PASSWORD_MISMATCH = "B001";
    }

    public final class StatusCode  {

        public static final Integer SUCCESS = 200;
        public static final Integer CREATED = 201;
        public static final Integer METHOD_NOT_ALLOWED = 405;
        public static final Integer UNSUPPORTED_MEDIA_TYPE = 415;
        public static final Integer NOT_ACCEPTABLE = 406;
        public static final Integer INTERNAL_ERROR = 500;
        public static final Integer BAD_REQUEST = 400;
        public static final Integer NOT_FOUND = 404;
        public static final Integer UNAUTHORIZED = 401;
        public static final Integer FORBIDDEN = 403;
    }
}

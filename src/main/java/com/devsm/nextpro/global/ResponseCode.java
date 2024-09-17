package com.devsm.nextpro.global;

public interface ResponseCode {
    // public static final 자동으로 붙음

    // HTTP Status 200
    String SUCCESS = "SU"; // 성공

    // HTTP Status 400
    String VALIDATION_FAILED = "VF"; // 유효성 검증 실패
    String DUPLICATE_EMAIL = "DE"; // 중복된 이메일
    String NOT_EXISTED_USER = "NU"; // 존재하지 않는 유저
    String NOT_EXISTED_PRODUCT = "NP"; // 존재하지 않는 상품
    String NOT_EXISTED_ORDER = "NO"; // 존재하지 않는 주문
    String NOT_EXISTED = "NE"; // 존재하지 않을 때
    String BAD_REQUEST = "BQ"; // 잘못된 요청

    // HTTP Status 401
    String SIGN_IN_FAILED = "SF"; // 로그인 실패
    String AUTHENTICATION_FAILED = "AT"; // 인증 실패

    // HTTP Status 403
    String NO_PERMISSION = "NP"; // 권한 없음

    // HTTP Status 500
    String DATABASE_ERROR = "DBE"; // 데이터베이스 에러

    // Feign 통신 에러
    String FEIGN_ERROR = "FE";
}

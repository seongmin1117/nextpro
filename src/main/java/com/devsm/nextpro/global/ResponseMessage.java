package com.devsm.nextpro.global;

public interface ResponseMessage {
    // HTTP Status 200
    String SUCCESS = "성공"; // 성공

    // HTTP Status 400
    String VALIDATION_FAILED = "인증되지 않은 사용자입니다."; // 유효성 검증 실패
    String DUPLICATE_EMAIL = "중복된 이메일입니다."; // 중복된 이메일
    String NOT_EXISTED_USER = "존재하지않는 유저입니다."; // 존재하지 않는 유저
    String NOT_EXISTED_PRODUCT = "상품을 찾을 수 없습니다."; // 존재하지 않는 상품
    String NOT_EXISTED_ORDER = "존재하지 않는 주문입니다."; // 존재하지 않는 주문
    String NOT_EXISTED = "존재하지 않습니다."; // 존재하지 않을 때

    // HTTP Status 401
    String SIGN_IN_FAILED = "로그인 실패"; // 로그인 실패
    String AUTHORIZATION_FAILED = "인증 실패"; // 인증 실패

    // HTTP Status 403
    String NO_PERMISSION = "권한이 없는 사용자입니다."; // 권한 없음

    // HTTP Status 500
    String DATABASE_ERROR = "데이터베이스 에러"; // 데이터베이스 에러

    // Feign 통신 에러
    String FEIGN_ERROR = "서버간 통신 에러"; // 서버 통신 에러
}

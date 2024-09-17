package com.devsm.nextpro.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private String code;
    private String message;
    private T data;

    // 데이터베이스 에러
    public static <T> ResponseDto<T> databaseError(){
        return new ResponseDto<>(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR, null);
    }

    // 성공 - 반환할 데이터가 없는 경우
    public static <T> ResponseDto<T> success(){
        return new ResponseDto<>(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,null);
    }
    // 성공 - 반환할 데이터가 있는 경우
    public static <T> ResponseDto<T> success(T data){
        return new ResponseDto<>(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

}

package com.test.community.Dto;

import lombok.Data;

@Data
public class ResultDto<T> {

        private int code;
        private String message;
        private T data;

        public ResultDto success(){
            ResultDto resultDto=new ResultDto();
            resultDto.setCode(200);
            resultDto.setMessage("成功");
            return resultDto;
        }
        public <T> ResultDto success(T data){
            ResultDto resultDto=new ResultDto();
            resultDto.setCode(200);
            resultDto.setMessage("成功");
            resultDto.setData(data);
            return resultDto;
        }


}

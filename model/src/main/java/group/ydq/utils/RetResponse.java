package group.ydq.utils;

import group.ydq.model.dto.BaseResponse;
import org.springframework.lang.Nullable;

/**
 * @author Daylight
 * @date 2018/12/13 15:30
 */
public class RetResponse {
    public static BaseResponse success(){
        return new BaseResponse();
    }

    public static BaseResponse success(Object obj){
        BaseResponse response=new BaseResponse();
        response.setObject(obj);
        return response;
    }

    public static BaseResponse error(){
        BaseResponse response=new BaseResponse();
        response.setStatusCode("400");
        response.setMsg("error");
        return response;
    }

    public static BaseResponse error(String code, String msg, @Nullable Object obj){
        BaseResponse response=new BaseResponse();
        response.setStatusCode(code);
        response.setMsg(msg);
        response.setObject(obj);
        return response;
    }
}

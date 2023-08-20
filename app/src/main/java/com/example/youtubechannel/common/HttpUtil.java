package com.example.youtubechannel.common;

import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.models.response.ApiError;
import com.example.youtubechannel.utils.StringUtil;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;

public class HttpUtil {

    private final static String HEAD_MESSAGE_KEY = "Message";

    public static ApiError getError(Throwable e, Retrofit retrofit) {
        ApiError apiError;
        if (e instanceof HttpException) {
            apiError = new ApiError();
            HttpException httpException = (HttpException) e;
            apiError.setCode(httpException.code());

            if (!StringUtil.isEmpty(httpException.response().headers().get(HEAD_MESSAGE_KEY))) {
                apiError.setMessage(httpException.response().headers().get(HEAD_MESSAGE_KEY));
            } else {
                ResponseBody body = httpException.response().errorBody();
                Converter<ResponseBody, ApiError> converter
                        = retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
                try {
                    ApiError error = converter.convert(body);
                    if (error != null) {
                        apiError.setMessage(error.getMessage());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    apiError.setMessage(httpException.message());
                }
            }
        } else if (e instanceof UncaughtHttpException) {
            apiError = new ApiError();
            UncaughtHttpException httpException = (UncaughtHttpException) e;
            ResponseBody body = httpException.response().errorBody();
            Converter<ResponseBody, ApiError> responseBodyObjectConverter
                    = retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
            try {
                ApiError error = responseBodyObjectConverter.convert(body);
                if (error != null) {
                    apiError.setMessage(error.getMessage());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                apiError.setMessage(httpException.message());
            }
        } else if (e instanceof ConnectException) {
            apiError = new ApiError(Constant.FAIL_CONNECT_CODE, Constant.SERVER_ERROR);
        } else if (e instanceof JsonSyntaxException) {
            apiError = new ApiError(Constant.JSON_PARSER_CODE, e.getMessage());
        } else {
            apiError = new ApiError(Constant.OTHER_CODE, e.getMessage());
        }
        return apiError;
    }
}

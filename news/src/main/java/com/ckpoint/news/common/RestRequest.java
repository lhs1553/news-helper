package com.ckpoint.news.common;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import hsim.checkpoint.util.ValidationObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RestRequest {

    public <T> T commonRestApiCall(String url, Object body, HttpMethod method, String cookie, String auth, Class<T> resType, boolean isList) {
        ResponseEntity<String> res = this.commonRestApiCall(url, body, method, cookie, auth);

        if (!this.isSuccess(res)) {
            throw new RuntimeException("url : " + url + " call is fail : " + res.getStatusCode());
        }

        String resBody = res.getBody();
        if( resBody == null){
            return null;
        }

        if (resType.equals(String.class)) {
            return resType.cast(resBody);
        }

        try {
            if(isList){

                CollectionType cType = TypeFactory.defaultInstance().constructCollectionType(java.util.List.class, resType);
                return ValidationObjUtil.getDefaultObjectMapper().readValue(resBody, cType);
            }
            Object resObj = ValidationObjUtil.getDefaultObjectMapper().readValue(resBody, resType);
            return resType.cast(resObj);
        } catch (IOException e) {
            log.info("object mapper parse error ");
            throw new RuntimeException("url : " + url + " : object mapper parsing error ");
        }
    }

    public ResponseEntity<String> commonRestApiCall(String url, Object body, HttpMethod method, String cookie, String auth) {
        String bodyStr = null;
        if (body != null) {
            try {
                bodyStr = ValidationObjUtil.getDefaultObjectMapper().writeValueAsString(body);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("object mapper obj to string error  ");
            }
        }
        return this.commonRestApiCall(url, bodyStr, method, cookie, auth);
    }

    public ResponseEntity<String> commonRestApiCall(String url, String body, HttpMethod method, String cookie, String auth) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8));
        if (auth != null) {
            headers.set("Authorization", auth);
        }

        if (cookie != null) {
            headers.set("Cookie", cookie);
        }

        log.info("request Url : " + url + ", method : " + method.toString());
        log.info("request body ");
        log.info(body);

        HttpEntity<String> httpEntity = null;
        if (body == null) {
            httpEntity = new HttpEntity<String>(headers);
        } else {
            httpEntity = new HttpEntity<String>(body, headers);
        }

        return restTemplate.exchange(url, method, httpEntity, String.class);
    }

    public boolean isSuccess(ResponseEntity<String> res) {
        if (res == null) {
            return false;
        }

        if ((res.getStatusCodeValue() / 100) == 2) {
            return true;
        }

        return false;
    }


}

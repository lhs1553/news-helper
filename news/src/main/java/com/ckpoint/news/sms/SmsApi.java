package com.ckpoint.news.sms;

import com.ckpoint.news.common.RestRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsApi {

    private final @NonNull
    RestRequest restRequest;


    private String getUrl(String url) {
        String smsUrl = "http://localhost:8090/" + url;
        return smsUrl;
    }

    public boolean sendSms(SmsMsg smsMsg) {

        ResponseEntity<String> res = this.restRequest.commonRestApiCall(this.getUrl("send"), smsMsg, HttpMethod.POST, null, null);
        return this.restRequest.isSuccess(res);
    }

}

package com.ckpoint.news.sms;

import lombok.Data;

@Data
public class SmsMsg {
    private String sendNumber;
    private String recvNumber;
    private String message;

    public SmsMsg copy(){
        SmsMsg clone = new SmsMsg();
        clone.recvNumber = this.recvNumber;
        clone.message = this.message;
        clone.sendNumber = this.sendNumber;
        return clone;
    }
}

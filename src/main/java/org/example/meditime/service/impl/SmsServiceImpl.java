package org.example.meditime.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.example.meditime.dto.SmsDTO;
import org.example.meditime.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${twilo.account.ssid}")
    private String twiloSid;

    @Value("${twilo.account.auth.token}")
    private String twiloAuthToken;

    @Value("${twilo.account.outgoing.number}")
    private String twiloSmsNumber;


    @PostConstruct
    private void setUp(){
        Twilio.init(twiloSid, twiloAuthToken);

    }

    @Override
    public String sendSms(List<SmsDTO> smsDTOList) {

        for(SmsDTO smsDTO: smsDTOList) {
            Message message = Message.creator(
                    new PhoneNumber(smsDTO.getSmsNumber()),
                    new PhoneNumber(twiloSmsNumber),
                    smsDTO.getSmsMessage()).create();
        }
        return "message sent";
    }

}

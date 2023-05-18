package org.example.meditime.controller;

import org.example.meditime.dto.SmsDTO;
import org.example.meditime.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/sms")
@RestController
public class SmsController {


    @Autowired
    SmsService smsService;

    @PostMapping("/send")
    public String sendSMSToDestination(@RequestBody List<SmsDTO> smsDTO) {
        return smsService.sendSms(smsDTO);
    }

}

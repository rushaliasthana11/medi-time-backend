package org.example.meditime.service;

import org.example.meditime.dto.SmsDTO;

import java.util.List;

public interface SmsService {
    public String sendSms(List<SmsDTO> smsDTO);
}

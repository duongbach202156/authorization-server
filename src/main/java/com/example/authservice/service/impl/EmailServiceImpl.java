package com.example.authservice.service.impl;

import com.example.authservice.event.ForgotPasswordEvent;
import com.example.authservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @EventListener
    @Async
    @Override
    public void sendForgotPasswordEmail(ForgotPasswordEvent forgotPasswordEvent) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(forgotPasswordEvent.getEmail());
        simpleMailMessage.setSubject("[QUÊN MẬT KHẨU]");
        simpleMailMessage.setText(String.format("""
                Đây là mã đặt lại mật khẩu: %s
                Vui lòng sử dụng mã này để tạo mật khẩu mới.
                LƯU Ý:
                - KHÔNG TIẾT LỘ MÃ ĐẶT LẠI MẬT KHẨU DƯỚI MỌI HÌNH THỨC
                - MÃ ĐẶT LẠI MẬT KHẨU CÓ HIỆU LỰC TRONG VÒNG 10 PHÚT
                """, forgotPasswordEvent.getForgotPasswordToken().getToken()));
        javaMailSender.send(simpleMailMessage);
    }
}

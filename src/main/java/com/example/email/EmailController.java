package com.example.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    @GetMapping("/mailCheck")
    @ResponseBody
    public int processMailCheck(@RequestParam(value = "email" , required = false) String email) throws Exception {
        int mailKey = (int) ((Math.random() * (99999 - 10000 + 1)) + 10000);

        String from = "";//보내는 이 메일주소
        String to = email;
        String title = "회원가입시 필요한 인증번호 입니다.";
        String content = "[인증번호] " + mailKey + " 입니다. <br/> 인증번호 확인란에 기입해주십시오.";
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

            mailHelper.setFrom(from);
            mailHelper.setTo(to);
            mailHelper.setSubject(title);
            mailHelper.setText(content, true);

            mailSender.send(mail);

        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
        return mailKey;
    }
}

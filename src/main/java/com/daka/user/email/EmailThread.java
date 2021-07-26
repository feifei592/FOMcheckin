package com.daka.user.email;

import com.daka.user.entity.Checker;
import com.daka.user.entity.EmailObj;
import com.daka.user.service.CheckerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailThread{
    @Autowired
    CheckerService checkerService;
    @Autowired
    EmailSend emailSend;

    @Async
    public void sendEmail(Checker checker){
        EmailObj emailObj = new EmailObj();
        emailObj.setUsr_ID(checker.getUsr_ID());
        emailObj.setUsr_name(checkerService.selectuserbyID(checker.getUsr_ID()).getUsr_name());
        emailObj.setTime(LocalDateTime.now());
        switch (checker.getCheck_type()){
            case CheckerService.check_in:
                emailObj.setType("上班");
                break;
            case CheckerService.check_in_warn:
                emailObj.setType("迟到");
                break;
            case CheckerService.check_out:
                emailObj.setType("下班");
                break;
            case CheckerService.check_out_warn:
                emailObj.setType("早退");
                break;
            default:
                break;
        }
        if((checker.getCollar_round_neck()==null||checker.getCollar_round_neck()==1)
                &&(checker.getLow_cloth()==null||checker.getLow_cloth().equals("Trousers")
                ||checker.getLow_cloth().equals("Skirt&Dress"))){
            emailObj.setCloths("合格");
        }else{
            emailObj.setCloths("异常");
        }
        if(checker.getHair_color()==null||checker.getHair_color().equals("gray")||checker.getHair_color().equals("black")){
            emailObj.setHair("合格");
        }else{
            emailObj.setHair("异常");
        }
        String expression = checker.getExpression();
        if (expression==null){
            expression = "neutral";
        }
        System.out.println(expression);
        emailObj.setStatement(checkerService.selectStatebyExpression(expression));
        emailObj.setEmail_address(checkerService.selectuserbyID(checker.getUsr_ID()).getEmail_address());
        System.out.println(emailObj.toString());
        //EmailThread emailThread = new EmailThread(emailObj);
        //emailThread.start();
        emailSend.sendSimpleMail(emailObj);
    }
}

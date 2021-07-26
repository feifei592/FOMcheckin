package com.daka.user.FtpServer;

import com.alibaba.fastjson.JSONObject;
import com.daka.user.entity.Checker;
import com.daka.user.service.AliService;
import com.daka.user.service.AsyncRequestService;
import com.daka.user.service.CheckerService;
import org.apache.ftpserver.ftplet.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Time;

@Component
public class MyFtpPlet extends DefaultFtplet {

    private static final Logger logger = LoggerFactory.getLogger(MyFtpPlet.class);

    @Autowired
    private CheckerService checkerService;

    @Override
    public FtpletResult onUploadStart(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        //获取上传文件的上传路径
        String path = session.getUser().getHomeDirectory();
        //获取上传用户
        String name = session.getUser().getName();
        //获取上传文件名
        String filename = request.getArgument();
        logger.info("用户:'{}'，上传文件到目录：'{}'，文件名称为：'{}'，状态：开始上传~", name, path, filename);
        return super.onUploadStart(session, request);
    }


    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {
        //获取上传文件的上传路径
        String path = session.getUser().getHomeDirectory();
        //获取上传用户
        String name = session.getUser().getName();
        //获取上传文件名
        String filename = request.getArgument();
        logger.info("用户:'{}'，上传文件到目录：'{}'，文件名称为：'{}，状态：成功！'", name, path, filename);
        String pic_url = path+ "\\" +filename;
        pic_url = pic_url.replace("\\","\\\\");
        checkerService.checkCompare(pic_url);
        return super.onUploadEnd(session, request);
    }

    @Override
    public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //todo servies...
        return super.onDownloadStart(session, request);
    }

    @Override
    public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //todo servies...
        return super.onDownloadEnd(session, request);
    }
}
package com.prometheus.sisyphus.uaa.service.impl;

import com.prometheus.sisyphus.common.util.DateUtils;
import com.prometheus.sisyphus.uaa.UaaApplicatioin;
import com.prometheus.sisyphus.uaa.service.ImageCaptchaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by wushaoyong on 2017/11/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApplicatioin.class)
public class ImageCaptchaServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(ImageCaptchaServiceImplTest.class);

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @Test
    public void generate() throws Exception {
        String phoneNumber = "19960800510";
        File file = new File("/export/image_captcha_" + DateUtils.getCurrentSecond() + ".jpeg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        imageCaptchaService.generate(phoneNumber, fileOutputStream);
    }

    @Test
    public void validate() throws Exception {
        String phoneNumber = "19960800510";
        Boolean x = imageCaptchaService.validate(phoneNumber, "uv8dz");
        logger.debug("验证的结果: {}", x);
    }

}

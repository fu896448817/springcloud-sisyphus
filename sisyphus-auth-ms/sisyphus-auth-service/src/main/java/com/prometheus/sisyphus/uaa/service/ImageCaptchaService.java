package com.prometheus.sisyphus.uaa.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wushaoyong on 2017/11/29.
 */
public interface ImageCaptchaService {
    void generate(String userIdentification, HttpServletResponse response) throws IOException;

    void generate(String userIdentification, OutputStream outputStream) throws IOException;

    Boolean validate(String userIdentification, String code);
}

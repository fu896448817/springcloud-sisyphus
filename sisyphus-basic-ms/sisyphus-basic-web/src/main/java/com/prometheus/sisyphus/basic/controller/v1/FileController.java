//package com.prometheus.sisyphus.basic.controller.v1;
//
//import com.prometheus.sisyphus.basic.exception.BasicErrorCode;
//import com.prometheus.sisyphus.common.exception.BusinessException;
//import com.prometheus.sisyphus.common.model.RestResult;
//import com.prometheus.sisyphus.common.model.RestResultBuilder;
//import com.prometheus.sisyphus.common.util.VerificationUtils;
//import com.prometheus.sisyphus.common.web.controller.BaseController;
//import com.prometheus.sisyphus.fastdfs.client.FastDFSClient;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.MultipartConfigElement;
//import javax.websocket.server.PathParam;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
///**
// * created by sunliangliang
// */
//@Api(description = "文件API")
//@RestController
//@RequestMapping("/v1/api/basic-ms/file")
//public class FileController extends BaseController {
//    @Autowired
//    private FastDFSClient fastDFSClient;
//    @Value("${fastdfs.nginx-web:}")
//    String fastdfsUrl;
//    @Value("${temp-file.path:}")
//    private String tempFilePath;
//    @ApiOperation(value="文件上传", notes="文件上传")
//    @PostMapping("/")
//    public RestResult handleFileUpload(@RequestParam("file") MultipartFile multipartFile) {
//        String name = multipartFile.getOriginalFilename();
//        if (!VerificationUtils.isImage(name)){
//            throw new BusinessException(BasicErrorCode.INVALID_IMAGE_FORMAT);
//        }
//        if (!multipartFile.isEmpty()) {
//            try {
//                String uploadFile = fastDFSClient.uploadFile(multipartFile);
//                logger.info("uploadFile:{}", uploadFile);
//                return RestResultBuilder.builder().success(uploadFile).build();
//            } catch (IOException e) {
//                return RestResultBuilder.builder().failure().build();
//            }
//        } else {
//            throw new BusinessException(BasicErrorCode.FILE_EMPTY);
//        }
//    }
//    @ApiOperation(value="删除文件", notes="删除文件")
//    @DeleteMapping("/")
//    public RestResult handleFileDel(@PathParam("path") String path) {
//        logger.debug("handleFileDel :{}", path);
//        StringBuilder apperder = new StringBuilder();
//        String [] dfsArray = path.split(fastdfsUrl);
//        apperder.append(fastdfsUrl).append("/purge").append(dfsArray[1]);
//        logger.info("--------url:"+apperder.toString());
//        fastDFSClient.deleteFile(apperder.toString());
//        return RestResultBuilder.builder().success().build();
//    }
//
//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setLocation(tempFilePath);
//        return factory.createMultipartConfig();
//    }
//}

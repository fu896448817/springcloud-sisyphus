package com.prometheus.sisyphus.common.web.controller;

import com.alibaba.fastjson.JSON;
import com.prometheus.sisyphus.common.model.PageInfo;
import com.prometheus.sisyphus.common.model.PageRestResultBuilder;
import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.web.controller.BaseController;
import com.prometheus.sisyphus.common.web.dto.PageInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wujun on 2017/02/25.
 *
 * @author wujun
 * @since 2017/02/25
 */
public abstract class BasePageController<T> extends BaseController {

    @RequestMapping("/list")
    public RestResult listPage(PageInfoDto pageInfoDto) {
        logger.debug("listPage pageInfoDto: {}", pageInfoDto);
        PageInfo<T> pageInfo = this.listPageInfo(PageInfo.<T>build(pageInfoDto));
        logger.debug("listPage {}", JSON.toJSONString(pageInfo));
        return PageRestResultBuilder.builder().success(pageInfo).build();
    }

    public abstract PageInfo<T> listPageInfo(PageInfo<T> pageInfo);
}

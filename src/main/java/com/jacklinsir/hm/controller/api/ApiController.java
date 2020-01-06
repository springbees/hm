package com.jacklinsir.hm.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author linSir
 * @version V1.0
 * @Description: (公共API控制器)
 * @Date 2019/12/31 14:55
 */
@Api(tags = "ApiController", description = "公用controller")
@Controller
@RequestMapping("${api-url}")
public class ApiController {

    @ApiOperation("页面请求渲染不同页面")
    @RequestMapping(value = "/getPage")
    public ModelAndView getPage(ModelAndView modelAndView, String pageName) {
        //返回请求参数中的结果视图
        modelAndView.setViewName(pageName);
        return modelAndView;
    }
}

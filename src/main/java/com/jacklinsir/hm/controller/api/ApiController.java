package com.jacklinsir.hm.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author linSir
 * @version V1.0
 * @Description: (公共API控制器)
 * @Date 2019/12/31 14:55
 */
@Controller
@RequestMapping("${api-url}")
public class ApiController {

    @RequestMapping(value = "/getPage")
    public ModelAndView getPage(ModelAndView modelAndView, String pageName) {
        //返回请求参数中的结果视图
        modelAndView.setViewName(pageName);
        return modelAndView;
    }
}

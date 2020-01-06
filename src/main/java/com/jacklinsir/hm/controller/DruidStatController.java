package com.jacklinsir.hm.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author linSir
 */
@RestController
@Api(tags = "DruidStatController", description = "Druid监控数据")
public class DruidStatController {

    @ApiOperation("Druid监控数据")
    @GetMapping("/druid/stat")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

}

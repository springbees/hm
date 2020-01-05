package com.jacklinsir.hm.model.base;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author linSir
 * @version V1.0
 * @Description: (pojo共性抽取)
 * @Date 2019/12/31 13:47
 */
@Data
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 8925514045582235838L;
    private ID id; //根据用户继承关系指定ID类型
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date createTime = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date updateTime = new Date();
}

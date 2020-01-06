package com.jacklinsir.hm.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author linSir
 */
@Data
public class BeanField implements Serializable {

    private static final long serialVersionUID = 4279960350136806659L;

    private String columnName;

    private String columnType;

    private String columnComment;

    private String columnDefault;

    private String name;

    private String type;

}

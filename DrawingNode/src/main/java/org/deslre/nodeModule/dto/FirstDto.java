package org.deslre.nodeModule.dto;

import lombok.Data;

@Data
public class FirstDto {

    private Long id;

    private Integer nodeId;
    /**
     * 名称
     */
    private String name;

    /**
     * 信息
     */
    private String identity;

    /**
     * 级别
     */
    private String level;

}

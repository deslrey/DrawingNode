package org.deslre.nodeModule.dto;

import lombok.Data;

@Data
public class RelationDto {

    /**
     * 头节点ID
     */
    private Integer headNodeId;

    /**
     * 头节点级别
     */
    private String headLevel;

    /**
     * 关系信息
     */
    private String information;

    /**
     * 尾结点ID
     */
    private Integer tailNodeId;

    /**
     * 尾结点级别
     */
    private String tailLevel;

    /**
     * 逻辑删除
     */
    private Integer exist;


}

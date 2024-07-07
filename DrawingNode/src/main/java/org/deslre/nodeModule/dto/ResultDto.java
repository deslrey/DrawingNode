package org.deslre.nodeModule.dto;

import lombok.Data;

@Data
public class ResultDto {
    private Long id;
    private Integer nodeId;
    private String name;
    private String identity;
    private String level;

}

package org.deslre.modules.filesModule.entity;

import lombok.Data;

@Data
public class PageEntity {

    private String searchKey;
    private Integer pageNum;
    private Integer pageSize;

}

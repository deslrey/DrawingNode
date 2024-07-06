package org.deslre.nodeModule.chartNode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartLink {
    private String source;
    private String target;
    private String category;
}
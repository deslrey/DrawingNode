package org.deslre.nodeModule.chartNode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartNode {
    private String name;
    private String des;
    private Integer symbolSize;
    private Integer category;

    public ChartNode() {
    }
}
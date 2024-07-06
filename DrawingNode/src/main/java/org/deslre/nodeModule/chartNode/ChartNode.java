package org.deslre.nodeModule.chartNode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartNode {
    private String name;
    private boolean draggable;
    private int[] symbolSize;
    private String color;
    private String category;
}
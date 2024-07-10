package org.deslre.nodeModule.chartNode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartLink {
    private String source;
    private String target;
    private String name;
    private String des;
    private String[] symbol;  // 符号数组，可选字段

    public ChartLink() {
    }
}
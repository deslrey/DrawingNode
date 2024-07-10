package org.deslre.nodeModule.chartNode;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChartDataResponse {
    private List<ChartNode> nodes;
    private List<ChartLink> links;
    private List<ChartCategory> categories;

    public ChartDataResponse() {
    }
}

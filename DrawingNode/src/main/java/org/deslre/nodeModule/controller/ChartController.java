package org.deslre.nodeModule.controller;

import org.deslre.nodeModule.chartNode.ChartCategory;
import org.deslre.nodeModule.chartNode.ChartLink;
import org.deslre.nodeModule.chartNode.ChartNode;
import org.deslre.utils.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChartController {

    @GetMapping("/chart/data")
    public Results<Object> getChartData() {
        // 节点数据
        List<ChartNode> nodes = new ArrayList<>();
        nodes.add(new ChartNode("某IT男", true, new int[]{80, 80}, "#000", "收入支出分析"));
        nodes.add(new ChartNode("工资\n6000", true, new int[]{80, 80}, "#0000ff", "收入+"));
        nodes.add(new ChartNode("租房\n600", true, new int[]{80, 80}, "#ff0000", "支出-"));
        nodes.add(new ChartNode("生活开销\n1400", true, new int[]{80, 80}, "#ff0000", "支出-"));
        nodes.add(new ChartNode("储蓄\n4000", true, new int[]{80, 80}, "#00ff00", "剩余="));

        // 链接数据
        List<ChartLink> links = new ArrayList<>();
        links.add(new ChartLink("某IT男", "工资\n6000", "收入+"));
        links.add(new ChartLink("某IT男", "租房\n600", "支出-"));
        links.add(new ChartLink("某IT男", "生活开销\n1400", "支出-"));
        links.add(new ChartLink("某IT男", "储蓄\n4000", "剩余="));

        // 类别数据
        List<ChartCategory> categories = new ArrayList<>();
        categories.add(new ChartCategory("收入支出分析"));
        categories.add(new ChartCategory("收入+"));
        categories.add(new ChartCategory("支出-"));
        categories.add(new ChartCategory("支出-"));
        categories.add(new ChartCategory("剩余="));

        // 返回的数据
        List<Object> data = new ArrayList<>();
        data.add(nodes);
        data.add(links);
        data.add(categories);

        return Results.ok(data);
    }
}

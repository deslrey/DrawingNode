package org.deslre.nodeModule.controller;

import org.deslre.nodeModule.chartNode.ChartCategory;
import org.deslre.nodeModule.chartNode.ChartDataResponse;
import org.deslre.nodeModule.chartNode.ChartLink;
import org.deslre.nodeModule.chartNode.ChartNode;
import org.deslre.utils.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chart")
public class ChartController {

    @GetMapping("/data")
    public Results<ChartDataResponse> getChartData() {
        List<ChartNode> nodes = generateNodes();
        List<ChartLink> links = generateLinks();
        List<ChartCategory> categories = generateCategories();

        ChartDataResponse chartData = new ChartDataResponse();
        chartData.setNodes(nodes);
        chartData.setLinks(links);
        chartData.setCategories(categories);

        // 返回成功结果
        return Results.ok(chartData);
    }

    // 模拟生成节点数据的方法
    private List<ChartNode> generateNodes() {
        List<ChartNode> nodes = new ArrayList<>();

        ChartNode node1 = new ChartNode("node01", "nodedes01", 70, 0);
        nodes.add(node1);

        ChartNode node2 = new ChartNode("node02", "nodedes02", 50, 1);
        nodes.add(node2);

        ChartNode node3 = new ChartNode("node03", "nodedes3", 50, 1);
        nodes.add(node3);

        ChartNode node4 = new ChartNode("node04", "nodedes04", 50, 1);
        nodes.add(node4);

        ChartNode node5 = new ChartNode("node05", "nodedes05", 50, 1);
        nodes.add(node5);

        ChartNode node6 = new ChartNode("node06", "nodedes04", 50, 2);
        nodes.add(node6);

        return nodes;
    }

    // 模拟生成链接数据的方法
    private List<ChartLink> generateLinks() {
        List<ChartLink> links = new ArrayList<>();

        ChartLink link1 = new ChartLink("node01", "node02", "link01", "link01des", null);
        links.add(link1);

        ChartLink link2 = new ChartLink("node01", "node03", "link02", "link02des", null);
        links.add(link2);

        ChartLink link3 = new ChartLink("node01", "node04", "link03", "link03des", null);
        links.add(link3);

        ChartLink link4 = new ChartLink("node01", "node05", "link04", "link05des", null);
        links.add(link4);

        ChartLink link5 = new ChartLink("node01", "node06", "link06", "link05des", new String[]{"none", "arrow"});
        links.add(link5);

        return links;
    }

    // 模拟生成类别数据的方法
    private List<ChartCategory> generateCategories() {
        List<ChartCategory> categories = new ArrayList<>();

        ChartCategory category1 = new ChartCategory();
        category1.setName("类别1");
        categories.add(category1);

        ChartCategory category2 = new ChartCategory();
        category2.setName("类别2");
        categories.add(category2);

        ChartCategory category3 = new ChartCategory();
        category3.setName("类别3");
        categories.add(category3);

        return categories;
    }
}

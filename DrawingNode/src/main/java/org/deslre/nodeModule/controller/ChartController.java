package org.deslre.nodeModule.controller;

import org.deslre.nodeModule.chartNode.*;
import org.deslre.nodeModule.dto.ResultDto;
import org.deslre.nodeModule.vo.RelationshipNode;
import org.deslre.utils.NeoUtil;
import org.deslre.result.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/chart")
public class ChartController {

    @Resource
    private NeoUtil neoUtil;

    @GetMapping("/data")
    public Results<ChartDataResponse> getChartData() {
        List<RelationshipNode> nodeList = neoUtil.getAllRelationships("2021").getData();
        List<ChartNode> nodes = generateNodes(nodeList);
        List<ChartLink> links = generateLinks(nodeList);
        List<ChartCategory> categories = generateCategories(nodeList);

        ChartDataResponse chartData = new ChartDataResponse();
        chartData.setNodes(nodes);
        chartData.setLinks(links);
        chartData.setCategories(categories);

        // 返回成功结果
        return Results.ok(chartData);
    }


    private List<ChartCategory> generateCategories(List<RelationshipNode> nodeList) {
        List<ChartCategory> categoryList = new ArrayList<>();

        categoryList.add(new ChartCategory("一级"));
        categoryList.add(new ChartCategory("二级"));
        categoryList.add(new ChartCategory("三级"));
        categoryList.add(new ChartCategory("四级"));

        return categoryList;
    }

    private List<ChartLink> generateLinks(List<RelationshipNode> nodeList) {
        List<ChartLink> chartLinkList = new ArrayList<>(nodeList.size());
        ChartLink chartLink;
        for (RelationshipNode node : nodeList) {
            chartLink = new ChartLink();
            setRelaList(node, chartLink);
            chartLinkList.add(chartLink);
        }
        return chartLinkList;
    }

    private List<ChartNode> generateNodes(List<RelationshipNode> nodeList) {
        Set<ChartNode> chartNodeSet = new HashSet<>(nodeList.size());
        for (RelationshipNode node : nodeList) {
            setRelaList(node, chartNodeSet);
        }

        return new ArrayList<>(chartNodeSet);
    }

    private void setRelaList(RelationshipNode node, ChartLink chartLink) {
        String startNode = node.getStartNode();
        String endNode = node.getEndNode();
        ResultDto startPro = NeoUtil.parseProduct(startNode);
        ResultDto endPro = NeoUtil.parseProduct(endNode);
        chartLink.setSource(startPro.getName());
        chartLink.setDes(NeoUtil.getValueFromJsonString(node.getRelationship()));
        chartLink.setTarget(endPro.getName());
        chartLink.setName(NeoUtil.getValueFromJsonString(node.getRelationship()));
    }

    private void setRelaList(RelationshipNode node, Set<ChartNode> chartNodeSet) {
        String startNode = node.getStartNode();
        String endNode = node.getEndNode();
        ResultDto startPro = NeoUtil.parseProduct(startNode);
        ResultDto endPro = NeoUtil.parseProduct(endNode);
        ChartNode startCharNode = new ChartNode();
        ChartNode endCharNode = new ChartNode();
        startCharNode.setName(startPro.getName());
        startCharNode.setDes(startPro.getIdentity());
        startCharNode.setCategory(selectionLevel(startPro.getLevel()));
        startCharNode.setSymbolSize(selectionSize(startPro.getLevel()));

        endCharNode.setName(endPro.getName());
        endCharNode.setDes(endPro.getIdentity());
        endCharNode.setCategory(selectionLevel(endPro.getLevel()));
        endCharNode.setSymbolSize(selectionSize(endPro.getLevel()));
        chartNodeSet.add(startCharNode);
        chartNodeSet.add(endCharNode);
    }


    private Integer selectionLevel(String str) {
        return switch (str) {
            case "一级" -> 0;
            case "二级" -> 1;
            case "三级" -> 2;
            case "四级" -> 3;
            default -> 0;
        };
    }

    private Integer selectionSize(String str) {
        return switch (str) {
            case "一级" -> 60;
            case "二级" -> 55;
            case "三级" -> 50;
            case "四级" -> 45;
            default -> 40;
        };
    }


    // 模拟生成节点数据的方法
//    private List<ChartNode> generateNodes() {
//        List<ChartNode> nodes = new ArrayList<>();
//
//        ChartNode node1 = new ChartNode("node01", "nodedes01", 70, 0,null);
//        nodes.add(node1);
//
//        ChartNode node2 = new ChartNode("node02", "nodedes02", 50, 1,null);
//        nodes.add(node2);
//
//        ChartNode node3 = new ChartNode("node03", "nodedes3", 50, 1,null);
//        nodes.add(node3);
//
//        ChartNode node4 = new ChartNode("node04", "nodedes04", 50, 1,null);
//        nodes.add(node4);
//
//        ChartNode node5 = new ChartNode("node05", "nodedes05", 50, 1,null);
//        nodes.add(node5);
//
//        ChartNode node6 = new ChartNode("node06", "nodedes04", 50, 2,null);
//        nodes.add(node6);
//
//        return nodes;
//    }
//
//    // 模拟生成链接数据的方法
//    private List<ChartLink> generateLinks() {
//        List<ChartLink> links = new ArrayList<>();
//
//        ChartLink link1 = new ChartLink("node01", "node02", "link01", "link01des", null,new LineStyle("#66FFCC"));
//        links.add(link1);
//
//        ChartLink link2 = new ChartLink("node01", "node03", "link02", "link02des", null,null);
//        links.add(link2);
//
//        ChartLink link3 = new ChartLink("node01", "node04", "link03", "link03des", null,null);
//        links.add(link3);
//
//        ChartLink link4 = new ChartLink("node01", "node05", "link04", "link05des", null,null);
//        links.add(link4);
//
//        ChartLink link5 = new ChartLink("node01", "node06", "link06", "link05des", new String[]{"none", "arrow"},null);
//        links.add(link5);
//
//        return links;
//    }
//
//    // 模拟生成类别数据的方法
//    private List<ChartCategory> generateCategories() {
//        List<ChartCategory> categories = new ArrayList<>();
//
//        ChartCategory category1 = new ChartCategory();
//        category1.setName("类别1");
//        categories.add(category1);
//
//        ChartCategory category2 = new ChartCategory();
//        category2.setName("类别2");
//        categories.add(category2);
//
//        ChartCategory category3 = new ChartCategory();
//        category3.setName("类别3");
//        categories.add(category3);
//
//        return categories;
//    }
}

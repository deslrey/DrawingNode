package org.deslre;

import org.deslre.nodeModule.chartNode.ChartCategory;
import org.deslre.nodeModule.chartNode.ChartDataResponse;
import org.deslre.nodeModule.chartNode.ChartLink;
import org.deslre.nodeModule.chartNode.ChartNode;
import org.deslre.nodeModule.dto.ResultDto;
import org.deslre.nodeModule.vo.RelationshipNode;
import org.deslre.utils.NeoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.deslre.utils.StringUtil.isEmpty;

@SpringBootTest
public class demoTest {

    @Resource
    private NeoUtil neoUtil;

    @Test
    void testOne() {
        List<RelationshipNode> nodeList = neoUtil.getAllRelationships("2021").getData();
        List<ChartNode> nodes = generateNodes(nodeList);
        List<ChartLink> links = generateLinks(nodeList);
        List<ChartCategory> categories = generateCategories(nodeList);

        ChartDataResponse chartData = new ChartDataResponse();
        chartData.setNodes(nodes);
        chartData.setLinks(links);
        chartData.setCategories(categories);

        System.out.println("chartData = " + chartData);

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
            case "一级" -> 1;
            case "二级" -> 2;
            case "三级" -> 3;
            case "四级" -> 4;
            default -> 0;
        };
    }

    private Integer selectionSize(String str) {
        return switch (str) {
            case "一级" -> 70;
            case "二级" -> 65;
            case "三级" -> 60;
            case "四级" -> 55;
            default -> 50;
        };
    }


}

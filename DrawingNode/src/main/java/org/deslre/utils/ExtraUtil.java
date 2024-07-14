package org.deslre.utils;

import cn.hutool.core.bean.BeanUtil;
import org.deslre.nodeModule.chartNode.ChartCategory;
import org.deslre.nodeModule.chartNode.ChartLink;
import org.deslre.nodeModule.chartNode.ChartNode;
import org.deslre.nodeModule.chartNode.ItemStyle;
import org.deslre.nodeModule.dto.*;
import org.deslre.nodeModule.repository.*;
import org.deslre.nodeModule.vo.RelationshipNode;
import org.deslre.result.CaseObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class ExtraUtil {
    @Resource
    private FirstNodeRepository firstNodeRepository;
    @Resource
    private SecondNodeRepository secondNodeRepository;
    @Resource
    private ThirdlyNodeRepository thirdlyNodeRepository;
    @Resource
    private FourthlyNodeRepository fourthlyNodeRepository;

    public ResultDto switchChoose(String level, Integer id) {
        ResultDto resultDto = new ResultDto();
        switch (level) {
            case "一级" -> {
                FirstDto firstDto = instanceofClass(firstNodeRepository, id);
                resultDto = BeanUtil.toBean(firstDto, ResultDto.class);
            }
            case "二级" -> {
                SecondDto secondDto = instanceofClass(secondNodeRepository, id);
                resultDto = BeanUtil.toBean(secondDto, ResultDto.class);
            }
            case "三级" -> {
                ThirdlyDto thirdlyDto = instanceofClass(thirdlyNodeRepository, id);
                resultDto = BeanUtil.toBean(thirdlyDto, ResultDto.class);
            }
            case "四级" -> {
                FourthlyDto fourthlyDto = instanceofClass(fourthlyNodeRepository, id);
                resultDto = BeanUtil.toBean(fourthlyDto, ResultDto.class);
            }
        }
        return resultDto;
    }

    private <T> T instanceofClass(ResultRepository<T> resultRepository, Integer id) {
        return resultRepository.searchNode(id);
    }


    public List<ChartCategory> generateCategories(List<RelationshipNode> nodeList) {
        if (nodeList == null || nodeList.isEmpty()) {
            return new ArrayList<>(1);
        }
        Set<ChartCategory> categorySet = new HashSet<>(15);
        for (RelationshipNode node : nodeList) {
            setLevel(node, categorySet);
        }
        List<ChartCategory> categoryList = new ArrayList<>(categorySet);

        categoryList.sort(Comparator.comparing(ChartCategory::getName));

        return categoryList;
    }

    private void setLevel(RelationshipNode node, Set<ChartCategory> categorySet) {
        String startNode = node.getStartNode();
        String endNode = node.getEndNode();
        ResultDto startPro = NeoUtil.parseProduct(startNode);
        ResultDto endPro = NeoUtil.parseProduct(endNode);
        categorySet.add(new ChartCategory(startPro.getLevel()));
        categorySet.add(new ChartCategory(endPro.getLevel()));
    }

    public List<ChartLink> generateLinks(List<RelationshipNode> nodeList) {
        List<ChartLink> chartLinkList = new ArrayList<>(nodeList.size());
        ChartLink chartLink;
        for (RelationshipNode node : nodeList) {
            chartLink = new ChartLink();
            setRelaList(node, chartLink);
            chartLinkList.add(chartLink);
        }
        return chartLinkList;
    }

    public List<ChartNode> generateNodes(List<RelationshipNode> nodeList) {
        Set<ChartNode> chartNodeSet = new HashSet<>(nodeList.size());
        for (RelationshipNode node : nodeList) {
            setRelaList(node, chartNodeSet);
        }

        return new ArrayList<>(chartNodeSet);
    }

    public List<ChartNode> generateNodes(List<RelationshipNode> nodeList, CaseObject caseObject) {
        Set<ChartNode> chartNodeSet = new HashSet<>(nodeList.size());
        for (RelationshipNode node : nodeList) {
            setRelaList(node, chartNodeSet, caseObject);
        }

        return new ArrayList<>(chartNodeSet);
    }


    private void setRelaList(RelationshipNode node, ChartLink chartLink) {
        String startNode = node.getStartNode();
        String endNode = node.getEndNode();
        ResultDto startPro = NeoUtil.parseProduct(startNode);
        ResultDto endPro = NeoUtil.parseProduct(endNode);
        String inform = NeoUtil.getValueFromJsonString(node.getRelationship());
        chartLink.setSource(startPro.getName());
        chartLink.setDes(inform);
        chartLink.setTarget(endPro.getName());
        if (isArrow(inform)) {
            chartLink.setSymbol(FinalUtil.SYMBOL);
        }
        chartLink.setName(inform);
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

    private void setRelaList(RelationshipNode node, Set<ChartNode> chartNodeSet, CaseObject caseObject) {
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
        if (highlight(startPro, caseObject)) {
            startCharNode.setItemStyle(new ItemStyle(FinalUtil.HIGHLIGHT_COLOR));
        }

        endCharNode.setName(endPro.getName());
        endCharNode.setDes(endPro.getIdentity());
        endCharNode.setCategory(selectionLevel(endPro.getLevel()));
        endCharNode.setSymbolSize(selectionSize(endPro.getLevel()));
        if (highlight(endPro, caseObject)) {
            endCharNode.setItemStyle(new ItemStyle(FinalUtil.HIGHLIGHT_COLOR));
        }
        chartNodeSet.add(startCharNode);
        chartNodeSet.add(endCharNode);
    }


    public boolean highlight(ResultDto resultDto, CaseObject caseObject) {
        if (StringUtil.isEmpty(caseObject.getCaseName())) {
            return resultDto.getCardId() != null && resultDto.getCardId().equals(caseObject.getCaseCardId());
        } else if (StringUtil.isEmpty(caseObject.getCaseCardId())) {
            return resultDto.getName().equals(caseObject.getCaseName());
        } else {
            if (resultDto.getName().equals(caseObject.getCaseName()))
                return resultDto.getCardId() != null && resultDto.getCardId().equals(caseObject.getCaseCardId());
            return false;
        }
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

    private boolean isArrow(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        for (String s : FinalUtil.ARROW) {
            if (str.contains(s))
                return true;
        }
        return false;
    }

}

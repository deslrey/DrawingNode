package org.deslre.nodeModule.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.deslre.nodeModule.chartNode.ChartCategory;
import org.deslre.nodeModule.chartNode.ChartDataResponse;
import org.deslre.nodeModule.chartNode.ChartLink;
import org.deslre.nodeModule.chartNode.ChartNode;
import org.deslre.nodeModule.dto.ResultDto;
import org.deslre.nodeModule.entity.CaseTableEntity;
import org.deslre.nodeModule.entity.RelationEntity;
import org.deslre.nodeModule.repository.CaseTableRepository;
import org.deslre.nodeModule.repository.RelationRepository;
import org.deslre.nodeModule.service.RelationService;
import org.deslre.nodeModule.vo.RelationVo;
import org.deslre.nodeModule.vo.RelationshipNode;
import org.deslre.result.CaseObject;
import org.deslre.utils.ExtraUtil;
import org.deslre.utils.FinalUtil;
import org.deslre.utils.NeoUtil;
import org.deslre.result.ResultCodeEnum;
import org.deslre.result.Results;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static org.deslre.utils.StringUtil.isEmpty;


@Slf4j
@Service
public class RelationServiceImpl implements RelationService {

    private static final Map<String, Boolean> caseAllMap = new HashMap<>(16);

    @PostConstruct
    private void init() {
        earlyInitialization();
    }

    @Resource
    private RelationRepository relationRepository;
    @Resource
    private CaseTableRepository caseTableRepository;
    @Resource
    private NeoUtil neoUtil;
    @Resource
    private ExtraUtil extraUtil;

    @Override
    public Results<String> addAllCorrespondingCases(final String caseNumber) {
        if (isEmpty(caseNumber)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        if (caseAllMap.get(caseNumber) == null) {
            return Results.fail("当前编号不存在");
        }

        List<RelationEntity> list = relationRepository.findAllByCaseNumber(caseNumber);
        ResultDto startNode;
        ResultDto endNode;
        RelationVo relationVo = new RelationVo();
        for (RelationEntity relation : list) {
            startNode = extraUtil.switchChoose(relation.getHeadLevel(), relation.getHeadNodeId());
            endNode = extraUtil.switchChoose(relation.getTailLevel(), relation.getTailNodeId());
            relationVo.setTitle(relation.getCaseNumber());
            relationVo.setName(relation.getInformation());
            neoUtil.addCaseRelationships(startNode, relationVo, endNode);
        }

        return Results.ok("添加完成");
    }

    @Override
    public Results<List<String>> getAllCaseNumbers() {
        List<String> list = new ArrayList<>(15);
        updateCaseMap();
        Set<String> keySet = caseAllMap.keySet();
        list.addAll(keySet);
        return Results.ok(list);
    }

    @Override
    public Results<ChartDataResponse> getAllRelationshipsCaseNumber(final CaseObject caseObject) {
        if (isEmpty(caseObject)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }

        Results<List<RelationshipNode>> results = neoUtil.getAllRelationships(caseObject.getCaseNumber());
        if (!Objects.equals(results.getCode(), FinalUtil.CODE)) {
            return Results.fail(results.getMessage());
        }
        if (results.getData() == null || results.getData().isEmpty()) {
            return Results.fail("该案号暂无数据");
        }

        List<RelationshipNode> nodeList = results.getData();
        List<ChartNode> nodes = extraUtil.generateNodes(nodeList, caseObject);
        List<ChartLink> links = extraUtil.generateLinks(nodeList);
        List<ChartCategory> categories = extraUtil.generateCategories(nodeList);

        ChartDataResponse chartData = new ChartDataResponse();
        chartData.setNodes(nodes);
        chartData.setLinks(links);
        chartData.setCategories(categories);

        return Results.ok(chartData);
    }

    @Override
    public Results<ChartDataResponse> getCaseNumberData(final String caseNumber) {
        if (isEmpty(caseNumber)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        Results<List<RelationshipNode>> results = neoUtil.getAllRelationships(caseNumber);
        if (!Objects.equals(results.getCode(), FinalUtil.CODE)) {
            return Results.fail(results.getMessage());
        }
        if (results.getData() == null || results.getData().isEmpty()) {
            return Results.fail("该案号暂无数据");
        }

        List<RelationshipNode> nodeList = results.getData();
        List<ChartNode> nodes = extraUtil.generateNodes(nodeList);
        List<ChartLink> links = extraUtil.generateLinks(nodeList);
        List<ChartCategory> categories = extraUtil.generateCategories(nodeList);

        ChartDataResponse chartData = new ChartDataResponse();
        chartData.setNodes(nodes);
        chartData.setLinks(links);
        chartData.setCategories(categories);

        return Results.ok(chartData);
    }


    private void earlyInitialization() {
        List<CaseTableEntity> list = caseTableRepository.findAll();
        list.forEach(caseTable -> {
            caseAllMap.put(caseTable.getCaseNumber(), true);
        });
    }

    private void updateCaseMap() {
        caseAllMap.clear();
        earlyInitialization();
    }

}

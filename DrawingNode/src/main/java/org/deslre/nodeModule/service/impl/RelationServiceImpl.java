package org.deslre.nodeModule.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.deslre.nodeModule.dto.ResultDto;
import org.deslre.nodeModule.entity.CaseTableEntity;
import org.deslre.nodeModule.entity.RelationEntity;
import org.deslre.nodeModule.repository.CaseTableRepository;
import org.deslre.nodeModule.repository.RelationRepository;
import org.deslre.nodeModule.service.RelationService;
import org.deslre.nodeModule.vo.RelationVo;
import org.deslre.utils.ExtraUtil;
import org.deslre.utils.NeoUtil;
import org.deslre.utils.ResultCodeEnum;
import org.deslre.utils.Results;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Results<String> addAllCorrespondingCases(String caseNumber) {
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


    private void earlyInitialization() {
        List<CaseTableEntity> list = caseTableRepository.findAll();
        list.forEach(caseTable -> {
            caseAllMap.put(caseTable.getCaseNumber(), true);
        });
        log.info("编号更新完成");
    }

    public void updateCaseMap() {
        caseAllMap.clear();
        earlyInitialization();
    }

}

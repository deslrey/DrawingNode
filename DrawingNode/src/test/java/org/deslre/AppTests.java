package org.deslre;

import org.deslre.nodeModule.dto.ResultDto;
import org.deslre.nodeModule.entity.RelationEntity;
import org.deslre.nodeModule.repository.RelationRepository;
import org.deslre.nodeModule.vo.RelationVo;
import org.deslre.utils.ExtraUtil;
import org.deslre.utils.NeoUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class AppTests {

    private static final Logger log = LoggerFactory.getLogger(AppTests.class);

    @Resource
    private RelationRepository relationRepository;
    @Resource
    private ExtraUtil extraUtil;
    @Resource
    private NeoUtil neoUtil;


    @Test
    void add() {
        List<String> categoryList = new ArrayList<>(10);


        categoryList.add("三级");
        categoryList.add("一级");
        categoryList.add("二级");
        categoryList.add("四级");

        Collections.sort(categoryList);
        categoryList.forEach(System.out::println);
    }

    @Test
    void findAll() {
        List<RelationEntity> list = relationRepository.findAll();
        RelationVo s = new RelationVo();
        for (RelationEntity relation : list) {
            ResultDto startNode = extraUtil.switchChoose(relation.getHeadLevel(), relation.getHeadNodeId());
            ResultDto endNode = extraUtil.switchChoose(relation.getTailLevel(), relation.getTailNodeId());
            s.setTitle(relation.getCaseNumber());
            s.setName(relation.getInformation());
            neoUtil.addCaseRelationships(startNode, s, endNode);
        }
    }

    @Test
    void delete() {
        neoUtil.deleteAll();
    }
}

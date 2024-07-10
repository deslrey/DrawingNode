package org.deslre.utils;

import org.deslre.nodeModule.dto.FirstDto;
import org.deslre.nodeModule.entity.FirstNodeEntity;
import org.deslre.nodeModule.repository.FirstNodeRepository;
import org.deslre.nodeModule.vo.RelationshipNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

@SpringBootTest
class NeoUtilTest {

    @Resource
    private NeoUtil neoUtil;
    @Resource
    private FirstNodeRepository firstNodeRepository;

    @Test
    void getAllRelationships() {
        List<RelationshipNode> nodeList = neoUtil.getAllRelationships("2021").getData();
        nodeList.forEach(System.out::println);
    }

    @Test
    void addSingleNode() {
        List<FirstNodeEntity> entityList = firstNodeRepository.findAll();
        entityList.forEach(firstNodeEntity -> neoUtil.addSingleNode(firstNodeEntity));
    }

    @Test
    void deleteAll() {
        neoUtil.deleteAll();
    }
}
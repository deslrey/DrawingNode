package org.deslre.utils;

import org.deslre.nodeModule.dto.FirstDto;
import org.deslre.nodeModule.entity.FirstNodeEntity;
import org.deslre.nodeModule.repository.FirstNodeRepository;
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
    void addSingleNode() {
        List<FirstNodeEntity> entityList = firstNodeRepository.findAll();
        entityList.forEach(firstNodeEntity -> neoUtil.addSingleNode(firstNodeEntity));
    }

    @Test
    void deleteAll() {
        neoUtil.deleteAll();
    }
}
package org.deslre;

import org.deslre.loginModule.repository.UserLoginRepository;
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
import java.util.List;

@SpringBootTest
class AppTests {

    private static final Logger log = LoggerFactory.getLogger(AppTests.class);

    @Resource
    private UserLoginRepository userLoginRepository;
    @Resource
    private RelationRepository relationRepository;
    @Resource
    private ExtraUtil extraUtil;
    @Resource
    private NeoUtil neoUtil;


    @Test
    void add() {
        List<RelationEntity> list = relationRepository.findAllByCaseNumber("221");
        System.out.println("list = " + list);
        list.forEach(System.out::println);
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

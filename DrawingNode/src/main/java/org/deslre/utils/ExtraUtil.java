package org.deslre.utils;

import cn.hutool.core.bean.BeanUtil;
import org.deslre.nodeModule.dto.*;
import org.deslre.nodeModule.repository.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

}

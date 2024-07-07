package org.deslre.nodeModule.repository;

import org.deslre.nodeModule.dto.SecondDto;
import org.deslre.nodeModule.dto.ThirdlyDto;
import org.deslre.nodeModule.entity.SecondNodeEntity;
import org.deslre.nodeModule.entity.ThirdlyNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.deslre.utils.NeoUtil.convert;

public interface ThirdlyNodeRepository extends JpaRepository<ThirdlyNodeEntity, Integer>, ResultRepository<ThirdlyDto> {
    @Override
    default ThirdlyDto searchNode(Integer id) {
        Optional<ThirdlyNodeEntity> optional = findById(id);
        if (optional.isEmpty()) {
            return new ThirdlyDto();
        }
        ThirdlyNodeEntity node = optional.get();
        return convert(node, ThirdlyDto.class);
    }
}

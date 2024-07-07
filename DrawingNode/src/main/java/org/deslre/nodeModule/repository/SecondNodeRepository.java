package org.deslre.nodeModule.repository;

import org.deslre.nodeModule.dto.FirstDto;
import org.deslre.nodeModule.dto.SecondDto;
import org.deslre.nodeModule.entity.FirstNodeEntity;
import org.deslre.nodeModule.entity.SecondNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.deslre.utils.NeoUtil.convert;

public interface SecondNodeRepository extends JpaRepository<SecondNodeEntity, Integer>, ResultRepository<SecondDto> {

    @Override
    default SecondDto searchNode(Integer id) {
        Optional<SecondNodeEntity> optional = findById(id);
        if (optional.isEmpty()) {
            return new SecondDto();
        }
        SecondNodeEntity node = optional.get();
        return convert(node, SecondDto.class);
    }
}

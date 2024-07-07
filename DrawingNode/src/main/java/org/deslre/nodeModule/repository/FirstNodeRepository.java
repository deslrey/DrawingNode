package org.deslre.nodeModule.repository;

import org.deslre.nodeModule.dto.FirstDto;
import org.deslre.nodeModule.entity.FirstNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.deslre.utils.NeoUtil.convert;

public interface FirstNodeRepository extends JpaRepository<FirstNodeEntity, Integer>, ResultRepository<FirstDto> {

    @Override
    default FirstDto searchNode(Integer id) {
        Optional<FirstNodeEntity> optional = findById(id);
        if (optional.isEmpty()) {
            return new FirstDto();
        }
        FirstNodeEntity node = optional.get();
        return convert(node, FirstDto.class);
    }
}

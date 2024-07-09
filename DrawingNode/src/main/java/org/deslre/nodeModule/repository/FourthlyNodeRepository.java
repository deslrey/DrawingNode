package org.deslre.nodeModule.repository;

import org.deslre.nodeModule.dto.FirstDto;
import org.deslre.nodeModule.dto.FourthlyDto;
import org.deslre.nodeModule.entity.FirstNodeEntity;
import org.deslre.nodeModule.entity.FourthlyNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.deslre.utils.NeoUtil.convert;

public interface FourthlyNodeRepository extends JpaRepository<FourthlyNodeEntity, Integer>, ResultRepository<FourthlyDto> {

    @Override
    default FourthlyDto searchNode(Integer id) {
        Optional<FourthlyNodeEntity> optional = findById(id);
        if (optional.isEmpty()) {
            return new FourthlyDto();
        }
        FourthlyNodeEntity node = optional.get();
        return convert(node, FourthlyDto.class);
    }
}

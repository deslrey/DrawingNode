package org.deslre.nodeModule.repository;

import org.springframework.stereotype.Repository;

//@Repository
public interface ResultRepository<T> {

    T searchNode(Integer id);

//    void updateExist(Integer id);

}

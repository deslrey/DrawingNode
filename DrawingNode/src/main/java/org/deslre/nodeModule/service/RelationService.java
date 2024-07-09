package org.deslre.nodeModule.service;

import org.deslre.utils.Results;

import java.util.List;

public interface RelationService {

    Results<String> addAllCorrespondingCases(String caseNumber);

    Results<List<String>> getAllCaseNumbers();

}

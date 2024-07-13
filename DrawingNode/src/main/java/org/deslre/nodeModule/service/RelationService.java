package org.deslre.nodeModule.service;

import org.deslre.result.Results;

import java.util.List;

public interface RelationService {

    Results<String> addAllCorrespondingCases(String caseNumber);

    Results<List<String>> getAllCaseNumbers();

}

package org.deslre.nodeModule.service;

import org.deslre.nodeModule.chartNode.ChartDataResponse;
import org.deslre.result.CaseObject;
import org.deslre.result.Results;

import java.util.List;

public interface RelationService {

    Results<String> addAllCorrespondingCases(String caseNumber);

    Results<List<String>> getAllCaseNumbers();

    Results<ChartDataResponse> getAllRelationshipsCaseNumber(CaseObject caseObject);
}

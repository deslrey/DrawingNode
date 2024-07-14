package org.deslre.nodeModule.service;

import org.deslre.nodeModule.chartNode.ChartDataResponse;
import org.deslre.result.CaseObject;
import org.deslre.result.Results;

import java.util.List;

public interface RelationService {

    Results<String> addAllCorrespondingCases(final String caseNumber);

    Results<List<String>> getAllCaseNumbers();

    Results<ChartDataResponse> getAllRelationshipsCaseNumber(final CaseObject caseObject);

    Results<ChartDataResponse> getCaseNumberData(final String caseNumber);
}

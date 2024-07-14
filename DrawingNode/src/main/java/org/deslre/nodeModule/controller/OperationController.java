package org.deslre.nodeModule.controller;

import lombok.extern.slf4j.Slf4j;
import org.deslre.nodeModule.chartNode.ChartDataResponse;
import org.deslre.nodeModule.service.RelationService;
import org.deslre.result.CaseObject;
import org.deslre.result.ResultCodeEnum;
import org.deslre.result.Results;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static org.deslre.utils.StringUtil.isEmpty;

@Slf4j
@RestController
@RequestMapping("/operation")
public class OperationController {

    @Resource
    private RelationService relationService;

    @PostMapping("/inquire")
    public Results<ChartDataResponse> getAllRelationshipsCaseNumber(@RequestBody CaseObject caseObject) {
        if (isEmpty(caseObject)) {
            log.debug("传入空对象");
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        return relationService.getAllRelationshipsCaseNumber(caseObject);
    }


    @PostMapping("/inquire/{caseNumber}")
    public Results<ChartDataResponse> getCaseNumberData(@PathVariable String caseNumber) {
        if (isEmpty(caseNumber)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }

        return relationService.getCaseNumberData(caseNumber);
    }

    @GetMapping("/allCase")
    public Results<List<String>> getAllCaseNumbers() {
        return relationService.getAllCaseNumbers();
    }


}

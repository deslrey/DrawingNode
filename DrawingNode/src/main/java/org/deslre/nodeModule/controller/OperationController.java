package org.deslre.nodeModule.controller;

import org.deslre.nodeModule.service.RelationService;
import org.deslre.utils.ResultCodeEnum;
import org.deslre.utils.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static org.deslre.utils.StringUtil.isEmpty;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Resource
    private RelationService relationService;

    @GetMapping("/inquire/{case}")
    public Results<String> addAllCorrespondingCases(@PathVariable("case") String caseNumber) {
        if (isEmpty(caseNumber)) {
            return Results.fail(ResultCodeEnum.EMPTY_VALUE);
        }
        return relationService.addAllCorrespondingCases(caseNumber);
    }

    @GetMapping("/allCase")
    public Results<List<String>> getAllCaseNumbers() {
        return relationService.getAllCaseNumbers();
    }


}

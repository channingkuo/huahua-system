package com.kuo.huahua.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuo.huahua.dto.UploadScoreDto;
import com.kuo.huahua.service.ITestScoreService;
import com.kuo.huahua.utils.Result;
import com.kuo.huahua.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Channing Kuo
 * @date 2021-06-28 19:50:58
 */
@RestController
@RequestMapping("/score")
public class TestScoreController {

    @Resource
    private ITestScoreService testScoreService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestBody List<UploadScoreDto> uploadScoreDtoList) {
        try {
            testScoreService.upload(uploadScoreDtoList);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getList(@RequestBody JSONObject payload) {
        try {
            List<Map<String, Object>> list = testScoreService.getList(payload);
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/save/single", method = RequestMethod.POST)
    public Result saveScore(@RequestBody JSONObject payload) {
        try {
            testScoreService.saveScore(payload);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
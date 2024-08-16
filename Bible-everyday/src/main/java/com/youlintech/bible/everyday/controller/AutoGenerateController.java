package com.youlintech.bible.everyday.controller;

import com.youlintech.bible.common.core.controller.BaseController;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.everyday.service.AutoGenerateAiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author oyhz
 */
@RequestMapping("/auto")
@RestController
@Api(tags="自动生成")
@Slf4j
public class AutoGenerateController extends BaseController {
    @Autowired
    private AutoGenerateAiService autoGenerateAiService;

    @ApiOperation("自动生成每日圣经")
    @PreAuthorize("@ss.hasPermi('bible:everyday:autogenerate')")
    @PostMapping("/Generate/everydayBible")
    public AjaxResult generateBible(@RequestBody(required = false) List<String> bibleDateList) {
        return autoGenerateAiService.generateEverydayBible(bibleDateList);
    }
}

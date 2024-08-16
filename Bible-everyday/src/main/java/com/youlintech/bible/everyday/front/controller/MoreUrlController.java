package com.youlintech.bible.everyday.front.controller;

import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.redis.RedisCache;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oyhz
 * 前端右上角more的链接
 */
@Api(tags="read more url")
@RestController
@RequestMapping("/more")
public class MoreUrlController {
    @Autowired
    private RedisCache redisCache;
    @GetMapping
    public AjaxResult getMoreUrl() {
        return AjaxResult.success("操作成功",redisCache.getCacheObject("sys_config:Bible.more.url"));
    }
}

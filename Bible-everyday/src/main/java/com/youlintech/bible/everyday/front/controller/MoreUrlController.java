package com.youlintech.bible.everyday.front.controller;

import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.redis.RedisCache;
import com.youlintech.bible.everyday.constant.BackPictureConstant;
import com.youlintech.bible.everyday.model.vo.MoreVO;
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
@RequestMapping("/bible/front/more/")
public class MoreUrlController {
    @Autowired
    private RedisCache redisCache;
    @GetMapping("/getMore")
    public AjaxResult getMoreUrl() {
        MoreVO moreVO = new MoreVO();
        moreVO.setMoreUrl(redisCache.getCacheObject(BackPictureConstant.MORE_URL_KEY));
        moreVO.setBackImg(redisCache.getCacheObject(BackPictureConstant.BACK_PICTURE_KEY));
        moreVO.setBackImgId(redisCache.getCacheObject(BackPictureConstant.BACK_PICTURE_ID_KEY));
        return AjaxResult.success("操作成功",moreVO);
    }
}

package com.youlintech.bible.everyday.front.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.everyday.domain.BibleEvents;
import com.youlintech.bible.everyday.domain.PetId;
import com.youlintech.bible.everyday.model.vo.BibleEventsVO;
import com.youlintech.bible.everyday.service.IBibleEventsService;
import com.youlintech.bible.everyday.service.IPetIdService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oyhz
 */
@RestController
@RequestMapping("/bible/front")
@AllArgsConstructor
@Slf4j
public class BibleFrontViewController {

    private final IBibleEventsService bibleEventsService;
    private final IPetIdService petIdService;

    private static LocalDate getCurrentDateTime() {
        // 获取美国东部时间区
        ZoneId usEasternTimeZone = ZoneId.of("America/New_York");

        // 获取美国东部时间的当前时间
        ZonedDateTime nowTime = ZonedDateTime.now(usEasternTimeZone);

        // 提取日期部分
        return nowTime.toLocalDate();
    }
    @PostMapping("/getBibleEvents")
    public AjaxResult getBibleEvents(@RequestParam(value = "petId") String petId) {
        if (petId == null) {
            return AjaxResult.error("The device whitelist is empty or incorrect.");
        }
        PetId one = petIdService.getOne(new LambdaQueryWrapper<PetId>().eq(PetId::getPetId, petId));
        if (one == null) {
            return AjaxResult.error("白名单ID不存在");
        }
        // 获取美国东部时间的当前日期
        LocalDate dateNow = getCurrentDateTime();
        log.info("美国当前纽约时间dateNow:{}", dateNow);
        List<BibleEventsVO> bibleEventsList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LocalDate dateBefore = dateNow.minusDays(i);
            BibleEvents bibleEvents = bibleEventsService.getOne(new LambdaQueryWrapper<BibleEvents>().eq(BibleEvents::getBibleDate, dateBefore.toString()));
            BibleEventsVO bibleEventsVO = BeanUtil.copyProperties(bibleEvents, BibleEventsVO.class);
            if (bibleEventsVO == null) {
                return AjaxResult.warn("日期：" + dateBefore + "未查询到数据");
            }
            bibleEventsList.add(bibleEventsVO);
        }
        log.info("bibleEventsList:{}", bibleEventsList.size());
        return AjaxResult.success(bibleEventsList);
    }
}

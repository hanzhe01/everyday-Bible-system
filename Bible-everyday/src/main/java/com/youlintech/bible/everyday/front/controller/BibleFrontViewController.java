package com.youlintech.bible.everyday.front.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.everyday.domain.BibleEvents;
import com.youlintech.bible.everyday.domain.PetId;
import com.youlintech.bible.everyday.service.IBibleEventsService;
import com.youlintech.bible.everyday.service.IPetIdService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author oyhz
 */
@RestController
@RequestMapping("/bible/front")
@AllArgsConstructor
public class BibleFrontViewController {

    private final IBibleEventsService bibleEventsService;
    private final IPetIdService petIdService;
    @PostMapping("/getBibleEvents")
    public AjaxResult getBibleEvents(@RequestParam(value = "petId", required = false) String petId,
                                     @RequestParam(value = "date", required = false) Integer date) {
        //获取当前的日期
        LocalDate dateNow = LocalDate.now();
        if (petId == null) {
            return AjaxResult.error("The device whitelist is empty or incorrect.");
        }
        PetId one = petIdService.getOne(new LambdaQueryWrapper<PetId>().eq(PetId::getPetId, petId));
        if (one == null) {
            return AjaxResult.error("白名单不存在");
        }
        if (date == null) {
            return AjaxResult.error("The date is empty or incorrect.");
        }
        switch(date) {
            case 0:
                return AjaxResult.success(bibleEventsService.getOne(new LambdaQueryWrapper<BibleEvents>().eq(BibleEvents::getBibleDate, dateNow.toString())));
            case 1:
                // 获取前一天的日期
                LocalDate yesterday = dateNow.minusDays(1);
                return AjaxResult.success(bibleEventsService.getOne(new LambdaQueryWrapper<BibleEvents>().eq(BibleEvents::getBibleDate, yesterday.toString())));
            case 2:
                // 获取前两天的日期
                LocalDate dayBeforeYesterday = dateNow.minusDays(2);
                return AjaxResult.success(bibleEventsService.getOne(new LambdaQueryWrapper<BibleEvents>().eq(BibleEvents::getBibleDate, dayBeforeYesterday.toString())));
            case 3:
                // 获取前两天的日期
                LocalDate dayThreeDay = dateNow.minusDays(3);
                return AjaxResult.success(bibleEventsService.getOne(new LambdaQueryWrapper<BibleEvents>().eq(BibleEvents::getBibleDate, dayThreeDay.toString())));
            default:
                return AjaxResult.error("The Transmission error.");
        }
    }
}

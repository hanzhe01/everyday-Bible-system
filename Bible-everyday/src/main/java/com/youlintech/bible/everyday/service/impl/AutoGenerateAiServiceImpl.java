package com.youlintech.bible.everyday.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.redis.RedisCache;
import com.youlintech.bible.common.utils.http.HttpUtils;
import com.youlintech.bible.everyday.constant.AutoGenerateConstant;
import com.youlintech.bible.everyday.domain.BibleEvents;
import com.youlintech.bible.everyday.mapper.BibleEventsMapper;
import com.youlintech.bible.everyday.model.dto.BibleEventsDTO;
import com.youlintech.bible.everyday.service.AutoGenerateAiService;
import com.youlintech.bible.everyday.service.IBibleEventsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author oyhz
 */
@Service
@Slf4j
@AllArgsConstructor
public class AutoGenerateAiServiceImpl implements AutoGenerateAiService {
    private final RedisCache redisCache;
    private final IBibleEventsService bibleEventsService;
    private final BibleEventsMapper baseMapper;

    @Override
    public AjaxResult generateEverydayBible(List<String> bibleDate) {
        if (bibleDate == null || bibleDate.isEmpty() || bibleDate.size() > AutoGenerateConstant.AUTO_GENERATE_MAX_SIZE) {
            return AjaxResult.error("参数错误,不可选择大于七天！");
        }
        String todayJoin = StrUtil.join(",", bibleDate);
        String response = HttpUtils.sendPost(AutoGenerateConstant.AUTO_GENERATE_URL, todayJoin);
        List<BibleEventsDTO> bibleEventsDTOSList;
        List<BibleEvents> bibleEventsList;
        // 移除开头的 "json" 和结尾的点号
        String processedJson = response.replaceFirst(AutoGenerateConstant.AUTO_GENERATE_PREFIX, "").replaceAll("```$", "");
        log.info("processedJson: {}", processedJson);
        // 验证处理后的 JSON 是否以 { 开始，以 } 结束
        if (!processedJson.startsWith(AutoGenerateConstant.AUTO_GENERATE_JSON_PREFIX) || !processedJson.endsWith(AutoGenerateConstant.AUTO_GENERATE_JSON_SUFFIX)) {
            log.error("处理过的JSON无效: {}", processedJson);
            return AjaxResult.error("收到无效JSON");
        }
        //计数
        Integer updataCount = 0;
        Integer addCount = 0;
        try {
            bibleEventsDTOSList = JSON.parseArray(processedJson, BibleEventsDTO.class);
            bibleEventsList = BeanUtil.copyToList(bibleEventsDTOSList, BibleEvents.class);
            for (BibleEvents be : bibleEventsList) {
                // 检查数据库中是否已存在相同日期的记录
                BibleEvents existingRecord = baseMapper.selectOne(new LambdaQueryWrapper<BibleEvents>()
                        .eq(BibleEvents::getBibleDate, be.getBibleDate()));
                if (existingRecord != null) {
                    // 更新现有记录
                    existingRecord.setVerse(be.getVerse());
                    existingRecord.setVerseContent(be.getVerseContent());
                    existingRecord.setHistoryTodayEvents(be.getHistoryTodayEvents());
                    existingRecord.setCreateBy("AutoGenerate");
                    // 执行更新操作
                    baseMapper.updateById(existingRecord);
                    updataCount += 1;
                } else {
                    // 不存在相同日期的记录，设置createBy并插入新记录
                    be.setCreateBy("AutoGenerate");
                    baseMapper.insert(be);
                    addCount +=1;
                }
            }
        } catch (Exception e) {
            log.error("解析JSON出错: {}", e.getMessage());
            return AjaxResult.error("解析JSON出错: " + e.getMessage());
        }
        try {
            redisCache.deleteObject(AutoGenerateConstant.AUTO_GENERATE_BIBLE_KEY);
            redisCache.setCacheObject(AutoGenerateConstant.AUTO_GENERATE_BIBLE_KEY, bibleEventsDTOSList);
        } catch (Exception e) {
            log.error("redis存储出错: {}", e.getMessage());
            return AjaxResult.error("redis存储出错: " + e.getMessage());
        }
        return AjaxResult.success("自动生成成功,更新" + updataCount + "条,增加了" + addCount + "条", bibleEventsDTOSList);
    }
}

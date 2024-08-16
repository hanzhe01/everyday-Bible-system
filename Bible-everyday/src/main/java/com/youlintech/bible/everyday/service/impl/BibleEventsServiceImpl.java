package com.youlintech.bible.everyday.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlintech.bible.common.exception.ServiceException;
import com.youlintech.bible.common.utils.StringUtils;
import com.youlintech.bible.common.utils.bean.BeanValidators;
import com.youlintech.bible.everyday.domain.BibleEvents;
import com.youlintech.bible.everyday.mapper.BibleEventsMapper;
import com.youlintech.bible.everyday.service.IBibleEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.Map;

/**
 * 每日圣经Service业务层处理
 *
 * @author oy
 * @date 2024-08-14
 */
@Service
public class BibleEventsServiceImpl extends ServiceImpl<BibleEventsMapper, BibleEvents> implements IBibleEventsService {

    @Autowired
    private BibleEventsMapper bibleEventsMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询每日圣经列表
     *
     * @param tBibleEvents 每日圣经
     * @return 每日圣经
     */
    @Override
    public List<BibleEvents> selectBibleEventsList(BibleEvents tBibleEvents) {
        List<BibleEvents> tBibleEventsList = bibleEventsMapper.selectList(buildQueryWrapper(tBibleEvents));
        return tBibleEventsList;
    }

    /**
     * 导入每日圣经
     *
     * @param bibleEventsList 用户数据列表
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importBibleEvents(List<BibleEvents> bibleEventsList, String operName) {
        if (StringUtils.isNull(bibleEventsList) || bibleEventsList.size() == 0) {
            throw new ServiceException("导入每日圣经数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (BibleEvents bibleEvents : bibleEventsList) {
            try {
                BeanValidators.validateWithException(validator, bibleEvents);
                bibleEvents.setCreateBy(operName);
                bibleEventsMapper.insert(bibleEvents);
                successNum++;
                successMsg.append("<br/>" + successNum + "、每日圣经 " + bibleEvents.getVerse() + " 导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、每日圣经 " + bibleEvents.getBibleDate() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


    private LambdaQueryWrapper<BibleEvents> buildQueryWrapper(BibleEvents query) {
        Map<String, Object> params = query.getParams();
        LambdaQueryWrapper<BibleEvents> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(query.getVerse()), BibleEvents::getVerse, query.getVerse());
        lqw.between(params.get("beginBibleDate") != null && params.get("endBibleDate") != null,
                BibleEvents::getBibleDate, params.get("beginBibleDate"), params.get("endBibleDate"));
        // 添加按 getBibleDate 降序排序
        lqw.orderByDesc(BibleEvents::getBibleDate);
        return lqw;
    }

}

package com.youlintech.bible.everyday.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.everyday.domain.BibleEvents;
import com.youlintech.bible.everyday.domain.BibleView;
import com.youlintech.bible.everyday.mapper.BibleEventsMapper;
import com.youlintech.bible.everyday.mapper.BibleViewMapper;
import com.youlintech.bible.everyday.service.IBibleViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 每日圣经展示Service业务层处理
 *
 * @author oyhz
 * @date 2024-08-15
 */
@Service
public class BibleViewServiceImpl extends ServiceImpl<BibleViewMapper, BibleView> implements IBibleViewService {

    @Autowired
    private BibleViewMapper bibleViewMapper;
    @Autowired
    private BibleEventsMapper bibleEventsMapper;

    /**
     * 查询每日圣经展示列表
     *
     * @param bibleView 每日圣经展示
     * @return 每日圣经展示
     */
    @Override
    public List<BibleView> selectBibleViewList(BibleView bibleView)
    {
        List<BibleView> bibleViewList = bibleViewMapper.selectList(buildQueryWrapper(bibleView));
        return bibleViewList;
    }

    @Override
    public AjaxResult saveBibleView(BibleView bibleView) {
        String s = saveAndUpdata(bibleView);
        if (s != null) {
            return AjaxResult.error(501,s);
        }
        if (this.save(bibleView)) {
            return AjaxResult.success("保存成功",bibleView);
        }
        //通过bibleView获取素材ID的值，获取他的日期是否和展示的值相同
        return AjaxResult.error(501,"添加失败");
    }

    @Override
    public AjaxResult updateBibleView(BibleView bibleView) {
        String s = saveAndUpdata(bibleView);
        if (s != null) {
            return AjaxResult.error(501,s);
        }
        if (this.updateById(bibleView)) {
            return AjaxResult.success("修改成功",bibleView);
        }
        return AjaxResult.error(501,"修改失败");
    }

    private String saveAndUpdata(BibleView bibleView) {
        //获取每日圣经素材ID的值，获取他的日期是否和展示的值相同
        Long bibleId = bibleView.getBibleId();
        BibleEvents bibleEvents = bibleEventsMapper.selectById(bibleId);
        if (bibleEvents == null) {
            return "该每日圣经素材不存在,素材ID错误";
        }
        if (!bibleView.getBibleDate().equals(bibleEvents.getBibleDate())) {
            return "该每日圣经素材日期与展示日期不一致";
        }
        return null;
    }

    private LambdaQueryWrapper<BibleView> buildQueryWrapper(BibleView query) {
        Map<String, Object> params = query.getParams();
        LambdaQueryWrapper<BibleView> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

}

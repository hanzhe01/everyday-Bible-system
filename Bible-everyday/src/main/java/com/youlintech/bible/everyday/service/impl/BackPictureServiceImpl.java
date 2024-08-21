package com.youlintech.bible.everyday.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.redis.RedisCache;
import com.youlintech.bible.common.utils.DateUtils;
import com.youlintech.bible.everyday.constant.BackPictureConstant;
import com.youlintech.bible.everyday.domain.BackPicture;
import com.youlintech.bible.everyday.enums.PictureEnum;
import com.youlintech.bible.everyday.mapper.BackPictureMapper;
import com.youlintech.bible.everyday.service.IBackPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 背景图片Service业务层处理
 *
 * @author oyhz
 * @date 2024-08-21
 */
@Service
public class BackPictureServiceImpl extends ServiceImpl<BackPictureMapper, BackPicture> implements IBackPictureService {

    @Autowired
    private BackPictureMapper backPictureMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询背景图片列表
     *
     * @param backPicture 背景图片
     * @return 背景图片
     */
    @Override
    public List<BackPicture> selectBackPictureList(BackPicture backPicture) {
        List<BackPicture> backPictureList = backPictureMapper.selectList(buildQueryWrapper(backPicture));
        return backPictureList;
    }

    @Override
    public AjaxResult addBackPicture(BackPicture backPicture) {
        backPicture.setUploadTime(DateUtils.getNowDate());
        PictureEnum isBg = backPicture.getIsBg();
        if (!isBg.equals(PictureEnum.IS_ENABLE)) {
            this.save(backPicture);
            return AjaxResult.success(backPicture);
        }
        BackPicture oldPicture = getRedisBackPicture();
        if (oldPicture != null) {
            //修改之前的为是否启用为禁用
            oldPicture.setIsBg(PictureEnum.NOT_ENABLE);
            this.updateById(oldPicture);
        }
        redisCache.deleteObject(BackPictureConstant.BACK_PICTURE_KEY);
        redisCache.deleteObject(BackPictureConstant.BACK_PICTURE_ID_KEY);
        redisCache.setCacheObject(BackPictureConstant.BACK_PICTURE_KEY, backPicture.getPicture());
        if (this.save(backPicture)) {
            return AjaxResult.success(backPicture);
        }
        return AjaxResult.error();
    }

    @Override
    public AjaxResult updateBackPicture(BackPicture backPicture) {
        /**
         * 当redisBackPicture缓存中不存在对象时
         */
        BackPicture redisBackPicture = getRedisBackPicture();
        if (redisBackPicture == null) {
            if (backPicture.getIsBg().equals(PictureEnum.IS_ENABLE)) {
                return updateRedisBackPicture(null, backPicture);
            }
            this.updateById(backPicture);
            return AjaxResult.success("修改成功", backPicture);
        }
        /**
         * 当redisBackPicture缓存中存在对象时
         */
        if (!redisBackPicture.getId().equals(backPicture.getId())) {
            if (backPicture.getIsBg().equals(PictureEnum.IS_ENABLE)){
                return updateRedisBackPicture(redisBackPicture, backPicture);
            }
            this.updateById(backPicture);
            return AjaxResult.success("修改成功", backPicture);
        }
        if (backPicture.getIsBg().equals(PictureEnum.NOT_ENABLE)) {
            return AjaxResult.warn("修改失败，至少保留一个背景图片！");
        }
        //当缓存中存在对象且id相同，修改缓存中的对象，但不是修改启动状态
        this.updateById(backPicture);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult removeBackPictureByIds(Collection<?> list) {
        BackPicture redisBackPicture = getRedisBackPicture();
        if (redisBackPicture == null) {
            AjaxResult.warn("未设置背景图片！");
        }
        list.forEach(id -> {
            if (redisBackPicture != null && id.equals(redisBackPicture.getId())) {
                AjaxResult.warn("删除失败，存在设置的背景图片！");
            }
        });
        this.removeByIds(list);
        return AjaxResult.success("删除成功");
    }

    private BackPicture getRedisBackPicture() {
        /**
         *
         * 首先检查 backPictureId 是否为 null。
         * 如果 backPictureId 不为 null，则通过调用 this.getById(backPictureId) 方法来获取对应的 BackPicture 对象。
         * 如果 backPictureId 为 null，则从缓存中获取默认的 BackPicture 对象（通过键 BackPictureConstant.BACK_PICTURE_KEY），然后使用 backPictureMapper.getOneByPicture 方法来获取 BackPicture 对象。
         */
        //获取缓存中的id和路径值，并找到对象
        Long redisPictureId = redisCache.getCacheObject(BackPictureConstant.BACK_PICTURE_ID_KEY);
        String pictureUrl = redisCache.getCacheObject(BackPictureConstant.BACK_PICTURE_KEY);
        if (redisPictureId == null && pictureUrl == null) {
            return null;
        }
        return redisPictureId != null ? this.getById(redisPictureId) :
                backPictureMapper.getOneByPicture(pictureUrl);
    }

    private AjaxResult updateRedisBackPicture(BackPicture oldBackPicture,BackPicture newBackPicture) {
        if (oldBackPicture != null) {
            //删除之前缓存的id值和路径
            redisCache.deleteObject(BackPictureConstant.BACK_PICTURE_KEY);
            redisCache.deleteObject(BackPictureConstant.BACK_PICTURE_ID_KEY);
            //修改之前的值,并更新
            oldBackPicture.setIsBg(PictureEnum.NOT_ENABLE);
            this.updateById(oldBackPicture);
        }
        //设置更新后的值
        redisCache.setCacheObject(BackPictureConstant.BACK_PICTURE_KEY, newBackPicture.getPicture());
        redisCache.setCacheObject(BackPictureConstant.BACK_PICTURE_ID_KEY, newBackPicture.getId());
        this.updateById(newBackPicture);
        return AjaxResult.success("修改成功", newBackPicture);
    }

    private LambdaQueryWrapper<BackPicture> buildQueryWrapper(BackPicture query) {
        Map<String, Object> params = query.getParams();
        LambdaQueryWrapper<BackPicture> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

}

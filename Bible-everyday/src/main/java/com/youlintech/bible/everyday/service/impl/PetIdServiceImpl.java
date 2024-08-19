package com.youlintech.bible.everyday.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlintech.bible.common.exception.ServiceException;
import com.youlintech.bible.common.utils.StringUtils;
import com.youlintech.bible.common.utils.bean.BeanValidators;
import com.youlintech.bible.everyday.domain.PetId;
import com.youlintech.bible.everyday.mapper.PetIdMapper;
import com.youlintech.bible.everyday.service.IPetIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.Map;

/**
 * 白名单列表Service业务层处理
 *
 * @author oyhz
 * @date 2024-08-19
 */
@Service
public class PetIdServiceImpl extends ServiceImpl<PetIdMapper, PetId> implements IPetIdService {

    @Autowired
    private PetIdMapper petIdMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询白名单列表列表
     *
     * @param petId 白名单列表
     * @return 白名单列表
     */
    @Override
    public List<PetId> selectPetIdList(PetId petId) {
        List<PetId> petIdList = petIdMapper.selectList(buildQueryWrapper(petId));
        return petIdList;
    }

    /**
     * 导入白名单数据
     *
     * @param petIdList 用户数据列表
     * @param operName  操作用户
     * @return 结果
     */
    @Override
    public String importPet(List<PetId> petIdList, String operName) {
        if (StringUtils.isNull(petIdList) || petIdList.size() == 0) {
            throw new ServiceException("导入白名单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (PetId petId : petIdList) {
            try {
                BeanValidators.validateWithException(validator, petId);
                petId.setCreateBy(operName);
                petIdMapper.insert(petId);
                successNum++;
                successMsg.append("<br/>" + successNum + "、白名单 " + petId.getPetId() + " 导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + petId.getPetId() + " 导入失败：";
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


    private LambdaQueryWrapper<PetId> buildQueryWrapper(PetId query) {
        Map<String, Object> params = query.getParams();
        LambdaQueryWrapper<PetId> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(query.getPetId()), PetId::getPetId, query.getPetId());
        return lqw;
    }

}

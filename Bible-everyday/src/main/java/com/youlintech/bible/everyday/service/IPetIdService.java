package com.youlintech.bible.everyday.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlintech.bible.everyday.domain.PetId;

import java.util.List;

/**
 * 白名单列表Service接口
 *
 * @author oyhz
 * @date 2024-08-19
 */
public interface IPetIdService extends IService<PetId> {

    /**
     * 查询白名单列表列表
     *
     * @param petId 白名单列表
     * @return 白名单列表集合
     */
    public List<PetId> selectPetIdList(PetId petId);

    String importPet(List<PetId> petIdList, String operName);
}

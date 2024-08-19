package com.youlintech.bible.everyday.controller;

import com.youlintech.bible.common.annotation.Log;
import com.youlintech.bible.common.core.controller.BaseController;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.page.TableDataInfo;
import com.youlintech.bible.common.enums.BusinessType;
import com.youlintech.bible.common.utils.poi.ExcelUtil;
import com.youlintech.bible.everyday.domain.PetId;
import com.youlintech.bible.everyday.service.IPetIdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 白名单列表Controller
 *
 * @author oyhz
 * @date 2024-08-19
 */
@Api(tags="白名单列表管理")
@RestController
@RequestMapping("/whiteList/pet")
public class PetIdController extends BaseController
{
    @Autowired
    private IPetIdService petIdService;

    /**
     * 查询白名单列表列表
     */
    @ApiOperation("查询白名单列表列表")
    @PreAuthorize("@ss.hasPermi('whiteList:pet:list')")
    @GetMapping("/list")
    public TableDataInfo list(PetId petId)
    {
        startPage();
        List<PetId> list = petIdService.selectPetIdList(petId);
        return getDataTable(list);
    }

    /**
     * 导出白名单列表列表
     */
    @ApiOperation("导出白名单列表列表")
    @PreAuthorize("@ss.hasPermi('whiteList:pet:export')")
    @Log(title = "白名单列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PetId petId)
    {
        List<PetId> list = petIdService.selectPetIdList(petId);
        ExcelUtil<PetId> util = new ExcelUtil<PetId>(PetId.class);
        util.exportExcel(response, list, "白名单列表数据");
    }

    /**
     * 获取白名单列表详细信息
     */
    @ApiOperation("获取白名单列表详细信息")
    @PreAuthorize("@ss.hasPermi('whiteList:pet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(petIdService.getById(id));
    }

    /**
     * 新增白名单列表
     */
    @ApiOperation("新增白名单列表")
    @PreAuthorize("@ss.hasPermi('whiteList:pet:add')")
    @Log(title = "白名单列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PetId petId)
    {
        return toAjax(petIdService.save(petId));
    }

    /**
     * 修改白名单列表
     */
    @ApiOperation("修改白名单列表")
    @PreAuthorize("@ss.hasPermi('whiteList:pet:edit')")
    @Log(title = "白名单列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PetId petId)
    {
        return toAjax(petIdService.updateById(petId));
    }

    /**
     * 删除白名单列表
     */
    @ApiOperation("删除白名单列表")
    @PreAuthorize("@ss.hasPermi('whiteList:pet:remove')")
    @Log(title = "白名单列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(petIdService.removeByIds(Arrays.asList(ids)));
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<PetId> util = new ExcelUtil<PetId>(PetId.class);
        util.importTemplateExcel(response, "白名单数据");
    }

    @Log(title = "白名单列表管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('whiteList:pet:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<PetId> util = new ExcelUtil<PetId>(PetId.class);
        List<PetId> petIdList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = petIdService.importPet(petIdList, operName);
        return success(message);
    }



}

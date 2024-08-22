package com.youlintech.bible.everyday.controller;

import com.youlintech.bible.common.annotation.Log;
import com.youlintech.bible.common.core.controller.BaseController;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.page.TableDataInfo;
import com.youlintech.bible.common.enums.BusinessType;
import com.youlintech.bible.common.utils.poi.ExcelUtil;
import com.youlintech.bible.everyday.domain.BackPicture;
import com.youlintech.bible.everyday.service.IBackPictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 背景图片Controller
 *
 * @author oyhz
 * @date 2024-08-21
 */
@Api(tags="背景图片管理")
@RestController
@RequestMapping("/picture/picture")
public class BackPictureController extends BaseController
{
    @Autowired
    private IBackPictureService backPictureService;

    /**
     * 查询背景图片列表
     */
    @ApiOperation("查询背景图片列表")
    @PreAuthorize("@ss.hasPermi('picture:picture:list')")
    @GetMapping("/list")
    public TableDataInfo list(BackPicture backPicture)
    {
        startPage();
        List<BackPicture> list = backPictureService.selectBackPictureList(backPicture);
        return getDataTable(list);
    }

    /**
     * 导出背景图片列表
     */
    @ApiOperation("导出背景图片列表")
    @PreAuthorize("@ss.hasPermi('picture:picture:export')")
    @Log(title = "背景图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BackPicture backPicture)
    {
        List<BackPicture> list = backPictureService.selectBackPictureList(backPicture);
        ExcelUtil<BackPicture> util = new ExcelUtil<BackPicture>(BackPicture.class);
        util.exportExcel(response, list, "背景图片数据");
    }

    /**
     * 获取背景图片详细信息
     */
    @ApiOperation("获取背景图片详细信息")
    @PreAuthorize("@ss.hasPermi('picture:picture:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(backPictureService.getById(id));
    }

    /**
     * 新增背景图片
     */
    @ApiOperation("新增背景图片")
    @PreAuthorize("@ss.hasPermi('picture:picture:add')")
    @Log(title = "背景图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BackPicture backPicture)
    {
        return backPictureService.addBackPicture(backPicture);
    }

    /**
     * 修改背景图片
     */
    @ApiOperation("修改背景图片")
    @PreAuthorize("@ss.hasPermi('picture:picture:edit')")
    @Log(title = "背景图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BackPicture backPicture)
    {
        return backPictureService.updateBackPicture(backPicture);
    }

    /**
     * 删除背景图片
     */
    @ApiOperation("删除背景图片")
    @PreAuthorize("@ss.hasPermi('picture:picture:remove')")
    @Log(title = "背景图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return backPictureService.removeBackPictureByIds(Arrays.asList(ids));
    }
}

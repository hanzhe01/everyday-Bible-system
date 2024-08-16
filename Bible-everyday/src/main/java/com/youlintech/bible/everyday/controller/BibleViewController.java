package com.youlintech.bible.everyday.controller;

import com.youlintech.bible.common.annotation.Log;
import com.youlintech.bible.common.core.controller.BaseController;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.page.TableDataInfo;
import com.youlintech.bible.common.enums.BusinessType;
import com.youlintech.bible.common.utils.poi.ExcelUtil;
import com.youlintech.bible.everyday.domain.BibleView;
import com.youlintech.bible.everyday.service.IBibleViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 每日圣经展示Controller
 *
 * @author oyhz
 * @date 2024-08-15
 */
@Api(tags="每日圣经展示管理")
@RestController
@RequestMapping("/everyday/bibleview")
public class BibleViewController extends BaseController
{
    @Autowired
    private IBibleViewService bibleViewService;

    /**
     * 查询每日圣经展示列表
     */
    @ApiOperation("查询每日圣经展示列表")
    @PreAuthorize("@ss.hasPermi('everyday:bibleview:list')")
    @GetMapping("/list")
    public TableDataInfo list(BibleView bibleView)
    {
        startPage();
        List<BibleView> list = bibleViewService.selectBibleViewList(bibleView);
        return getDataTable(list);
    }

    /**
     * 导出每日圣经展示列表
     */
    @ApiOperation("导出每日圣经展示列表")
    @PreAuthorize("@ss.hasPermi('everyday:bibleview:export')")
    @Log(title = "每日圣经展示", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BibleView bibleView)
    {
        List<BibleView> list = bibleViewService.selectBibleViewList(bibleView);
        ExcelUtil<BibleView> util = new ExcelUtil<BibleView>(BibleView.class);
        util.exportExcel(response, list, "每日圣经展示数据");
    }

    /**
     * 获取每日圣经展示详细信息
     */
    @ApiOperation("获取每日圣经展示详细信息")
    @PreAuthorize("@ss.hasPermi('everyday:bibleview:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bibleViewService.getById(id));
    }

    /**
     * 新增每日圣经展示
     */
    @ApiOperation("新增每日圣经展示")
    @PreAuthorize("@ss.hasPermi('everyday:bibleview:add')")
    @Log(title = "每日圣经展示", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BibleView bibleView)
    {
        return bibleViewService.saveBibleView(bibleView);
    }

    /**
     * 修改每日圣经展示
     */
    @ApiOperation("修改每日圣经展示")
    @PreAuthorize("@ss.hasPermi('everyday:bibleview:edit')")
    @Log(title = "每日圣经展示", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BibleView bibleView)
    {
        return bibleViewService.updateBibleView(bibleView);
    }

    /**
     * 删除每日圣经展示
     */
    @ApiOperation("删除每日圣经展示")
    @PreAuthorize("@ss.hasPermi('everyday:bibleview:remove')")
    @Log(title = "每日圣经展示", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bibleViewService.removeByIds(Arrays.asList(ids)));
    }
}

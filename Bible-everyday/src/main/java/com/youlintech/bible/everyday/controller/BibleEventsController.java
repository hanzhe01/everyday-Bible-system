package com.youlintech.bible.everyday.controller;

import com.youlintech.bible.common.annotation.Log;
import com.youlintech.bible.common.core.controller.BaseController;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.common.core.page.TableDataInfo;
import com.youlintech.bible.common.enums.BusinessType;
import com.youlintech.bible.common.utils.poi.ExcelUtil;
import com.youlintech.bible.everyday.domain.BibleEvents;
import com.youlintech.bible.everyday.service.IBibleEventsService;
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
 * 每日圣经Controller
 *
 * @author oy
 * @date 2024-08-14
 */
@Api(tags="每日圣经管理")
@RestController
@RequestMapping("/bible/everyday")
public class BibleEventsController extends BaseController
{
    @Autowired
    private IBibleEventsService bibleEventsService;

    /**
     * 查询每日圣经列表
     */
    @ApiOperation("查询每日圣经列表")
    @PreAuthorize("@ss.hasPermi('bible:everyday:list')")
    @GetMapping("/list")
    public TableDataInfo list(BibleEvents tBibleEvents)
    {
        startPage();
        List<BibleEvents> list = bibleEventsService.selectBibleEventsList(tBibleEvents);
        return getDataTable(list);
    }

    /**
     * 导出每日圣经列表
     */
    @ApiOperation("导出每日圣经列表")
    @PreAuthorize("@ss.hasPermi('bible:everyday:export')")
    @Log(title = "每日圣经", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BibleEvents tBibleEvents)
    {
        List<BibleEvents> list = bibleEventsService.selectBibleEventsList(tBibleEvents);
        ExcelUtil<BibleEvents> util = new ExcelUtil<BibleEvents>(BibleEvents.class);
        util.exportExcel(response, list, "每日圣经数据");
    }

    /**
     * 获取每日圣经详细信息
     */
    @ApiOperation("获取每日圣经详细信息")
    @PreAuthorize("@ss.hasPermi('bible:everyday:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bibleEventsService.getById(id));
    }

    /**
     * 新增每日圣经
     */
    @ApiOperation("新增每日圣经")
    @PreAuthorize("@ss.hasPermi('bible:everyday:add')")
    @Log(title = "每日圣经", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BibleEvents tBibleEvents)
    {
        return toAjax(bibleEventsService.save(tBibleEvents));
    }

    /**
     * 修改每日圣经
     */
    @ApiOperation("修改每日圣经")
    @PreAuthorize("@ss.hasPermi('bible:everyday:edit')")
    @Log(title = "每日圣经", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BibleEvents tBibleEvents)
    {
        return toAjax(bibleEventsService.updateById(tBibleEvents));
    }

    /**
     * 删除每日圣经
     */
    @ApiOperation("删除每日圣经")
    @PreAuthorize("@ss.hasPermi('bible:everyday:remove')")
    @Log(title = "每日圣经", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bibleEventsService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 获取导入模版
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<BibleEvents> util = new ExcelUtil<BibleEvents>(BibleEvents.class);
        util.importTemplateExcel(response, "每日圣经");
    }

    @Log(title = "每日圣经", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('bible:everyday:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<BibleEvents> util = new ExcelUtil<BibleEvents>(BibleEvents.class);
        List<BibleEvents> bibleEventsList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = bibleEventsService.importBibleEvents(bibleEventsList, operName);
        return success(message);
    }
}

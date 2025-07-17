package org.dromara.crm.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.system.domain.vo.EmailMailMessageVo;
import org.dromara.system.domain.bo.EmailMailMessageBo;
import org.dromara.system.service.IEmailMailMessageService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 邮件，支持多租户（模块：email）
 *
 * @author Lion Li
 * @date 2025-07-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/mailMessage")
public class EmailMailMessageController extends BaseController {

    private final IEmailMailMessageService emailMailMessageService;

    /**
     * 查询邮件，支持多租户（模块：email）列表
     */
    @SaCheckPermission("system:mailMessage:list")
    @GetMapping("/list")
    public TableDataInfo<EmailMailMessageVo> list(EmailMailMessageBo bo, PageQuery pageQuery) {
        return emailMailMessageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出邮件，支持多租户（模块：email）列表
     */
    @SaCheckPermission("system:mailMessage:export")
    @Log(title = "邮件，支持多租户（模块：email）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(EmailMailMessageBo bo, HttpServletResponse response) {
        List<EmailMailMessageVo> list = emailMailMessageService.queryList(bo);
        ExcelUtil.exportExcel(list, "邮件，支持多租户（模块：email）", EmailMailMessageVo.class, response);
    }

    /**
     * 获取邮件，支持多租户（模块：email）详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:mailMessage:query")
    @GetMapping("/{id}")
    public R<EmailMailMessageVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(emailMailMessageService.queryById(id));
    }

    /**
     * 新增邮件，支持多租户（模块：email）
     */
    @SaCheckPermission("system:mailMessage:add")
    @Log(title = "邮件，支持多租户（模块：email）", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody EmailMailMessageBo bo) {
        return toAjax(emailMailMessageService.insertByBo(bo));
    }

    /**
     * 修改邮件，支持多租户（模块：email）
     */
    @SaCheckPermission("system:mailMessage:edit")
    @Log(title = "邮件，支持多租户（模块：email）", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody EmailMailMessageBo bo) {
        return toAjax(emailMailMessageService.updateByBo(bo));
    }

    /**
     * 删除邮件，支持多租户（模块：email）
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:mailMessage:remove")
    @Log(title = "邮件，支持多租户（模块：email）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(emailMailMessageService.deleteWithValidByIds(List.of(ids), true));
    }
}

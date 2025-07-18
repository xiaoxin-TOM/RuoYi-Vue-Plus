package org.dromara.crm.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.crm.domain.bo.EmailMailMessageBo;
import org.dromara.crm.domain.vo.EmailMailMessageVo;
import org.dromara.crm.service.IEmailMailMessageService;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.service.ISysUserService;
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
@RequestMapping("/mail/mailMessage")
public class EmailMailMessageController extends BaseController {

    private final IEmailMailMessageService emailMailMessageService;
    private final ISysUserService sysUserService;

    /**
     * 查询邮件，支持多租户（模块：email）列表
     */
    @SaCheckPermission("mail:mailMessage:list")
    @GetMapping("/list")
    public TableDataInfo<EmailMailMessageVo> list(EmailMailMessageBo bo, PageQuery pageQuery) {
        return emailMailMessageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出邮件，支持多租户（模块：email）列表
     */
    @SaCheckPermission("mail:mailMessage:export")
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
    @SaCheckPermission("mail:mailMessage:query")
    @GetMapping("/{id}")
    public R<EmailMailMessageVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(emailMailMessageService.queryById(id));
    }

    /**
     * 新增邮件，支持多租户（模块：email）
     */
    @SaCheckPermission("mail:mailMessage:add")
    @Log(title = "邮件，支持多租户（模块：email）", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody EmailMailMessageBo bo) {
        return toAjax(emailMailMessageService.insertByBo(bo));
    }

    /**
     * 修改邮件，支持多租户（模块：email）
     */
    @SaCheckPermission("mail:mailMessage:edit")
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
    @SaCheckPermission("mail:mailMessage:remove")
    @Log(title = "邮件，支持多租户（模块：email）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(emailMailMessageService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 查询用户邮箱
     */
    @SaCheckPermission("mail:mailMessage:accountInfo")
    @GetMapping("/accountInfo")
    public R<SysUserVo> accountInfo() {
        Long userId = LoginHelper.getUserId();
        SysUserVo user = sysUserService.selectUserById(userId);
        return R.ok(user);
    }

    /**
     * 同步邮件到本地数据库
     */
    @SaCheckPermission("mail:mailMessage:sync")
    @Log(title = "同步邮件", businessType = BusinessType.OTHER)
    @RepeatSubmit(interval = 60000) // 设置1分钟内不能重复提交
    @PostMapping("/sync")
    public R<Void> syncMail() {
        // 获取当前登录用户
        Long userId = LoginHelper.getUserId();
        SysUserVo user = sysUserService.selectUserById(userId);

        // 检查用户邮箱配置
        if (user.getEmail() == null || user.getEmailPassword() == null) {
            return R.fail("请先配置邮箱账号和密码");
        }

        try {
            // 调用service层的同步方法
            emailMailMessageService.syncMailMessage(user);
            return R.ok();
        } catch (Exception e) {
            return R.fail("同步邮件失败：" + e.getMessage());
        }
    }
}

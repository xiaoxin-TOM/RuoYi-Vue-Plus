package org.dromara.crm.service;


import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.crm.domain.bo.EmailMailMessageBo;
import org.dromara.crm.domain.vo.EmailMailMessageVo;
import org.dromara.system.domain.vo.SysUserVo;

import java.util.Collection;
import java.util.List;

/**
 * 邮件，支持多租户（模块：email）Service接口
 *
 * @author Lion Li
 * @date 2025-07-17
 */
public interface IEmailMailMessageService {

    /**
     * 查询邮件，支持多租户（模块：email）
     *
     * @param id 主键
     * @return 邮件，支持多租户（模块：email）
     */
    EmailMailMessageVo queryById(Long id);

    /**
     * 分页查询邮件，支持多租户（模块：email）列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 邮件，支持多租户（模块：email）分页列表
     */
    TableDataInfo<EmailMailMessageVo> queryPageList(EmailMailMessageBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的邮件，支持多租户（模块：email）列表
     *
     * @param bo 查询条件
     * @return 邮件，支持多租户（模块：email）列表
     */
    List<EmailMailMessageVo> queryList(EmailMailMessageBo bo);

    /**
     * 新增邮件，支持多租户（模块：email）
     *
     * @param bo 邮件，支持多租户（模块：email）
     * @return 是否新增成功
     */
    Boolean insertByBo(EmailMailMessageBo bo);

    /**
     * 修改邮件，支持多租户（模块：email）
     *
     * @param bo 邮件，支持多租户（模块：email）
     * @return 是否修改成功
     */
    Boolean updateByBo(EmailMailMessageBo bo);

    /**
     * 校验并批量删除邮件，支持多租户（模块：email）信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 同步用户邮件到本地数据库
     *
     * @param user 用户信息
     */
    void syncMailMessage(SysUserVo user);
}

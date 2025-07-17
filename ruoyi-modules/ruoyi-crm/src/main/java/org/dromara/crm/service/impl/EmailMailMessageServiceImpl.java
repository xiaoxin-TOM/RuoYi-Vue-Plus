package org.dromara.crm.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.EmailMailMessageBo;
import org.dromara.system.domain.vo.EmailMailMessageVo;
import org.dromara.system.domain.EmailMailMessage;
import org.dromara.system.mapper.EmailMailMessageMapper;
import org.dromara.system.service.IEmailMailMessageService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 邮件，支持多租户（模块：email）Service业务层处理
 *
 * @author Lion Li
 * @date 2025-07-17
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailMailMessageServiceImpl implements IEmailMailMessageService {

    private final EmailMailMessageMapper baseMapper;

    /**
     * 查询邮件，支持多租户（模块：email）
     *
     * @param id 主键
     * @return 邮件，支持多租户（模块：email）
     */
    @Override
    public EmailMailMessageVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询邮件，支持多租户（模块：email）列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 邮件，支持多租户（模块：email）分页列表
     */
    @Override
    public TableDataInfo<EmailMailMessageVo> queryPageList(EmailMailMessageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<EmailMailMessage> lqw = buildQueryWrapper(bo);
        Page<EmailMailMessageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的邮件，支持多租户（模块：email）列表
     *
     * @param bo 查询条件
     * @return 邮件，支持多租户（模块：email）列表
     */
    @Override
    public List<EmailMailMessageVo> queryList(EmailMailMessageBo bo) {
        LambdaQueryWrapper<EmailMailMessage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<EmailMailMessage> buildQueryWrapper(EmailMailMessageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<EmailMailMessage> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(EmailMailMessage::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getMailId()), EmailMailMessage::getMailId, bo.getMailId());
        lqw.eq(StringUtils.isNotBlank(bo.getFolder()), EmailMailMessage::getFolder, bo.getFolder());
        lqw.eq(StringUtils.isNotBlank(bo.getFromAddress()), EmailMailMessage::getFromAddress, bo.getFromAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getToAddresses()), EmailMailMessage::getToAddresses, bo.getToAddresses());
        lqw.eq(StringUtils.isNotBlank(bo.getCcAddresses()), EmailMailMessage::getCcAddresses, bo.getCcAddresses());
        lqw.eq(StringUtils.isNotBlank(bo.getBccAddresses()), EmailMailMessage::getBccAddresses, bo.getBccAddresses());
        lqw.eq(StringUtils.isNotBlank(bo.getSubject()), EmailMailMessage::getSubject, bo.getSubject());
        lqw.eq(StringUtils.isNotBlank(bo.getBodyText()), EmailMailMessage::getBodyText, bo.getBodyText());
        lqw.eq(StringUtils.isNotBlank(bo.getBodyHtml()), EmailMailMessage::getBodyHtml, bo.getBodyHtml());
        lqw.eq(bo.getSentDate() != null, EmailMailMessage::getSentDate, bo.getSentDate());
        lqw.eq(bo.getReceivedDate() != null, EmailMailMessage::getReceivedDate, bo.getReceivedDate());
        lqw.eq(bo.getIsRead() != null, EmailMailMessage::getIsRead, bo.getIsRead());
        lqw.eq(bo.getHasAttachments() != null, EmailMailMessage::getHasAttachments, bo.getHasAttachments());
        lqw.eq(StringUtils.isNotBlank(bo.getAttachments()), EmailMailMessage::getAttachments, bo.getAttachments());
        lqw.eq(StringUtils.isNotBlank(bo.getFlags()), EmailMailMessage::getFlags, bo.getFlags());
        lqw.eq(bo.getCreatedAt() != null, EmailMailMessage::getCreatedAt, bo.getCreatedAt());
        lqw.eq(bo.getUpdatedAt() != null, EmailMailMessage::getUpdatedAt, bo.getUpdatedAt());
        return lqw;
    }

    /**
     * 新增邮件，支持多租户（模块：email）
     *
     * @param bo 邮件，支持多租户（模块：email）
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(EmailMailMessageBo bo) {
        EmailMailMessage add = MapstructUtils.convert(bo, EmailMailMessage.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改邮件，支持多租户（模块：email）
     *
     * @param bo 邮件，支持多租户（模块：email）
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(EmailMailMessageBo bo) {
        EmailMailMessage update = MapstructUtils.convert(bo, EmailMailMessage.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(EmailMailMessage entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除邮件，支持多租户（模块：email）信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}

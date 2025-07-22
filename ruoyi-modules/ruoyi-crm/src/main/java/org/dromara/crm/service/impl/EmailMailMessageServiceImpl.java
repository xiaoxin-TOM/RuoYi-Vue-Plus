package org.dromara.crm.service.impl;

import jakarta.mail.internet.MimeUtility;
import org.dromara.common.core.exception.ServiceException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.system.domain.vo.SysUserVo;
import org.springframework.stereotype.Service;
import org.dromara.crm.domain.bo.EmailMailMessageBo;
import org.dromara.crm.domain.vo.EmailMailMessageVo;
import org.dromara.crm.domain.EmailMailMessage;
import org.dromara.crm.mapper.EmailMailMessageMapper;
import org.dromara.crm.service.IEmailMailMessageService;

import java.util.*;

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
        lqw.orderByDesc(EmailMailMessage::getReceivedDate);
        lqw.eq(StringUtils.isNotBlank(bo.getMailId()), EmailMailMessage::getMailId, bo.getMailId());
        lqw.eq(StringUtils.isNotBlank(bo.getFolder()), EmailMailMessage::getFolder, bo.getFolder());
        lqw.eq(StringUtils.isNotBlank(bo.getFromAddress()), EmailMailMessage::getFromAddress, bo.getFromAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getToAddresses()), EmailMailMessage::getToAddresses, bo.getToAddresses());
        lqw.eq(StringUtils.isNotBlank(bo.getCcAddresses()), EmailMailMessage::getCcAddresses, bo.getCcAddresses());
        lqw.eq(StringUtils.isNotBlank(bo.getBccAddresses()), EmailMailMessage::getBccAddresses, bo.getBccAddresses());
        lqw.eq(StringUtils.isNotBlank(bo.getSubject()), EmailMailMessage::getSubject, bo.getSubject());
        lqw.eq(StringUtils.isNotBlank(bo.getBodyText()), EmailMailMessage::getBodyText, bo.getBodyText());
        lqw.eq(StringUtils.isNotBlank(bo.getBodyHtml()), EmailMailMessage::getBodyHtml, bo.getBodyHtml());
        lqw.eq(bo.getUserId() != null, EmailMailMessage::getUserId, bo.getUserId());
        lqw.eq(bo.getSentDate() != null, EmailMailMessage::getSentDate, bo.getSentDate());
        lqw.eq(bo.getReceivedDate() != null, EmailMailMessage::getReceivedDate, bo.getReceivedDate());
        lqw.eq(bo.getIsRead() != null, EmailMailMessage::getIsRead, bo.getIsRead());
        lqw.eq(bo.getHasAttachments() != null, EmailMailMessage::getHasAttachments, bo.getHasAttachments());
        lqw.eq(StringUtils.isNotBlank(bo.getAttachments()), EmailMailMessage::getAttachments, bo.getAttachments());
        lqw.eq(StringUtils.isNotBlank(bo.getFlags()), EmailMailMessage::getFlags, bo.getFlags());
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

    @Override
    public void syncMailMessage(SysUserVo user) {
        try {
            // 1. 创建邮件会话
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imap");
            props.setProperty("mail.imap.host", "imap.exmail.qq.com"); // 根据实际邮箱服务器配置
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.ssl.enable", "true");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect(user.getEmail(), user.getEmailPassword());

            // 2. 获取收件箱
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // 3. 获取所有邮件
            Message[] messages = folder.getMessages();

            for (Message message : messages) {
                // 4. 检查邮件是否已存在
                String mailId = getMessageId(message);
                LambdaQueryWrapper<EmailMailMessage> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(EmailMailMessage::getMailId, mailId);
                if (baseMapper.exists(wrapper)) {
                    continue;
                }

                // 5. 构建邮件对象
                // 5. 构建邮件对象
                EmailMailMessageBo bo = new EmailMailMessageBo();
                bo.setMailId(mailId);
                bo.setFolder("INBOX");

// 设置发件人地址
                bo.setFromAddress(decodeEmailAddress(InternetAddress.toString(message.getFrom())));

// 设置收件人地址
                Address[] toAddrs = message.getRecipients(Message.RecipientType.TO);
                bo.setToAddresses(toAddrs != null ? decodeEmailAddress(InternetAddress.toString(toAddrs)) : "");

// 设置抄送地址
                Address[] ccAddrs = message.getRecipients(Message.RecipientType.CC);
                if (ccAddrs != null) {
                    bo.setCcAddresses(decodeEmailAddress(InternetAddress.toString(ccAddrs)));
                }

// 设置密送地址
                Address[] bccAddrs = message.getRecipients(Message.RecipientType.BCC);
                if (bccAddrs != null) {
                    bo.setBccAddresses(decodeEmailAddress(InternetAddress.toString(bccAddrs)));
                }

                bo.setSubject(message.getSubject());
                bo.setSentDate(message.getSentDate());
                bo.setReceivedDate(message.getReceivedDate());
                bo.setIsRead(message.getFlags().contains(Flags.Flag.SEEN) ? 1L : 0L);
                // 6. 处理邮件内容
                Object content = message.getContent();
                if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    handleMultipart(multipart, bo);
                } else {
                    bo.setBodyText(content.toString());
                }
                // 6.1 设置用户id
                bo.setUserId(user.getUserId());

                // 7. 保存到数据库
                insertByBo(bo);
            }

            // 8. 关闭连接
            folder.close(false);
            store.close();

        } catch (Exception e) {
            log.error("同步邮件失败", e);
            throw new ServiceException("同步邮件失败：" + e.getMessage());
        }
    }

    /**
     * 获取邮件ID
     */
    private String getMessageId(Message message) throws MessagingException {
        String[] headers = message.getHeader("Message-ID");
        return headers != null && headers.length > 0 ? headers[0] : String.valueOf(message.getMessageNumber());
    }

    /**
     * 处理多部分邮件内容
     */
    private void handleMultipart(Multipart multipart, EmailMailMessageBo bo) throws Exception {
        int count = multipart.getCount();
        StringBuilder textContent = new StringBuilder();
        StringBuilder htmlContent = new StringBuilder();
        List<String> attachments = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            String disposition = bodyPart.getDisposition();

            if (disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) ||
                disposition.equalsIgnoreCase(Part.INLINE))) {
                // 处理附件
                attachments.add(bodyPart.getFileName());
            } else if (bodyPart.getContentType().toLowerCase().startsWith("text/plain")) {
                textContent.append(bodyPart.getContent().toString());
            } else if (bodyPart.getContentType().toLowerCase().startsWith("text/html")) {
                htmlContent.append(bodyPart.getContent().toString());
            }
        }

        bo.setBodyText(textContent.toString());
        bo.setBodyHtml(htmlContent.toString());
        bo.setHasAttachments(!attachments.isEmpty() ? 1L : 0L);
        bo.setAttachments(String.join(",", attachments));        bo.setAttachments(String.join(",", attachments));
    }


    /**
     * 解码邮件地址
     */
    private String decodeEmailAddress(String address) {
        try {
            // 处理完整的邮件地址格式 "name <email@domain.com>"
            if (address.contains("<")) {
                String[] parts = address.split("<", 2);
                String name = MimeUtility.decodeText(parts[0].trim());
                String email = parts[1].substring(0, parts[1].length() - 1).trim();
                return name + " <" + email + ">";
            }
            // 处理纯邮件地址
            return MimeUtility.decodeText(address);
        } catch (Exception e) {
            log.warn("解码邮件地址失败: {}", address, e);
            return address;
        }
    }
}

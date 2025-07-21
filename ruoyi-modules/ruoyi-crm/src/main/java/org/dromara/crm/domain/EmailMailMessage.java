package org.dromara.crm.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 邮件，支持多租户（模块：email）对象 email_mail_message
 *
 * @author Lion Li
 * @date 2025-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("email_mail_message")
public class EmailMailMessage extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 邮件唯一ID（Message‑ID）
     */
    private String mailId;

    /**
     * 邮件所属文件夹，如 inbox、sent 等
     */
    private String folder;

    /**
     * 发件人邮箱地址
     */
    private String fromAddress;

    /**
     * 收件人邮箱地址，多个用逗号分隔
     */
    private String toAddresses;

    /**
     * 抄送人邮箱地址，多个用逗号分隔
     */
    private String ccAddresses;

    /**
     * 密送人邮箱地址，多个用逗号分隔
     */
    private String bccAddresses;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件正文纯文本内容
     */
    private String bodyText;

    /**
     * 邮件正文 HTML 内容
     */
    private String bodyHtml;

    /**
     * 发送时间
     */
    private Date sentDate;

    /**
     * 接收时间
     */
    private Date receivedDate;

    /**
     * 是否已读，0‑未读，1‑已读
     */
    private Long isRead;

    /**
     * 是否包含附件
     */
    private Long hasAttachments;

    /**
     * 附件信息 JSON（文件名、大小、类型等）
     */
    private String attachments;

    /**
     * 邮件标记，如星标、重要等
     */
    private String flags;


}

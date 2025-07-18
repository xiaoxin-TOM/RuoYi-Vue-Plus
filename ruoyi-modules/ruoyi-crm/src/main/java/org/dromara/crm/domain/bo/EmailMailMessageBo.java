package org.dromara.crm.domain.bo;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;

import org.dromara.crm.domain.EmailMailMessage;

/**
 * 邮件，支持多租户（模块：email）业务对象 email_mail_message
 *
 * @author Lion Li
 * @date 2025-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = EmailMailMessage.class, reverseConvertGenerate = false)
public class EmailMailMessageBo extends BaseEntity {

    /**
     * 主键ID
     */
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
    @NotBlank(message = "抄送人邮箱地址，多个用逗号分隔不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ccAddresses;

    /**
     * 密送人邮箱地址，多个用逗号分隔
     */
    @NotBlank(message = "密送人邮箱地址，多个用逗号分隔不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bccAddresses;

    /**
     * 邮件主题
     */
    @NotBlank(message = "邮件主题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subject;

    /**
     * 邮件正文纯文本内容
     */
    @NotBlank(message = "邮件正文纯文本内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bodyText;

    /**
     * 邮件正文 HTML 内容
     */
    @NotBlank(message = "邮件正文 HTML 内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bodyHtml;

    /**
     * 发送时间
     */
    @NotNull(message = "发送时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date sentDate;

    /**
     * 接收时间
     */
    @NotNull(message = "接收时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date receivedDate;

    /**
     * 是否已读，0‑未读，1‑已读
     */
    @NotNull(message = "是否已读，0‑未读，1‑已读不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long isRead;

    /**
     * 是否包含附件
     */
    @NotNull(message = "是否包含附件不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long hasAttachments;

    /**
     * 附件信息 JSON（文件名、大小、类型等）
     */
    @NotBlank(message = "附件信息 JSON（文件名、大小、类型等）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attachments;

    /**
     * 邮件标记，如星标、重要等
     */
    @NotBlank(message = "邮件标记，如星标、重要等不能为空", groups = { AddGroup.class, EditGroup.class })
    private String flags;

    /**
     * 记录创建时间
     */
    @NotNull(message = "记录创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createdAt;

    /**
     * 记录更新时间
     */
    @NotNull(message = "记录更新时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date updatedAt;


}

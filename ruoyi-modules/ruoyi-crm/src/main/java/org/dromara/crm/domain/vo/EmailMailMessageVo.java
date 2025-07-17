package org.dromara.crm.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.EmailMailMessage;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 邮件，支持多租户（模块：email）视图对象 email_mail_message
 *
 * @author Lion Li
 * @date 2025-07-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = EmailMailMessage.class)
public class EmailMailMessageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 邮件唯一ID（Message‑ID）
     */
    @ExcelProperty(value = "邮件唯一ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "M=essage‑ID")
    private String mailId;

    /**
     * 邮件所属文件夹，如 inbox、sent 等
     */
    @ExcelProperty(value = "邮件所属文件夹，如 inbox、sent 等")
    private String folder;

    /**
     * 发件人邮箱地址
     */
    @ExcelProperty(value = "发件人邮箱地址")
    private String fromAddress;

    /**
     * 收件人邮箱地址，多个用逗号分隔
     */
    @ExcelProperty(value = "收件人邮箱地址，多个用逗号分隔")
    private String toAddresses;

    /**
     * 抄送人邮箱地址，多个用逗号分隔
     */
    @ExcelProperty(value = "抄送人邮箱地址，多个用逗号分隔")
    private String ccAddresses;

    /**
     * 密送人邮箱地址，多个用逗号分隔
     */
    @ExcelProperty(value = "密送人邮箱地址，多个用逗号分隔")
    private String bccAddresses;

    /**
     * 邮件主题
     */
    @ExcelProperty(value = "邮件主题")
    private String subject;

    /**
     * 邮件正文纯文本内容
     */
    @ExcelProperty(value = "邮件正文纯文本内容")
    private String bodyText;

    /**
     * 邮件正文 HTML 内容
     */
    @ExcelProperty(value = "邮件正文 HTML 内容")
    private String bodyHtml;

    /**
     * 发送时间
     */
    @ExcelProperty(value = "发送时间")
    private Date sentDate;

    /**
     * 接收时间
     */
    @ExcelProperty(value = "接收时间")
    private Date receivedDate;

    /**
     * 是否已读，0‑未读，1‑已读
     */
    @ExcelProperty(value = "是否已读，0‑未读，1‑已读")
    private Long isRead;

    /**
     * 是否包含附件
     */
    @ExcelProperty(value = "是否包含附件")
    private Long hasAttachments;

    /**
     * 附件信息 JSON（文件名、大小、类型等）
     */
    @ExcelProperty(value = "附件信息 JSON", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "文=件名、大小、类型等")
    private String attachments;

    /**
     * 邮件标记，如星标、重要等
     */
    @ExcelProperty(value = "邮件标记，如星标、重要等")
    private String flags;

    /**
     * 记录创建时间
     */
    @ExcelProperty(value = "记录创建时间")
    private Date createdAt;

    /**
     * 记录更新时间
     */
    @ExcelProperty(value = "记录更新时间")
    private Date updatedAt;


}

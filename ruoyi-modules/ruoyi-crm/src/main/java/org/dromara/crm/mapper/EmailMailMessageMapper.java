package org.dromara.crm.mapper;

import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.crm.domain.EmailMailMessage;
import org.dromara.crm.domain.vo.EmailMailMessageVo;

/**
 * 邮件，支持多租户（模块：email）Mapper接口
 *
 * @author Lion Li
 * @date 2025-07-17
 */
public interface EmailMailMessageMapper extends BaseMapperPlus<EmailMailMessage, EmailMailMessageVo> {

}

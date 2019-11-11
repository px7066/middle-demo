package com.github.remote.api.dubbo.producer;

import com.github.remote.model.dubbo.producer.DubboSerializableTestDto;
import com.github.remote.model.dubbo.producer.UserRmoteDto;

/**
 * <p>dubbo远程调用用户demo样例</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public interface IUserRemoteService {
    /**
     * 查询样例
     * @param id 用户id
     * @return UserRmoteDto
     */
    UserRmoteDto queryUserById(Integer id);

    /**
     * 序列化测试
     * @return DubboSerializableTestDto
     */
    DubboSerializableTestDto serializatableTestMethod();




}

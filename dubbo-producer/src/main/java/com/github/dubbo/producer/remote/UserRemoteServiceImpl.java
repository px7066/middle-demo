package com.github.dubbo.producer.remote;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.dubbo.producer.model.UserVo;
import com.github.dubbo.producer.service.IUserService;
import com.github.remote.api.dubbo.producer.IUserRemoteService;
import com.github.remote.model.dubbo.producer.DubboSerializableTestDto;
import com.github.remote.model.dubbo.producer.UserRmoteDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>远程服务实现</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@Service
@Component
public class UserRemoteServiceImpl implements IUserRemoteService {
    @Autowired
    private IUserService userService;

    /**
     * 查询样例
     *
     * @param id 用户id
     * @return UserRmoteDto
     */
    @Override
    public UserRmoteDto queryUserById(Integer id) {
        if(id != null){
            UserRmoteDto userRmoteDto = new UserRmoteDto();
            UserVo vo = userService.queryUserById(id);
            BeanUtils.copyProperties(vo, userRmoteDto);
            return userRmoteDto;
        }
        return null;
    }

    /**
     * 序列化测试
     *
     * @return DubboSerializableTestDto
     */
    @Override
    public DubboSerializableTestDto serializatableTestMethod() {
        DubboSerializableTestDto dubboSerializableTestDto = new DubboSerializableTestDto();
        dubboSerializableTestDto.setName("序列化测试");
        dubboSerializableTestDto.setAge(10);
        return dubboSerializableTestDto;
    }
}

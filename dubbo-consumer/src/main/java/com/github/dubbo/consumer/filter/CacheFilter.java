package com.github.dubbo.consumer.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * <p></p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Activate(group = Constants.PROVIDER)
public class CacheFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result;
        try{
            System.out.println("before CacheFilter");
            result = invoker.invoke(invocation);
            if (result.getException() instanceof Exception)
            {
                throw new Exception(result.getException());
            }
            System.out.println(result);
            System.out.println("after CacheFilter");
        }
        catch(Exception e){
            e.printStackTrace();
            result = new RpcResult(e);
            return result;
        }

        return result;
    }
}

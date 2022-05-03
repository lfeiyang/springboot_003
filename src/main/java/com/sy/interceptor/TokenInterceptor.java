package com.sy.interceptor;

import com.sy.util.RedisCatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token拦截器
 *
 * @author lfeiyang
 * @since 2022-05-03 23:38
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisCatchUtil redisCatchUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenName = request.getRequestURI() + request.getParameter("token_value");
        String tokenValue = request.getParameter("token_value");
        if (tokenValue != null && !tokenValue.equals("")) {
            log.info("tokenName:{},tokenValue:{}", tokenName, tokenValue);
            return handleToken(request, response, handler);
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (redisCatchUtil.hasKey(request.getParameter("token_value"))) {
            redisCatchUtil.delDistributedKey(request.getParameter("token_value"), request.getParameter("token_value"));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    /**
     * 分布式锁处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     * @return boolean
     **/
    private boolean handleToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (redisCatchUtil.hasKey(request.getRequestURI() + request.getParameter("token_value"))) {
            //当大量高并发下所有带token参数的请求进来时，进行分布式锁定,允许某一台服务器的一个线程进入，锁定时间3分钟
            if (redisCatchUtil.addDistributedKey(request.getParameter("token_value"), request.getParameter("token_value"), 180)) {
                //当请求的url与token与redis中的存储相同时
                if (redisCatchUtil.get(request.getRequestURI() + request.getParameter("token_value")).equals(request.getParameter("token_value"))) {
                    //放行的该线程删除redis中存储的token
                    redisCatchUtil.del(request.getRequestURI() + request.getParameter("token_value"));
                    //放行
                    return true;
                }
            }

            //当请求的url与token与redis中的存储不相同时，解除锁定
            redisCatchUtil.delDistributedKey(request.getParameter("token_value"), request.getParameter("token_value"));

            return false;
        }

        return false;
    }
}

package com.lagou.edu.filter;

import com.lagou.edu.service.UserServiceFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@RefreshScope
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger LOGGER= LoggerFactory.getLogger(AuthFilter.class);
//    @Qualifier("com.lagou.edu.service.UserServiceFeignClient")
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        RequestPath path = request.getPath();
        // LOGGER.info("request path : "+path);
        PathContainer pathContainer = path.subPath(5, 6);
        RestOperations restTemplate=new RestTemplate();
        //登录和注册时检查用户名和密码格式,不检查cookies
        if(pathContainer.toString().equals("login") || pathContainer.toString().equals("register")){
            LOGGER.info("login format check username : "+exchange.getRequest().getPath().subPath(7,8));
            LOGGER.info("login format check password : "+exchange.getRequest().getPath().subPath(9,10));
            return chain.filter(exchange);
        }
        if(pathContainer.toString().equals("create")){
            return chain.filter(exchange);
        }
        //其他服务检查是否有token
        else {
            HttpCookie token = request.getCookies().getFirst("token");
            if(token==null || token.getValue().isEmpty()){
                //没有token,重定向到注册页面
                String redirectUrl = "http://www.test.com/register.html";
//                response = exchange.getResponse();
//                response.setStatusCode(HttpStatus.SEE_OTHER);
//                response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
//                return exchange.getResponse().setComplete();
                //"完整的URL地址，比如： http://192.168.22.140:8002/index.html";

                LOGGER.info("bmg 重定向到URL: {}", redirectUrl);
                response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
                //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.setComplete();
            }
            else{

                String email = userServiceFeignClient.info(token.getValue());
                if(!email.isEmpty()){

                   Boolean isRegister = userServiceFeignClient.isRegister(email);
                    if(isRegister){
                        return chain.filter(exchange);
                    }
                }
            }
        }
        String redirectUrl = "http://www.test.com/login.html";
        response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
        //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
        response.setStatusCode(HttpStatus.SEE_OTHER);

        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.setComplete();

    }

    @Override
    public int getOrder() {
        return 1;
    }
}

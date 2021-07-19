package com.lagou.edu.filter;

import com.lagou.edu.utils.WindowLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class LimitedIPFilter implements GlobalFilter, Ordered {
    private Map<String, WindowLimiter> IpCountMap = new HashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String hostString = request.getRemoteAddress().getHostString();
        Boolean outOfLimit = false;
        try {
            if (IpCountMap.containsKey(hostString)) {
                WindowLimiter windowLimiter = IpCountMap.get(hostString);
                outOfLimit = windowLimiter.reuestCountAdd();
            } else {
                WindowLimiter windowLimiter1 = new WindowLimiter();
                WindowLimiter windowLimiter = IpCountMap.put(hostString,windowLimiter1);
                outOfLimit = windowLimiter1.reuestCountAdd();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!outOfLimit) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.debug("====> IP : " + hostString + " has request out of limit!");
            String data = "Request be denied";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

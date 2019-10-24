package com.github.eureka.consumer.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>控制器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    @GetMapping("callHello")
    public String callHello(){
        return restTemplate.getForObject("http://producer/producer/hello", String.class);
    }


    @GetMapping("metadata")
    public Object metadata(){
        return discoveryClient.getInstances("producer");
    }

    @GetMapping("choose")
    public Object chooseUrl(){
        ServiceInstance instance = loadBalancerClient.choose("producer");
        return instance;
    }

    @GetMapping("infos")
    public Object serviceUrl(){
        return eurekaClient.getInstancesByVipAddress("producer", false);
    }
}

package com.lrm.commentms.clients;

import com.lrm.commentms.external.Blog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "BLOG-SERVICE", url = "${blog-service.url}")
public interface BlogClient {
    @GetMapping("/blog/{id}")
    Blog getBlog(@PathVariable("id") Long id);
}

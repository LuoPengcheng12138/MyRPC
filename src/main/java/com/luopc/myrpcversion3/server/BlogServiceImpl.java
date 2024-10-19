package com.luopc.myrpcversion3.server;

import com.luopc.myrpcversion2.common.Blog;
import com.luopc.myrpcversion2.service.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        System.out.println("Client query "+id+" 's blog");
        Blog blog = Blog.builder().id(id).userId(1706).title("My Blog").build();
        return blog;
    }
}
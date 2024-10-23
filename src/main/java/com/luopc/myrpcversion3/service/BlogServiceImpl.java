package com.luopc.myrpcversion3.service;

import com.luopc.myrpcversion3.common.Blog;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        System.out.println("Client query "+id+" 's blog");
        Blog blog = Blog.builder().id(id).userId(1706).title("My Blog").build();
        return blog;
    }
}
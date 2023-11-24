package com.pro05.config;

import com.pro05.service.*;
import com.pro05.util.AdminInterceptor;
import com.pro05.util.UserInterceptor;
import com.pro05.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Log4j2
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    // 리소스를 지정하는 세번째 방법


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/upload/");
        registry.addResourceHandler("/static/**").addResourceLocations("");

    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public MemberService memberService(){return new MemberServiceImpl();}

    @Bean
    public ProductService productService() {return new ProductServiceImpl();}

    @Bean
    public NoticeSerivce noticeSerivce() {return new NoticeServiceImpl();}

    @Bean
    public RecommentService recommentService() {return new RecommentServiceImpl();}

    @Bean
    public WishService wishService() {return new WishServiceImpl();}

    @Bean
    public KeywordService keywordService() {
        return new KeywordServiceImpl();
    }

    @Bean
    public AdminInterceptor adminInterceptor() {return new AdminInterceptor();}

    @Bean
    public UserInterceptor userInterceptor() {return new UserInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor());
        registry.addInterceptor(userInterceptor());
    }

}

package com.example.demo.config;

import com.example.demo.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class GlobalSimpleConfig implements WebMvcConfigurer {
    /**
     * 开启跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
//                 设置允许的方法
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                // 是否允许携带cookie参数
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(4600);
    }


    private TokenInterceptor tokenInterceptor;
    //构造方法
    public GlobalSimpleConfig(TokenInterceptor tokenInterceptor){
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //排除拦截，除了注册登录(此时还没token)，其他都拦截
//        excludePath.add("");  //登录
        excludePath.add("/login");  //登录
        excludePath.add("/register");     //注册
        excludePath.add("/forgotPwd");     //忘记密码
        excludePath.add("/get/coins");     //行情页面获取展示数据
//        excludePath.add("/**");
//        excludePath.add("/img/**");  //静态资源
//        excludePath.add("/song/**");  //静态资源
        excludePath.add("/addWallet");
        excludePath.add("/getSingleNum");
        excludePath.add("/getAllNum");
        excludePath.add("/updateSingleNum");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}

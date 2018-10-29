package com.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

@Configuration
// @EnableConfigurationProperties(ConfigBean.class)
public class MvcConfig implements WebMvcConfigurer
{
    // @Autowired
    // private ConfigBean configBean;
    
    @Bean
    public HttpMessageConverter<?> fastJsonHttpMessageConverter4()
    {
        FastJsonHttpMessageConverter4 fastJsonConverter = new FastJsonHttpMessageConverter4();
        fastJsonConverter.getFastJsonConfig().setSerializerFeatures(SerializerFeature.PrettyFormat);
        return fastJsonConverter;
    }
    
    // @Bean
    // public HttpMessageConverters fastJsonHttpMessageConverter4()
    // {
    // FastJsonHttpMessageConverter4 fastJsonConverter = new
    // FastJsonHttpMessageConverter4();
    // fastJsonConverter.getFastJsonConfig().setSerializerFeatures(SerializerFeature.PrettyFormat);
    // return new HttpMessageConverters(fastJsonConverter);
    // }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        // registry.addResourceHandler("/upload/*").addResourceLocations(configBean.getUploadDir());
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        // converters.add(fastJsonHttpMessageConverter4());
    }
    
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(fastJsonHttpMessageConverter4());
    }
    
}

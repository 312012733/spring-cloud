package com.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// @EnableConfigurationProperties(ConfigBean.class)
public class MvcConfig implements WebMvcConfigurer
{
    // @Autowired
    // private ConfigBean configBean;
    
//    @Bean
//    public HttpMessageConverter<?> fastJsonHttpMessageConverter4()
//    {
//        FastJsonHttpMessageConverter4 fastJsonConverter = new FastJsonHttpMessageConverter4();
//        fastJsonConverter.getFastJsonConfig().setSerializerFeatures(SerializerFeature.PrettyFormat);
//        return fastJsonConverter;
//    }
    
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
//        converters.add(fastJsonHttpMessageConverter4());
    }
    
}

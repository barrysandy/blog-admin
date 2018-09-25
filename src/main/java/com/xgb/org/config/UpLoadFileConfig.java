package com.xgb.org.config;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
@SuppressWarnings({ "deprecation", "unused" })
public class UpLoadFileConfig extends WebMvcConfigurerAdapter {

    @Value("${web.imagesPath}")
    private String mImagesPath;
    
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
      //指向外部目录
      registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
      super.addResourceHandlers(registry);
  }
}
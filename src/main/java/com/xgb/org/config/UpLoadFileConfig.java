package com.xgb.org.config;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UpLoadFileConfig extends WebMvcConfigurerAdapter {

    @Value("${web.imagesPath}")
    private String mImagesPath;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(mImagesPath.equals("") || mImagesPath.equals("${web.imagesPath}")){
               String imagesPath = UpLoadFileConfig.class.getClassLoader().getResource("").getPath();
               if(imagesPath.indexOf(".jar")>0){
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
               }else if(imagesPath.indexOf("classes")>0){
                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
               }
               imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
               mImagesPath = imagesPath;
              }
              LoggerFactory.getLogger(UpLoadFileConfig.class).info("imagesPath="+mImagesPath);
              registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
        super.addResourceHandlers(registry);
    }
}
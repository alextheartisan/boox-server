package com.github.alextheartisan.boox.web.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class WebAppInitialization implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext context) throws ServletException {
        //        var servlet = context.addServlet(
        //            "mvc",
        //            new DispatcherServlet(new GenericWebApplicationContext())
        //        );
        //
        //        servlet.setLoadOnStartup(1);
        //
        //        var TMP_FOLDER = "/tmp";
        //        var MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
        //
        //        var element = new MultipartConfigElement(
        //            TMP_FOLDER,
        //            MAX_UPLOAD_SIZE,
        //            MAX_UPLOAD_SIZE * 2,
        //            MAX_UPLOAD_SIZE / 2
        //        );
        //
        //        servlet.setMultipartConfig(element);
    }
}

package com.github.alextheartisan.boox.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class FileUploadConfiguration {
    //    @Bean
    //    public StandardServletMultipartResolver multipartResolver() {
    //        return new StandardServletMultipartResolver();
    //    }

    //  @Override
    //  public void onStartup(ServletContext context) throws ServletException {
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
    //  }
}

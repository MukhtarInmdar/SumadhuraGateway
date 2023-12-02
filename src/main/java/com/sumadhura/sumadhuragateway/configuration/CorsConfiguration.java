/**
 * 
 *//*
package com.sumadhura.sumadhuragateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

*//**
 * CorsConfiguration class provides solution for cors origin problem.
 * 
 * @author Venkat_Koniki
 * @since 10.07.2019
 * @time 11:44AM
 *//*
@Configuration
@EnableWebMvc
public class CorsConfiguration extends WebMvcConfigurerAdapter
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST");
    }
}
*/
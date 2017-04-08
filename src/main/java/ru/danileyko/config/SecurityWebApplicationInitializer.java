package ru.danileyko.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

/**
 * Created by danil on 18.02.2017.
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {}


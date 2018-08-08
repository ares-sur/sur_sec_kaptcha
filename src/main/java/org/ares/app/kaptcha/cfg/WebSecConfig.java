package org.ares.app.kaptcha.cfg;

import static org.ares.app.common.cfg.param.GlobalConfig.URL_ALLOW_CSS;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_ALLOW_FONTS;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_ALLOW_INDEX;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_ALLOW_JS;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_LOGIN;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_LOGIN_ERROR;
import static org.ares.app.common.cfg.param.GlobalConfig.URL_LOGIN_SUCCESS;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ares.app.common.security.spring.KaptchaAuthenticationFilter;
import org.ares.app.common.security.spring.UserDetailsServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class WebSecConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();// 使用 BCrypt 加密
    }
    
    @Bean
    UserDetailsService ucJdbcUserService(){
        return new UserDetailsServiceBean();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
        return authenticationProvider;
    }
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    /**
     * 自定义配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new KaptchaAuthenticationFilter(URL_LOGIN, URL_LOGIN_ERROR), UsernamePasswordAuthenticationFilter.class) //在认证用户名之前认证验证码，如果验证码错误，将不执行用户名和密码的认证
                .authorizeRequests().antMatchers(URL_ALLOW_CSS, URL_ALLOW_JS, URL_ALLOW_FONTS, URL_ALLOW_INDEX,URL_LOGIN).permitAll()
                //.antMatchers("/h2-console/**").permitAll() // 都可以访问
                .antMatchers("/admin/**").hasRole("ADMIN") // 需要相应的角色才能访问
                .and()
                .formLogin()   //基于 Form 表单登录验证
                .loginPage(URL_LOGIN)
                .successHandler(new AuthenticationSuccessHandler() {//用户名和密码正确执行
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
                        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        if (principal != null && principal instanceof UserDetails) {
                            httpServletResponse.setContentType(json_content_type);
                            PrintWriter out = httpServletResponse.getWriter();
                            out.write("{\"status\":\"ok\",\"message\":\"登录成功\"}");
                            out.flush();
                            out.close();
                        }*/
                    	request.getRequestDispatcher(URL_LOGIN_SUCCESS).forward(request, response);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {//用户名密码错误执行
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                    	log.info(e.getMessage()+"****************************");
                        httpServletResponse.setContentType(json_content_type);
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\":\"error\",\"message\":\"用户名或密码错误\"}");
                        out.flush();
                        out.close();
                    }
                })
                .and().rememberMe().key(KEY) // 启用 remember me
                .and().exceptionHandling().accessDeniedPage("/403");  // 处理异常，拒绝访问就重定向到 403 页面
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        http.csrf().ignoringAntMatchers("/h2-console/**"); // 禁用 H2 控制台的 CSRF 防护
        http.csrf().ignoringAntMatchers("/ajax/**"); // 禁用 H2 控制台的 CSRF 防护
        http.csrf().ignoringAntMatchers("/upload");
        http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的H2 控制台的请求
    }
    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        auth.authenticationProvider(authenticationProvider());
    }
    
    final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${webapp.content_type.json}") String json_content_type;
    static final String KEY = "inctech.cn";
    @Autowired UserDetailsService userService;//实现了UserDetailsService类
    @Autowired PasswordEncoder passwordEncoder;
}

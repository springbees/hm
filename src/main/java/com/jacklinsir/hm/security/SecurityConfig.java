package com.jacklinsir.hm.security;

import com.jacklinsir.hm.security.authentication.MyAuthenctiationFailureHandler;
import com.jacklinsir.hm.security.authentication.MyAuthenticationSuccessHandler;
import com.jacklinsir.hm.security.authentication.MyLogoutSuccessHandler;
import com.jacklinsir.hm.security.authentication.RestAuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author linSir
 * @version V1.0
 * @Description: (SecurityConfig配置类)
 * @Date 2020/1/6 19:12
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 指定身份认真服务
     * 密码加密方式
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 方法定义了哪些URL路径应该被保护
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //过滤不拦截请求
                .antMatchers("/login.html")
                //允许全部
                .permitAll()
                .anyRequest()
                //已经认证
                .authenticated()
        ;
        //禁用CSRF保护
        http.csrf().disable();
        //解决X-Frame-Options DENY问题
        http.headers().frameOptions().sameOrigin();
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and().logout().permitAll().invalidateHttpSession(true).
                deleteCookies("JSESSIONID").logoutSuccessHandler(myLogoutSuccessHandler)
        ;
        //异常处理
        http.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);

    }

    /**
     * 配置静态资源不被拦截
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/my/**",
                "/treetable-lay/**",
                "/xadmin/**",
                "/ztree/**",
                "/statics/**");
    }
}

package cn.mauth.ccrm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private RESTAuthenticationFailureHandler failureHandler;

    @Autowired
    private RESTAuthenticationSuccessHandler successHandler;

    @Autowired
    private RESTLogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] mvcPatterns={
                "/**/*.css",
                "/**/*.js",
                "/**/*.gif",
                "/**/*.jpg",
                "/**/*.jpg",
                "/**/*.ico",
                "/**/*.png",
                "/**/*.ttf",
                "/**/*.woff",
                "/**/*.eot",
                "/**/*.svg",
                "/login",
                "/saleActivityWechat/**",
                "/login.ftl",
                "/index.ftl",
                "/index"
        };
        web.ignoring().mvcMatchers(mvcPatterns);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler);
    }


}

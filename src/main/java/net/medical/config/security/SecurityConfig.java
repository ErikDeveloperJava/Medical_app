package net.medical.config.security;

import net.medical.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("USD")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/register")
                .anonymous()
                .antMatchers("/","/error","/doctors")
                .permitAll()
                .antMatchers("/doctor/users",
                        "/doctor/working-days/update","/doctor/users/**",
                        "/post/add","/doctor/data/update","/doctor/post/**")
                .hasAuthority(UserRole.DOCTOR.name())
                .antMatchers("/user/request","/user/request/**")
                .hasAuthority(UserRole.USER.name())
                .antMatchers("/department/*","/departments","/doctor/*","/posts")
                .hasAnyAuthority(UserRole.DOCTOR.name(),UserRole.USER.name(),UserRole.ROLE_ANONYMOUS.name())
                .antMatchers("/post/comment/add")
                .hasAnyAuthority(UserRole.DOCTOR.name(),UserRole.USER.name())
                .antMatchers("/admin","/admin/**")
                .hasAuthority(UserRole.ADMIN.name())
        .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
        .and()
                .rememberMe()
                .rememberMeCookieName("RM")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(2000000)
                .userDetailsService(userDetailsService)
        .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("RM")
                .logoutSuccessUrl("/")
        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

        .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccsessDinededHandlerImpl());
    }
}

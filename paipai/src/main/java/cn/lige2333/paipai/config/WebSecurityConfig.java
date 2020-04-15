package cn.lige2333.paipai.config;


import cn.lige2333.paipai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.sessionManagement()
                .maximumSessions(1).maxSessionsPreventsLogin(true);
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
        http.rememberMe().rememberMeParameter("remember");
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/code/**").permitAll()
                .antMatchers("/userReg").permitAll()
                .antMatchers("/list").permitAll()
                .antMatchers("/findProductByKeyword").permitAll()
                .antMatchers("/queryProduct").permitAll()
                .antMatchers("/shop/liebiao").permitAll()
                .antMatchers("/biddingForProd").hasRole("VIP3")
                .antMatchers("/chat").hasRole("VIP3")
                .antMatchers("/addbid").hasRole("VIP3")
                .antMatchers("/shop/info").hasRole("VIP3")
                .antMatchers("/openWork/**").hasRole("VIP3")
                .antMatchers("/innerWork/**").hasRole("VIP3")
                .antMatchers("/demo/**").hasRole("VIP3")
                .antMatchers("/selfInfo/**").hasRole("VIP3")
                .antMatchers("/uploadFile").hasRole("VIP3")
                .antMatchers("/updateAddress").hasRole("VIP3")
                .antMatchers("/updateUser/**").hasRole("VIP3");

    }
}

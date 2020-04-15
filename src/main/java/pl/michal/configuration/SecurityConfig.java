package pl.michal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.michal.service.impl.UserDetailsServiceImpl;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable(); // cross sign request foreign
        http.headers().disable();
        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/addMedicineDialog").hasAuthority("ROLE_ADMIN")
                .antMatchers("/changeMedicineInfo").hasAuthority("ROLE_ADMIN")
                .antMatchers("/sellMedicineDialog").hasAuthority("ROLE_ADMIN")
                .antMatchers("/showInfo").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                //.antMatchers("/").permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/");
    }
}

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
                .antMatchers("/admin/*").hasAuthority("ROLE_ADMIN")
                .antMatchers("/changeMedicineInfo").hasAnyAuthority( "ROLE_ADMIN", "ROLE_SALESMAN")
                .antMatchers("/addMedicineDialog").hasAnyAuthority("ROLE_SALESMAN", "ROLE_ADMIN")
                .antMatchers("/sellMedicineDialog").hasAnyAuthority("ROLE_SALESMAN", "ROLE_ADMIN")
                .antMatchers("/showInfo").permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/");

    }
}

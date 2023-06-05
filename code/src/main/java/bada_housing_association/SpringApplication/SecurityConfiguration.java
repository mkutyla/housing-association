package bada_housing_association.SpringApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import
        org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(getPasswordEncoder().encode("user"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin").password(getPasswordEncoder().encode("admin"))
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/resources/static/**").permitAll()
                .antMatchers("/main").authenticated()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/user/**").access("hasRole('USER')")

                .antMatchers("/spoldzielnia/delete/**").access("hasRole('ADMIN')")
                .antMatchers("/spoldzielnia/edit/**").access("hasRole('ADMIN')")
                .antMatchers("/spoldzielnia/add").access("hasRole('ADMIN')")
                .antMatchers("/spoldzielnia/**").authenticated()

                .antMatchers("/adres/delete/**").access("hasRole('ADMIN')")
                .antMatchers("/adres/edit/**").access("hasRole('ADMIN')")
                .antMatchers("/adres/**").authenticated()

                .antMatchers("/pracownik/delete/**").access("hasRole('ADMIN')")
                .antMatchers("/pracownik/**").authenticated()

                .antMatchers("/stanowisko/delete/**").access("hasRole('ADMIN')")
                .antMatchers("/stanowisko/add").access("hasRole('ADMIN')")
                .antMatchers("/stanowisko/**").authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/main")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/index")
                .logoutSuccessUrl("/index")
                .permitAll();
    }
}

package ticketing_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationSuccessHandler successHandler;
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                                .requestMatchers("/swagger-ui/index.html/api/v1/sign")
                                .permitAll()
                .anyRequest().authenticated()
                                //.anyRequest()
                                //.authenticated()
                .and()
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(anonymousAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .formLogin(formLogin ->
                        formLogin
                                //  .loginPage("/swagger-ui/index.html") // Specify a custom login page if needed
                                .successHandler(successHandler)
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // Specify the URL for logout
                                .logoutSuccessUrl("/login?logout")// Redirect to login page after logout
                                .deleteCookies("JSESSIONID") // Delete cookies, if needed
                                .invalidateHttpSession(true) // Invalidating the HTTP session

                );



        return http
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
        return new AnonymousAuthenticationFilter("anonymousUser", "ROLE_ANONYMOUS", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }
}

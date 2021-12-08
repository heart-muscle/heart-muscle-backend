package shop.heartmuscle.heartmuscle.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.heartmuscle.heartmuscle.controller.JwtAuthenticationEntryPoint;
import shop.heartmuscle.heartmuscle.controller.JwtAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAuthenticationFilter jwtRequestFilter;

        @Bean
        public BCryptPasswordEncoder encodePassword() {
            return new BCryptPasswordEncoder();
}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // html 열어주기
                .antMatchers("/*.html").permitAll()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                .antMatchers("/feed/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/basic.js").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/login/kakao").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/checkSignup").permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET, "/feed/**").permitAll()
                .antMatchers(HttpMethod.GET, "/qna/**").permitAll()
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

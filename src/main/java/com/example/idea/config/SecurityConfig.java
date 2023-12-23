package com.example.idea.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.example.idea.config.jwt.JwtAccessDeniedHandler;
import com.example.idea.config.jwt.JwtAuthenticationEntryPoint;
import com.example.idea.config.jwt.JwtFilter;
import com.example.idea.config.jwt.TokenProvider;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
//@EnableGlobalAuthentication(prePostEnable = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    @Qualifier("bcryptPasswordEncoder")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                .requestMatchers("/api/authenticate").permitAll() // 로그인 api
                .requestMatchers("/api/signup").permitAll() // 회원가입 api
                .requestMatchers(PathRequest.toH2Console()).permitAll()// h2-console, favicon.ico 요청 인증 무시
                .requestMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated() // 그 외 인증 없이 접근X

                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

        return httpSecurity.build();
    }
*/
    // 커스텀 시도중...

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(csrf -> csrf
////                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//                                .disable() // CSRF 비활성화
//                )

//        httpSecurity.csrf(AbstractHttpConfigurer::disable)// CSRF 비활성화
//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling
//                                .accessDeniedHandler(jwtAccessDeniedHandler)
//                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                )
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/h2-console/**") // H2 콘솔에 대한 CSRF 비활성화
//                )
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .headers(headers -> headers
                        .frameOptions(FrameOptionsConfig::disable) // H2 콘솔 프레임 관련 보안 비활성화
                )
                // 세션을 사용하지 않기 때문에 SessionCreationPolicy.STATELESS로 설정함
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .cors(Customizer.withDefaults()) // 기본 CORS 설정 사용
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 커스텀 CORS 설정 적용
                .httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**", "/favicon.ico").permitAll() // H2 콘솔, favicon 접근 허용
                        .requestMatchers("/favicon.ico").permitAll() // favicon 접근 허용
                        .requestMatchers(PathRequest.toH2Console()).permitAll()// favicon.ico 요청 인증 무시
                        .requestMatchers("/api/users/join", "/api/users/check-id", "/api/users/login",
                                "/api/users/find-id", "/api/users/find-pwd", "/api/users/reset-pwd",
                                "/api/users/kakao-login",
                                "/api/user/findPwd").permitAll() // 접근 허용
                        .requestMatchers("/api/users/my-info", "/api/users/add-image", "/api/users/delete",
                                "/api/users/logout", "/adm/test", "/user/test",
                                "/api/ide/**", "/api/chat/**", "/chat/**").authenticated() // 로그인했을 때 접근 허용
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 콘솔접근 허용
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class) // JwtFilter 추가
                // 'apply(C)' is deprecated since version 6.2 and marked for removal
//                .apply(new JwtSecurityConfig(tokenProvider)) // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용
                .formLogin(
                        form -> form.disable()
                )
//                .logout(logout -> logout
//                        .logoutUrl("/user/logout") // 로그아웃 URL 설정
//                        .permitAll()
//                );
        ;
        return httpSecurity.build();
    }

    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf
////                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//                                .disable() // CSRF 비활성화
//                )
        http.csrf(AbstractHttpConfigurer::disable)// CSRF 비활성화
                .exceptionHandling(exceptionHandling ->
                    exceptionHandling
                            .accessDeniedHandler(jwtAccessDeniedHandler)
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 접근 허용
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // H2 콘솔에 대한 CSRF 비활성화
                )
                .headers(headers -> headers
                        .frameOptions(FrameOptionsConfig::disable) // H2 콘솔 프레임 관련 보안 비활성화
                )

//                .and() // 'and()' is deprecated since version 6.1 and marked for removal
//                .headers()
//                .frameOptions()
//                .sameOrigin()

//                .equals((headers) -> headers
//                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                                XFrameOptionsMode.SAMEORIGIN))
//                ) // h2 console
                // 세션을 사용하지 않기 때문에 SessionCreationPolicy.STATELESS로 설정함
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .authorizeHttpRequests((authorizeRequests) ->
//                        authorizeRequests.anyRequest().permitAll()
//                )

//                .cors(Customizer.withDefaults()) // 기본 CORS 설정 사용
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 커스텀 CORS 설정 적용
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/user/findId", "/user/findPwd").permitAll() // 접근 허용
                        .requestMatchers("/user/myInfo", "/user/addImage",
                                "/ide/**", "/chat/sendMsg", "/chat/chatFile").authenticated() // 로그인했을 때 접근 허용
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 콘솔, favicon.ico 요청 인증 무시
                        .requestMatchers("/favicon.ico").permitAll()
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                .formLogin(
                        form -> form.disable()
                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/user/login")
//                        .usernameParameter("userId")
//                        .passwordParameter("pwd")
//                        .loginProcessingUrl("/loginProc")
//                        .successHandler(new CustomAuthenticationSuccessHandler())
//                        .failureHandler(new CustomAuthenticationFailureHandler())
//                        .permitAll()
//                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout") // 로그아웃 URL 설정
                        .permitAll()
                );
        return http.build();
    }
    */

    // CORS 설정을 위한 Bean 정의
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // 실제 환경에서는 구체적으로 지정
//        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

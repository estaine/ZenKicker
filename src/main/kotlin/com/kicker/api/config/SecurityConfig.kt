package com.kicker.api.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kicker.api.domain.exception.ExceptionResponse
import com.kicker.api.service.PlayerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Yauheni Efimenko
 */
@Configuration
@EnableGlobalMethodSecurity
class SecurityConfig : GlobalMethodSecurityConfiguration() {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    @Configuration
    class SecurityConfigurer(
            private val userDetailsService: PlayerService,
            private val passwordEncoder: PasswordEncoder
    ) : WebSecurityConfigurerAdapter() {

        override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
        }

        override fun configure(http: HttpSecurity) {
            http.csrf().disable()
            http.cors()

            http.authorizeRequests()
                    .antMatchers(*Companion.AUTH_WHITELIST).permitAll()
                    .antMatchers("/sign-up").permitAll()
                    .antMatchers(POST, "/api/players").permitAll()
                    .antMatchers("/**").authenticated()

                    .and()

                    .formLogin()
                    .loginPage("/login").permitAll()
                    .failureHandler(AuthenticationFailureHandler())
        }

        companion object {
            private val AUTH_WHITELIST = arrayOf(
                    "/css/**",
                    "/js/**",
                    "/images/**",

                    "/v2/api-docs",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**"
            )
        }

    }

    class AuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

        override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse,
                                             exception: AuthenticationException) {
            val exceptionResponse = ExceptionResponse(UNAUTHORIZED.value(), "Invalid username or password")
            response.status = exceptionResponse.status
            response.addHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
            response.writer.write(jacksonObjectMapper().writeValueAsString(exceptionResponse))
        }

    }

}
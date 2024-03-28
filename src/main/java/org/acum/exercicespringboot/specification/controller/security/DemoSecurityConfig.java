package org.acum.exercicespringboot.specification.controller.security;

import org.acum.exercicespringboot.entity.Candidate;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.sql.DataSource;
import java.time.Duration;

@Configuration
@EnableCaching
public class DemoSecurityConfig {

        @Bean
        public UserDetailsManager userDetailsManager(DataSource dataSource) {

            JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
            jdbcUserDetailsManager.setUsersByUsernameQuery(
                    "select user_id, pw, active from members where user_id=?");
            jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                    "select user_id, role from roles where user_id=?");
            return jdbcUserDetailsManager;
        }


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.authorizeHttpRequests(configurer ->
                            configurer
                                    .requestMatchers("/").hasRole("EMPLOYEE")
                                    .requestMatchers("/leaders/**").hasRole("MANAGER")
                                    .requestMatchers("/systems/**").hasRole("ADMIN")
                                    .requestMatchers("/candidate/**").hasRole("ADMIN")
                                    .anyRequest().authenticated()
                    )
                    .formLogin(form ->
                            form
                                    .loginPage("/showMyLoginPage")
                                    .loginProcessingUrl("/authenticateTheUser")
                                    .permitAll()
                    )
                    .logout(logout -> logout.permitAll()
                    )
                    .exceptionHandling(configurer ->
                            configurer.accessDeniedPage("/access-denied")
                    );

            return http.build();
        }


         @Bean
        public CacheManager getCacheManager() {
            CachingProvider provider = Caching.getCachingProvider();
            CacheManager cacheManager = provider.getCacheManager();
            CacheConfiguration<Object, Object> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder
                            .heap(100)
                            .offheap(10, MemoryUnit.MB))
                    .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(10)))
                    .build();
            javax.cache.configuration.Configuration<Object, Object> cacheConfiguration = Eh107Configuration
                    .fromEhcacheCacheConfiguration( configuration);
            cacheManager.createCache("candidate", cacheConfiguration);
            return cacheManager;
        }


}

package backend.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

// @Configuration

// public class CorsConfig implements WebMvcConfigurer {
// 	@Bean
// 	public WebMvcConfigurer corsConfigurer() {
// 		return new WebMvcConfigurer() {
// 			@Override
// 			public void addCorsMappings(CorsRegistry registry) {
// 				registry.addMapping("/api/v1/**")
// 				.allowedOrigins("*")
// 				.allowedHeaders("*")
// 				.allowedMethods("*")
// 				.maxAge(-1)
// 				.allowCredentials(false);
// 			}
// 		};
// 	}
// }

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

/* (non-Javadoc)
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry)
 */
@Override
protected void addCorsMappings(CorsRegistry registry) {
    //NOTE: servlet context set in "application.properties" is "/api" and request like "/api/session/login" resolves here to "/session/login"!
    registry.addMapping("/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .allowCredentials(false);
    }
}
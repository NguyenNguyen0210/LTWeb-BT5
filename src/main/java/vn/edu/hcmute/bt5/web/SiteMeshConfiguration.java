package vn.edu.hcmute.bt5.web;

import jakarta.servlet.DispatcherType;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
class SiteMeshConfiguration {
    @Bean
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> siteMeshResponseBuffering() {
        // Let SiteMesh write the final HTML after Spring MVC forwards to a JSP.
        return factory -> factory.addContextCustomizers(
                context -> context.setSuspendWrappedResponseAfterForward(false));
    }

    @Bean
    FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter() {
        var filter = new ConfigurableSiteMeshFilter() {
            @Override
            protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
                builder.addDecoratorPath("/*", "web.jsp")
                        .addDecoratorPath("/admin/*", "admin.jsp")
                        .addExcludedPath("/login")
                        .addExcludedPath("/css/*")
                        .addExcludedPath("/api/*");
            }
        };
        var registration = new FilterRegistrationBean<ConfigurableSiteMeshFilter>(filter);
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }
}

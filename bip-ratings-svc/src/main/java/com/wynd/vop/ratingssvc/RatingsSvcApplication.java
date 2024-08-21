package com.wynd.vop.ratingssvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import brave.sampler.Sampler;
import com.wynd.vop.ratingssvc.config.RatingsSvcConfig;

/**
 * An <tt>RatingsSvc Service Application</tt> enabled for Spring Boot Application,
 * Spring Cloud Netflix Feign Clients, Hystrix circuit breakers, Swagger and
 * AspectJ's @Aspect annotation.
 *
 */
@SpringBootApplication
@EnableDiscoveryClient // needed to reach out to spring cloud config, eureka
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableFeignClients
@EnableCaching
@EnableAsync
// Add any partner Config classes to @Import
@Import({ RatingsSvcConfig.class })
public class RatingsSvcApplication {

	/**
	 * Runs the spring-boot application with this class and any command-line arguments.
	 *
	 * @param args the array or command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RatingsSvcApplication.class, args);
	}

	/**
	 * Always sampler for Zipkin traces.
	 *
	 * @return the sampler
	 */
	@Bean
	Sampler alwaysSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}

package com.wynd.vop.ratingssvc.config;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = { "spring.cloud.bus.enabled=false", "spring.cloud.discovery.enabled=false",
        "spring.cloud.consul.enabled=false", "spring.cloud.config.discovery.enabled=false",
        "spring.cloud.vault.enabled=false", "bip.framework.security.http.cors.enabled=true",
        "bip-ratings-svc.orIgIns=https://bip-ratings-svc-ui-dev.dev.bip.va.gov" })
public class RatingsSvcCorsConfigTest extends TestCase {

 @Autowired
 private RatingsSvcCorsConfig ratingssvcCorsConfig;
 @Test
 public void testCorsConfigurer() {
   WebMvcConfigurer corsConfigurer
       = ratingssvcCorsConfig.corsConfigurer();
   Assert.assertNotNull(corsConfigurer);
 }
}
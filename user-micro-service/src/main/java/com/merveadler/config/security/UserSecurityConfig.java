package com.merveadler.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class UserSecurityConfig {

    @Bean
    JwtFilter getJwtFilter(){
        return new JwtFilter();
    }

    /**
     * Spring Application Context, uygulamanız başlatılırken gerekli olan tüm sınıflardan nesneler
     * yaratarak context içine atar, gerekli olduğunda buradan kullanır.
     * Spring Security de SecurityFilterChain nesnesini context içine atar. Bu nesne ile
     * filtreleme işlemlerini yapar. Biz bu sınıf içinde bir SecurityFilterChain nesnesi oluşturarak
     * yani bir Bean yaratarak bu nesnenin özelliklerini değiştireceğiz.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /**
         * Spring 6.0 ve üzeri için Security Kullanımı
         * permitAll() -> herkese açık
         * hasRole() -> belirtilen rolde olmalı
         * DİKKAT!! kişi kullanıcı adı şifresi ya da jwt ile giriş yapsa bile eğer belirtilen rolü yok ise giriş yapamaz.
         */
        httpSecurity.authorizeHttpRequests(req->
                req.requestMatchers("/api/v1/user/hello","/swagger-ui/**",
                                "/v3/api-docs/**", "/userrole/**","/api/v1/user/upperCaseName")   // requestMatchers isteklerin end point path lerin tanımlandığı kısım
                        .permitAll() // herkese açık
                        .requestMatchers("/api/v1/user/**") // http://localhost:9092/api/v1/user/[herhangi bir istek]
                        .hasAuthority("USER") // user rolüne sahip olmalı
                        // .requestMatchers("/api/v1/**").hasAnyRole("Admin", "SuperUser", "AhmetAmca", "Ben_De_GELDIM")

                        //.anyRequest() // diğer bütün istekler
                      //  .authenticated() // oturum açmış olmayı zorla
        );

        /**
         * DİKKAT!!! RestApı kullanırken form login işlemleri kullanılmaz. Aynı zamanda form işlemlerinde
         * gelen isteklerin kendi gönderdiğimiz formlar üzerinden gelmesini sağlamak için CSRF token kullanılır.
         * bu rest api için mümkün değildir bu nedenel kapatmanız gerekir. aksi takdirde 403 hata kodu alırsınız.
         * _csrf
         *
         * <form action="/login" method="post">
         *      _csrf = UUID
         *      username
         *      password
         * </form>
         * http:localhost:9090/login -> fetch('http://localhost:9090/login',{username, password});
         */
        httpSecurity.csrf(AbstractHttpConfigurer::disable); // csrf token kullanımını kapatır
        /**
         * Filtreleme işleminden önce devreye girerer filtreleme işlemnini manipüle ederek değiştiriyoruz.
         * yani filtreleme yöntemini devralıuyoruz.
         */
        httpSecurity.addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        /**
         * Spring Security Spring 6.0 öncesi için kullanım örnekleri
         */
//        httpSecurity
//                .authorizeRequests()
//                .antMatchers("/admin/**") // admin altındaki tüm istekler
//                .permitAll() // herkese açık izin ver
//                .anyRequest() // herhangi bir istek gelirse
//                .authenticated(); // oturum açmış olmayı zorla
        // httpSecurity.formLogin(); // form login kullan
        // httpSecurity.formLogin("/admin/login");
 //       httpSecurity.formLogin(Customizer.withDefaults()); // varsayılan form login sayfasını kullan

        return httpSecurity.build();
    }
}

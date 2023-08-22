package com.merveadler.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    /**
     * RabbitMQ bir mesajı iletmek için bir exchange ve bir queue kullanır.
     * Bu iki yapı arasında bir routing-key bulunur. Bu tanımlamaları yapılandırmak için
     * bunların isimlerini tanımlamamız gerekiyor.
     */
    private final String directExchangeAuth = "direct-exchange-auth";
    private final String queueAuthCreateProfile = "queue-auth-create-profile";
    private final String routingKetAuth = "routing-key-auth-create-profile";
    /**
     * burada kuyruğu tanımlamak için ortamda Bean ile nesneleri enjekte ediyoruz.
     */
    @Bean
    DirectExchange directExchangeAuthMethod(){
        return new DirectExchange(directExchangeAuth);
    }
    @Bean
    Queue createQueueAuthCreateProfileMethod(){
        return new Queue(queueAuthCreateProfile);
    }
    /**
     * Bu kısımda ortamda oluşturduğumuz Excehange ile Queue arasında bir yol bir bağlantı oluşturuyoruz.
     * böylece mesajlarımızı bu yolla iletebiliyoruz.
     */
    @Bean
    public Binding bindingCreateProfileMethod(final DirectExchange directExchangeAuthMethod,
                                              final Queue createQueueAuthCreateProfileMethod){
        return BindingBuilder
                .bind(createQueueAuthCreateProfileMethod)
                .to(directExchangeAuthMethod)
                                .with(routingKetAuth);
    }
}

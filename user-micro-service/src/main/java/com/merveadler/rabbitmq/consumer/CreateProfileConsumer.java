package com.merveadler.rabbitmq.consumer;

import com.merveadler.dto.request.UserSaveRequestDto;
import com.merveadler.rabbitmq.model.CreateProfile;
import com.merveadler.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProfileConsumer {
    private final UserService userService;
    @RabbitListener(queues = "queue-auth-create-profile")
    public void consumerCreateProfile(CreateProfile createProfile) {
        System.out.println("Create Profile: " + createProfile.toString());
        userService.save(UserSaveRequestDto.builder()
                        .authid(createProfile.getAuthid())
                        .username(createProfile.getUsername())
                        .email(createProfile.getEmail())
                .build());

    }
}

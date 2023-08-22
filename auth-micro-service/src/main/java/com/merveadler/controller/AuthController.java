package com.merveadler.controller;

import com.merveadler.dto.request.DoLoginRequestDto;
import com.merveadler.dto.request.DoRegisterRequestDto;
import com.merveadler.dto.response.DoLoginResponseDto;
import com.merveadler.dto.response.DoRegisterResponseDto;
import com.merveadler.rabbitmq.model.CreateProfile;
import com.merveadler.rabbitmq.producer.CreateProfileProducer;
import com.merveadler.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.merveadler.constants.RestApis.*;
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CreateProfileProducer createProfileProducer;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Merhaba bu servis AUTH servisidir.");
    }

    @GetMapping("/testrabbit")
    public ResponseEntity<Void> testRabbitSendMessage(String username,String email,Long authid){
        createProfileProducer.sendCreateProfileMessage(
                CreateProfile.builder()
                        .authid(authid)
                        .email(email)
                        .username(username)
                        .build()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping(LOGIN)
    @CrossOrigin("*")
    public ResponseEntity<DoLoginResponseDto> doLogin(@RequestBody @Valid DoLoginRequestDto dto){
       String token = authService.login(dto);
       return ResponseEntity.ok(DoLoginResponseDto.builder()
                           .status(200)
                           .result("Giriş İşlemi Başarılı")
                           .token(token)
                   .build());
    }

    @PostMapping(REGISTER)
    @CrossOrigin("*")
    public ResponseEntity<DoRegisterResponseDto> doRegister(@RequestBody @Valid DoRegisterRequestDto dto){
        Boolean isRegister = authService.register(dto);
        if(isRegister)
            return ResponseEntity.ok(DoRegisterResponseDto.builder()
                    .status(200)
                    .result("Kayıt İşlemi Başarılı")
                    .build());
        return ResponseEntity.badRequest().body(
                DoRegisterResponseDto.builder()
                        .status(400)
                        .result("Kayıt İşlemi Başarısız oldu. Lütfen tekrar deneyiniz.")
                        .build()
        );
    }
}

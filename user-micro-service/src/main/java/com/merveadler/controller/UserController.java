package com.merveadler.controller;

import com.merveadler.dto.request.UserSaveRequestDto;
import com.merveadler.dto.response.UserSaveResponseDto;
import com.merveadler.repository.entity.User;
import com.merveadler.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.merveadler.constants.RestApis.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

    @GetMapping("/merhaba")
    @PreAuthorize("hasAnyAuthority('USER','AHMET_DAYI')")
    public ResponseEntity<String> getMerhaba(){
        return ResponseEntity.ok("Merhaba bu USER Role ile girilen bir method dur");
    }

    @GetMapping("/upperCaseName")
    public ResponseEntity<String> getUppserCaseName(String ad){
        return ResponseEntity.ok(userService.toUpper(ad));
    }


    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Merhaba bu servis USER servisidir.");
    }

    @PostMapping(SAVE)
    @CrossOrigin("*")
    public ResponseEntity<UserSaveResponseDto> save(@RequestBody UserSaveRequestDto dto){
        userService.save(dto);
        return ResponseEntity.ok(UserSaveResponseDto.builder()
                .status(200)
                .result("Başarılı bir şekilde kayıt edildi.")
                .build());
    }

    @GetMapping(FINDALL)
    @CrossOrigin("*")
    public ResponseEntity<List<User>> findAll(String token){
        return ResponseEntity.ok(userService.findAll(token));
    }
}

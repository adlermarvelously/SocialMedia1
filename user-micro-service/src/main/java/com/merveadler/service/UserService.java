package com.merveadler.service;

import com.merveadler.dto.request.UserSaveRequestDto;
import com.merveadler.exceptions.ErrorType;
import com.merveadler.exceptions.UserException;
import com.merveadler.repository.IUserRepository;
import com.merveadler.repository.entity.User;
import com.merveadler.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository repository;
    private final JwtTokenManager jwtTokenManager;

    public void save(UserSaveRequestDto dto){
        repository.save(User.builder()
                        .authid(dto.getAuthid())
                        .username(dto.getUsername())
                        .email(dto.getEmail())
                .build());
    }

    /**
     * ad = muhammet -> MUHAMMET
     * Cache- > redis üzerinde =     upperCase::muhammet -> MUHAMMET
     * @param ad
     * @return
     */
    @Cacheable(value = "upperCase")
    public String toUpper(String ad){
        String upperCaseName = ad.toUpperCase();
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            System.out.println("Hata oluştu");
        }
        return upperCaseName;
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public List<User> findAll(String token){
        Optional<Long> id = jwtTokenManager.getByIdFromToken(token);
        if(id.isEmpty()) throw new UserException(ErrorType.INVALID_TOKEN);
        return repository.findAll();
    }

}

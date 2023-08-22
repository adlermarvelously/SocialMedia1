package com.merveadler.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoLoginRequestDto {
    @NotEmpty(message = "Kullanıcı adı boş geçilemez")
    @Size(min = 3,max=64,message = "Kullanıcı adı 3 ile 64 karakter arasında olmalıdır")
    String username;
    @NotEmpty(message = "Şifreyi boş geçemezsiniz")
    @Size(min = 8,max = 64, message = "Şifre 8 ile 64 karakter arasında olmalıdır.")
    @Pattern(
            message = "Şifre enaz 8 karakter olmalı, içinde en az bir büyük bir küçük harf sayı ve rakam olmalıdır.",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$")
    String password;
}

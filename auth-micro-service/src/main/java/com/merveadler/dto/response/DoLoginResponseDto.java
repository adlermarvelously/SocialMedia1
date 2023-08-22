package com.merveadler.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoLoginResponseDto {
    /**
     * 200 - Başarılı
     * 400 - Hatalı istek
     *
     */
    Integer status;
    String result;
    String token; // JWT Token
}

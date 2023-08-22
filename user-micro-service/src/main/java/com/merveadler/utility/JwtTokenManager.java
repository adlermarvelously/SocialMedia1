package com.merveadler.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    /**
     * 1- Kullanıcı kendini doğrulamalı, username ve password ile. doLogin
     * 2- doğrulanmış kişinin kimlik bilgileri ile ona yeni bir JWT oluşturmalıyız.
     */
    private final long exDate = 1000L*20; // 20 saniye
    private final String sifreAnahtari = "bşfb546-5436*-4--56ı987845ygyhı45*096845096";
    public Optional<String> createToken(Long id){
        try{
            String token;
            token = JWT.create()
                    .withAudience()
                    // DİKKAT!! Lütfen buraya kişisel bilgiler eklemeyin ya da gizli olan bilgiler eklemeyin.
                    // şifre, adres, email v.s. buraya eklenmemeli.
                    .withIssuer("muhammet") // Token içinde gizli olmayan ancak kullanılması gereken bilgiler
                    .withClaim("id",id)
                    .withClaim("islemturu","genel")
                    .withIssuedAt(new Date()) // JWT token ın oluşturulma zamanı. timestamp
                    .withExpiresAt(new Date(System.currentTimeMillis() + exDate)) // JWT token ın geçerlilik süresi. timestamp
                    .sign(Algorithm.HMAC512(sifreAnahtari)); // JWT token ın şifreleme algoritması
            return Optional.of(token);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public Optional<Long> getByIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(sifreAnahtari);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("muhammet")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT==null)
                return Optional.empty();
            Long id = decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }

}

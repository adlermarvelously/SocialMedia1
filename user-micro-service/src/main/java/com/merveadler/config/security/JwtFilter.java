package com.merveadler.config.security;

import com.merveadler.exceptions.ErrorType;
import com.merveadler.exceptions.UserException;
import com.merveadler.utility.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    JwtUser jwtUser;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /**
         * RestAPI ye gelen istek türleri şu şekilde ulaşır.
         * fetch('http://localhost:9090/api/v1/user/findAll',{
         *     method: 'POST',
         *     headers: {
         *         'Content-Type': 'application/json',
         *         'Authorization': 'Bearer ' + token
         *     },
         *     body: JSON.stringify({
         *         userid: 223454354,
         *         ip: '122.28.57.54'
         *     })
         * })
         * Burada dikkat ederseniz Header içinde token bilgisi Authorization KEY ile gelir bu nedenle header içinden
         * bu KEY in VALUE bilgisini almamız ve içinden token ı ayırmamız gereklidir.
          */
        final String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){ // başlık dolumu ve Bearer ile başlıyor mu?
            String token = authorizationHeader.substring(7); // token bilgisinin çek.
            Optional<Long> authId = jwtTokenManager.getByIdFromToken(token); // token içinden id bilgisini çek.
            if(authId.isEmpty()) throw new UserException(ErrorType.INVALID_TOKEN); // token geçersiz ise hata fırlat.
            UserDetails userDetails = jwtUser.getUserByAuthId(authId.get());
            UsernamePasswordAuthenticationToken userSpringToken =
             new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(userSpringToken);
        }
        filterChain.doFilter(request,response);
    }
}

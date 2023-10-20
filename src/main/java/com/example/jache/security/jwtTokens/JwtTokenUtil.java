package com.example.jache.security.jwtTokens;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.repository.ChefRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.springframework.security.config.Elements.JWT;

@Slf4j
@Service //jwt토큰 서비스니까
@RequiredArgsConstructor
public final class JwtTokenUtil {

    private final UserDetailsService userDetailsService;
    private final ChefRepository chefRepository;
    private static final String SECRET_KEY = "sljflsalkfjleiglejgalejlighlaealeldkjlfeilvnk";

    /*
     JWT Token 발급
     */
    public String createToken(String email) {

        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 *24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String email){
        Claims reClaims = Jwts.claims();
        reClaims.put("email",email);

        return Jwts.builder()
                .setClaims(reClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
    토큰 유효한지 검사
     */
    public String validRefreshToken(String refreshToken){
        try {
            Claims claims = extractClaims(refreshToken);
            if(!claims.getExpiration().before(new Date())){
                return createRefreshToken(claims.get("email").toString());
            }
        }catch (ExpiredJwtException e){
            log.info("만료된 토큰");
            return new CustomException(CustomResponseStatus.EXPIRED_TOKEN).getMessage();
        }
        return null;
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    // Request의 Header에서 token 값을 가져옵니다. "Bearer " : "TOKEN값'
    public String resolveToken(String token){
        if(token != null){
            return token.substring("Bearer ".length());
        }
        else{
            return "";
        }
    }

    /*
    refresh 토큰 얻기
     */
    public String getRefreshByChefName(String token){
        String email = getEmail(token);
        String refresh = chefRepository.findChefByEmail(email).orElseThrow().getRefreshToken();
        return refresh;
    }

    public void removeRefreshToken(String chefName, String refreshToken){
        chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        ).modifyRefreshToken(null);
    }

    // 밝급된 Token이 만료 시간이 지났는지 체크
    public boolean isTokenExpired(String token) {

        // Token의 만료 날짜가 지금보다 이전인지 check
        //return expiredDate.before(new Date());
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {

        return extractClaims(token).getExpiration();
    }


    //claim - 정보를 담고 있는 name/value 한 쌍
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSigninKey() {
        //byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    //토큰에서 email꺼내기
    public String getEmail(String token){

        return extractClaims(token).get("email",String.class);
    }

    public boolean isNeedToUpdateRefreshToken(String token){
        try{
            Date expireAt = extractExpiration(token);
            Date current = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE,7);

            Date afterSevenDays = calendar.getTime();
            if(expireAt.before(afterSevenDays)){
                log.info("리프레시 토큰 7일 이내 만료");
                return true;
            }
        }catch (ExpiredJwtException e){
            return true;
        }
        return false;
    }

    public String extractJwt(final StompHeaderAccessor accessor) {
        return accessor.getFirstNativeHeader("Authorization");
    }

    // jwt 인증
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CustomException(CustomResponseStatus.MALFORMED_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomException(CustomResponseStatus.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(CustomResponseStatus.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(CustomResponseStatus.BAD_JWT);
        }
    }
}

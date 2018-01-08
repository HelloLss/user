package com.lss.user.util;

import com.lss.user.config.JwtConfig;
import com.lss.user.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

import static com.lss.user.util.HttpUtils.*;


@Component
@Slf4j
public class JwtUtil {
    @Autowired
    public JwtConfig jwtConfig;

    public String createToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密匙
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtConfig.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
            .claim(HEADER_USER_ID, user.getId())
            .claim(HEADER_NICKNAME, user.getNickName())
            .claim(HEADER_PHOTO, user.getPhoto())
            .claim(HEADER_PHONE, user.getPhoneNumber())
            .setIssuer(jwtConfig.getClientId())
            .setAudience(jwtConfig.getName())
            .signWith(signatureAlgorithm, signingKey)
            .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiresMillis()))
            .compact();
    }

    /**
     * 根据 jwt token获取存在里边的 用户基本信息
     *
     * @param jsonWebToken
     * @return
     */
    public BaseUserInfo getBaseInfo(String jsonWebToken) {
        Claims claims = getClaims(jsonWebToken);
        if (claims == null) {
            return null;
        }
        BaseUserInfo userInfo = new BaseUserInfo();
        userInfo.setId(claims.get(HEADER_USER_ID, Integer.class));
        userInfo.setNickname(claims.get(HEADER_NICKNAME, String.class));
        userInfo.setPhone(claims.get(HEADER_PHONE, String.class));
        userInfo.setPhoto(claims.get(HEADER_PHOTO, String.class));
        return userInfo;
    }

    private Claims getClaims(String token) {
        if (!validateToken(token)) {
            return null;
        }
        return Jwts.parser().parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid jwt signature.");
            log.trace("Invalid jwt signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid jwt token.");
            log.trace("Invalid jwt token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired jwt token.");
            log.trace("Expired jwt token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported jwt token.");
            log.trace("Unsupported jwt token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("jwt token compact of handler are invalid.");
            log.trace("jwt token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}

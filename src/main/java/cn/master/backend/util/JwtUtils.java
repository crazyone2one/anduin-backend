package cn.master.backend.util;

import cn.master.backend.security.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author create by 11's papa on 2022-11-18
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {
    final JwtProperties jwtProperties;

    /**
     * 创建JWT
     *
     * @param claims  私有声明
     * @param subject subject
     * @return java.lang.String
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date nowDate = new Date();
        return Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                .setSubject(subject)
                //iat: jwt的签发时间
                .setIssuedAt(nowDate)
                //设置过期时间
                .setExpiration(new Date(nowDate.getTime() + 1000 * jwtProperties.getExpirationTime()))
                // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 判断JWT是否过期
     *
     * @param token token
     * @return java.lang.Boolean
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析JWT
     *
     * @param token token
     * @return io.jsonwebtoken.Claims
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }

    /**
     * 生成JWT
     *
     * @param userDetails userDetails
     * @return java.lang.String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new LinkedHashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
}

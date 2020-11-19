package br.com.acalapi.configuration.security;

import br.com.acalapi.entity.security.Usuario;
import com.google.gson.Gson;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationService {

    private static final long HOURS_TO_EXPIRATION = 24;

    private static final String SECRET = "mySecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse response, Authentication auth) {

        var usuario = (Usuario) auth.getPrincipal();

        JwtBuilder jwt = Jwts.builder()
            .setSubject(auth.getName())
            .claim("role", auth.getAuthorities())
            .claim("title", usuario.getTitle())
            .claim("name", usuario.getName())
            .claim("key", usuario.getId())
            .setExpiration(
                Date.from(LocalDateTime.now().plusHours(HOURS_TO_EXPIRATION).atZone(ZoneId.systemDefault()).toInstant())
            )
            .signWith(SignatureAlgorithm.HS512,
                new SecretKeySpec(
                    DatatypeConverter.parseBase64Binary(SECRET),
                    SignatureAlgorithm.HS512.getJcaName()
                )
            );

        String token = jwt.compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);

        Usuario user = (Usuario) auth.getPrincipal();
        user.setToken(token);

        try {

            response.getWriter().write(new Gson().toJson(user));
            response.getWriter().flush();
            response.getWriter().close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }

        }

        return null;
    }
}

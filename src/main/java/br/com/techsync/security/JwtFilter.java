package br.com.techsync.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String chaveSecreta = "MinhaChaveSuperSecreta1234567890123456";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Configurar cabeçalhos CORS para todas as respostas
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // Permitir requisições OPTIONS (pré-voo CORS) sem validação
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove o "Bearer "

            try {
                // Valida o token
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(chaveSecreta.getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();
                request.setAttribute("email", email);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token JWT inválido ou expirado.");
                return;
            }

        } else {
            if (isProtectedEndpoint(request)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token JWT ausente.");
                return;
            }
        }

        // Continua a requisição
        filterChain.doFilter(request, response);
    }

    private boolean isProtectedEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Endpoints desprotegidos
        if (path.equals("/api/usuarios/login") && method.equals("POST")) {
            return false;
        }

        if (path.equals("/api/usuarios") && method.equals("POST")) {
            return false;
        }

        return path.startsWith("/api/usuarios");
    }
}
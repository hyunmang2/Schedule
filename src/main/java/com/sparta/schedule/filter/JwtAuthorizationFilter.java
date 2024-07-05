package com.sparta.schedule.filter;

import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "jwt검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromHeader(JwtUtil.AUTHORIZATION_HEADER, request);

        if (StringUtils.hasText(tokenValue)) {
            try {
                if (!jwtUtil.validateToken(tokenValue)) {
                    log.error("토큰이 일치하지 않습니다.");
                } else if (tokenValue == null) {
                    throw new IllegalArgumentException("토큰이 없습니다.");
                }
                // jwt 구조자체가 헤더, 페이로드, 시그니처로 나뉘어진다.
                // Claims는 페이로드 = 데이터다.
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                // info에 담긴 subjedct(username)로 인증객체 생성 및 인증처리
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        // UsernamePasswordAuthenticationFilter로 넘어가는 것
        filterChain.doFilter(request, response);
    }

    // 인증이 된 유저 정보를 저장
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}

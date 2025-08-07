package eng.bns.nomenclature.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = auth.substring(7);
            if (jwtService.validToken(jwt)) {
                String username = jwtService.excractUsername(jwt);
                List<String> roles = jwtService.extractRoles(jwt);
                List<GrantedAuthority> authorities = roles.stream()
                        .map(this::extractRoleName)
                        .filter(Objects::nonNull)
                        .map(roleName -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + roleName))
                        .toList();


                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);

                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");

        }





    }
    private String extractRoleName(String role) {
        if (role == null || role.isEmpty()) {
            return null;
        }

        // Pattern to match "name=ADMIN" in the role string
        Pattern pattern = Pattern.compile("name=([A-Z]+)");
        Matcher matcher = pattern.matcher(role);

        if (matcher.find()) {
            return matcher.group(1); // Returns "ADMIN"
        }

        // Fallback to simple extraction if pattern doesn't match
        return role.replace("ROLE_", "")
                .replaceAll("\\[.*?\\]", "")
                .trim();
    }

}

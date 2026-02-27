package com.toomate.backend.config;

import com.toomate.backend.service.AutenticacaoService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AutenticacaoProvider implements AuthenticationProvider {
    private final AutenticacaoService usuarioAutenticacaoService;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoProvider(AutenticacaoService usuarioAutenticacaoService, PasswordEncoder passwordEncoder) {
        this.usuarioAutenticacaoService = usuarioAutenticacaoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        UserDetails userDetails = usuarioAutenticacaoService.loadUserByUsername(username);

        try {
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
        } catch (IllegalArgumentException exception) {
            throw new BadCredentialsException("Usuario ou senha invalidos!");
        }

        throw new BadCredentialsException("Usuario ou senha invalidos!");
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

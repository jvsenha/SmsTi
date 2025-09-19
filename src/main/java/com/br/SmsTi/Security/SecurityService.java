package com.br.SmsTi.Security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {


    public boolean isNivel1(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Nivel1"));
    }

    public boolean isNivel2(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Nivel2"));
    }

    public boolean isNivel3(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().startsWith("Nivel3"));
    }

}

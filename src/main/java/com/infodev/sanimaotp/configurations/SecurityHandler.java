package com.infodev.sanimaotp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


@Component
public class SecurityHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        Set<String> roleSet = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println(roleSet.size());
        if(roleSet.contains("ROLE_CENTRAL_ADMIN") || roleSet.contains("ROLE_BRANCH_ADMIN") || roleSet.contains("ROLE_NORMAL_USER")){
            response.sendRedirect(request.getContextPath()+"/admin");

        }else{
            response.sendRedirect(request.getContextPath()+"/dashboard");
        }

    }
}

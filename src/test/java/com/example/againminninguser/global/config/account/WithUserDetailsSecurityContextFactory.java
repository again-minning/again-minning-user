package com.example.againminninguser.global.config.account;

import com.example.againminninguser.domain.account.domain.dto.UserAccount;
import com.example.againminninguser.global.common.AccountTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithUserDetailsSecurityContextFactory implements WithSecurityContextFactory<TestAccount> {
    @Override
    public SecurityContext createSecurityContext(TestAccount annotation) {
        UserDetails userAccount = new UserAccount(AccountTemplate.account);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAccount, "", userAccount.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        return context;
    }
}
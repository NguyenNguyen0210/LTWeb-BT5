package vn.edu.hcmute.bt5.account;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

final class AccountPrincipal extends User {
    private final Long accountId;
    private final long accountVersion;

    AccountPrincipal(Account account) {
        super(account.getEmail(), account.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole().name())));
        accountId = account.getId();
        accountVersion = account.getVersion();
    }

    Long accountId() {
        return accountId;
    }

    long accountVersion() {
        return accountVersion;
    }
}

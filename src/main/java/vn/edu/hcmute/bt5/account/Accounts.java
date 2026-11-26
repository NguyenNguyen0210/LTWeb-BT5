package vn.edu.hcmute.bt5.account;

import java.util.Locale;

import jakarta.validation.Validator;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

@Service
class Accounts implements UserDetailsService {
    private final AccountRepository repository;
    private final PasswordEncoder passwords;

    Accounts(AccountRepository repository, PasswordEncoder passwords) {
        this.repository = repository;
        this.passwords = passwords;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return new AccountPrincipal(repository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("Account unavailable")));
    }

    boolean isCurrent(AccountPrincipal principal) {
        return repository.existsByIdAndVersion(principal.accountId(), principal.accountVersion());
    }

    Account find(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    Page<Account> search(String keyword, Pageable pageable) {
        return repository.findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(keyword, keyword, pageable);
    }

    void saveUser(Account existing, AccountForm form, Errors errors) {
        if ((existing == null || form.hasPassword()) && !validPassword(form.password())) {
            errors.rejectValue("password", "length", "Mật khẩu phải có ít nhất 8 ký tự và chứa ký tự khác khoảng trắng.");
        }
        if (errors.hasErrors()) {
            return;
        }

        String email = normalizeEmail(form.email());
        if (repository.findByEmail(email).filter(other -> existing == null || !other.getId().equals(existing.getId()))
                .isPresent()) {
            errors.rejectValue("email", "duplicate", "Email này đã được sử dụng.");
            return;
        }

        try {
            if (existing == null) {
                repository.save(new Account(email, form.fullName().strip(),
                        passwords.encode(form.password()), Account.Role.USER));
            } else {
                String hash = form.hasPassword() ? passwords.encode(form.password()) : existing.getPasswordHash();
                existing.update(email, form.fullName().strip(), hash);
                repository.save(existing);
            }
        } catch (DataIntegrityViolationException ex) {
            errors.rejectValue("email", "duplicate", "Email này đã được sử dụng.");
        }
    }

    void delete(Account account) {
        repository.delete(account);
    }

    @Bean
    ApplicationRunner initializeAdmin(Environment env, Validator validator) {
        return args -> {
            String email = env.getProperty("BT5_ADMIN_EMAIL");
            String password = env.getProperty("BT5_ADMIN_PASSWORD");
            if ((email == null && password == null) || repository.existsByRole(Account.Role.ADMIN)) {
                return;
            }

            AccountForm admin = new AccountForm(email, "Quản trị viên", password);
            if (!validator.validate(admin).isEmpty() || !validPassword(password)) {
                throw new IllegalArgumentException("Set BT5_ADMIN_EMAIL and a valid BT5_ADMIN_PASSWORD (8–128 characters).");
            }
            String normalized = normalizeEmail(email);
            if (repository.findByEmail(normalized).isPresent()) {
                throw new IllegalStateException("BT5_ADMIN_EMAIL belongs to an existing account. Choose an unused email.");
            }
            repository.save(new Account(normalized, admin.fullName(), passwords.encode(password), Account.Role.ADMIN));
        };
    }

    private static boolean validPassword(String password) {
        return password != null && password.length() >= 8 && !password.isBlank();
    }

    private static String normalizeEmail(String email) {
        return email.strip().toLowerCase(Locale.ROOT);
    }
}

package vn.edu.hcmute.bt5.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountForm(
        @NotBlank @Email @Size(max = 100) String email,
        @NotBlank @Size(max = 100) String fullName,
        @Size(max = 128) String password) {

    boolean hasPassword() {
        return password != null && !password.isEmpty();
    }
}

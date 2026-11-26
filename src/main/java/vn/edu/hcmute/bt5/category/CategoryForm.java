package vn.edu.hcmute.bt5.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CategoryForm(
        @NotBlank @Size(max = 50) String name,
        @Size(max = 500) @URL(message = "Hãy nhập một URL hợp lệ.") String imageUrl,
        @NotNull Boolean active) {
}

package vn.edu.hcmute.bt5.account;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class AuthController {
    private final Accounts accounts;

    AuthController(Accounts accounts) {
        this.accounts = accounts;
    }

    @GetMapping("/")
    String home() {
        return "home";
    }

    @GetMapping("/login")
    String login(Authentication authentication) {
        return signedIn(authentication) ? "redirect:/" : "login";
    }

    @GetMapping("/register")
    String register(Authentication authentication, Model model) {
        if (signedIn(authentication)) {
            return "redirect:/";
        }
        model.addAttribute("accountForm", new AccountForm("", "", null));
        return "register";
    }

    @PostMapping("/register")
    String register(Authentication authentication, @Valid @ModelAttribute AccountForm accountForm,
                    BindingResult errors) {
        if (signedIn(authentication)) {
            return "redirect:/";
        }
        accounts.saveUser(null, accountForm, errors);
        return errors.hasErrors() ? "register" : "redirect:/login?registered";
    }

    private boolean signedIn(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }
}

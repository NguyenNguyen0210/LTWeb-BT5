package vn.edu.hcmute.bt5.account;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.hcmute.bt5.web.AdminList;

@Controller
@RequestMapping("/admin/users")
class AdminUserController {
    private static final String PATH = "/admin/users";
    private static final String LIST = "redirect:" + PATH;
    private final Accounts accounts;

    AdminUserController(Accounts accounts) {
        this.accounts = accounts;
    }

    @GetMapping
    String list(@RequestParam(defaultValue = "") String q, @RequestParam(defaultValue = "1") int page,
                @AuthenticationPrincipal AccountPrincipal principal, Model model, RedirectAttributes redirect) {
        model.addAttribute("ownId", principal.accountId());
        return AdminList.render(q, page, PATH, "admin/users", accounts::search, model, redirect);
    }

    @GetMapping("/new")
    String newUser(Model model) {
        model.addAttribute("accountForm", new AccountForm("", "", null));
        return form(model, null);
    }

    @PostMapping
    String create(@Valid @ModelAttribute AccountForm accountForm, BindingResult errors,
                  Model model, RedirectAttributes redirect) {
        accounts.saveUser(null, accountForm, errors);
        if (errors.hasErrors()) {
            return form(model, null);
        }
        redirect.addFlashAttribute("notice", "Đã thêm người dùng.");
        return LIST;
    }

    @GetMapping("/{id}/edit")
    String edit(@PathVariable long id, Model model) {
        Account account = accounts.find(id);
        model.addAttribute("accountForm", new AccountForm(account.getEmail(), account.getFullName(), null));
        return form(model, account);
    }

    @PostMapping("/{id}")
    String update(@PathVariable long id, @Valid @ModelAttribute AccountForm accountForm,
                  BindingResult errors, Model model, RedirectAttributes redirect) {
        Account account = accounts.find(id);
        accounts.saveUser(account, accountForm, errors);
        if (errors.hasErrors()) {
            return form(model, account);
        }
        redirect.addFlashAttribute("notice", "Đã cập nhật người dùng.");
        return LIST;
    }

    @GetMapping("/{id}/delete")
    String confirmDelete(@PathVariable long id, @AuthenticationPrincipal AccountPrincipal principal,
                         Model model, RedirectAttributes redirect) {
        Account account = accounts.find(id);
        if (account.getId().equals(principal.accountId())) {
            return ownAccount(redirect);
        }
        model.addAttribute("itemName", account.getEmail());
        model.addAttribute("deletePath", PATH + "/" + id + "/delete");
        model.addAttribute("listPath", PATH);
        return "admin/delete";
    }

    @PostMapping("/{id}/delete")
    String delete(@PathVariable long id, @AuthenticationPrincipal AccountPrincipal principal,
                  RedirectAttributes redirect) {
        Account account = accounts.find(id);
        if (account.getId().equals(principal.accountId())) {
            return ownAccount(redirect);
        }
        accounts.delete(account);
        redirect.addFlashAttribute("notice", "Đã xóa người dùng.");
        return LIST;
    }

    private String ownAccount(RedirectAttributes redirect) {
        redirect.addFlashAttribute("problem", "Bạn không thể xóa tài khoản đang đăng nhập.");
        return LIST;
    }

    private String form(Model model, Account account) {
        model.addAttribute("editing", account != null);
        model.addAttribute("formPath", account == null ? PATH : PATH + "/" + account.getId());
        return "admin/user-form";
    }
}

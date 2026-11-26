package vn.edu.hcmute.bt5.category;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.hcmute.bt5.web.AdminList;

@Controller
@RequestMapping("/admin/categories")
class AdminCategoryController {
    private static final String PATH = "/admin/categories";
    private static final String LIST = "redirect:" + PATH;
    private final CategoryRepository categories;

    AdminCategoryController(CategoryRepository categories) {
        this.categories = categories;
    }

    @GetMapping
    String list(@RequestParam(defaultValue = "") String q, @RequestParam(defaultValue = "1") int page,
                Model model, RedirectAttributes redirect) {
        return AdminList.render(q, page, PATH, "admin/categories", categories::findByNameContainingIgnoreCase,
                model, redirect);
    }

    @GetMapping("/new")
    String newCategory(Model model) {
        model.addAttribute("categoryForm", new CategoryForm("", "", true));
        return form(model, null);
    }

    @PostMapping
    String create(@Valid @ModelAttribute CategoryForm categoryForm, BindingResult errors,
                  Model model, RedirectAttributes redirect) {
        return save(new Category(), categoryForm, errors, model, redirect);
    }

    @GetMapping("/{id}/edit")
    String edit(@PathVariable long id, Model model) {
        Category category = find(id);
        model.addAttribute("categoryForm",
                new CategoryForm(category.getName(), category.getImageUrl(), category.isActive()));
        return form(model, category.getId());
    }

    @PostMapping("/{id}")
    String update(@PathVariable long id, @Valid @ModelAttribute CategoryForm categoryForm,
                  BindingResult errors, Model model, RedirectAttributes redirect) {
        return save(find(id), categoryForm, errors, model, redirect);
    }

    @GetMapping("/{id}/delete")
    String confirmDelete(@PathVariable long id, Model model) {
        Category category = find(id);
        model.addAttribute("itemName", category.getName());
        model.addAttribute("deletePath", PATH + "/" + id + "/delete");
        model.addAttribute("listPath", PATH);
        return "admin/delete";
    }

    @PostMapping("/{id}/delete")
    String delete(@PathVariable long id, RedirectAttributes redirect) {
        categories.delete(find(id));
        redirect.addFlashAttribute("notice", "Đã xóa danh mục.");
        return LIST;
    }

    private Category find(long id) {
        return categories.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private String save(Category category, CategoryForm input, BindingResult errors,
                        Model model, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return form(model, category.getId());
        }
        boolean adding = category.getId() == null;
        category.update(input);
        categories.save(category);
        redirect.addFlashAttribute("notice", adding ? "Đã thêm danh mục." : "Đã cập nhật danh mục.");
        return LIST;
    }

    private String form(Model model, Long id) {
        model.addAttribute("editing", id != null);
        model.addAttribute("formPath", id == null ? PATH : PATH + "/" + id);
        return "admin/category-form";
    }
}

package vn.edu.hcmute.bt5.category;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
class AdminCategoryController {
    private static final String PATH = "/admin/categories";
    private static final String LIST = "redirect:" + PATH;
    private static final String SEARCH = PATH + "/searchpaginated";
    private static final List<Integer> PAGE_SIZES = List.of(3, 5, 10, 15, 20);
    private static final Sort SORT = Sort.by("name", "id");
    private final CategoryService categoryService;

    AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/searchpaginated"})
    String search(@RequestParam(required = false) String name,
                  @RequestParam(defaultValue = "") String q,
                  @RequestParam(defaultValue = "1") int page,
                  @RequestParam(defaultValue = "3") int size,
                  Model model, RedirectAttributes redirect) {
        String keyword = (name == null ? q : name).strip();
        keyword = keyword.substring(0, Math.min(keyword.length(), 100));
        int pageSize = PAGE_SIZES.contains(size) ? size : PAGE_SIZES.getFirst();
        int index = Math.clamp((long) page - 1, 0, Integer.MAX_VALUE / pageSize);
        var pageable = PageRequest.of(index, pageSize, SORT);
        Page<Category> resultPage = keyword.isEmpty() ? categoryService.findAll(pageable)
                : categoryService.findByNameContaining(keyword, pageable);
        int lastPage = Math.max(1, resultPage.getTotalPages());
        int currentPage = Math.clamp(page, 1, lastPage);
        if (currentPage != page || pageSize != size) {
            redirect.addAttribute("name", keyword);
            redirect.addAttribute("size", pageSize);
            redirect.addAttribute("page", currentPage);
            return "redirect:" + SEARCH;
        }

        int start = Math.max(1, Math.min(currentPage - 2, lastPage - 4));
        int end = Math.min(lastPage, start + 4);
        model.addAttribute("name", keyword);
        model.addAttribute("categoryPage", resultPage);
        model.addAttribute("pageNumbers", IntStream.rangeClosed(start, end).boxed().toList());
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("searchPath", SEARCH);
        return "admin/category/list";
    }

    @GetMapping({"/new", "/add"})
    String newCategory(Model model) {
        model.addAttribute("categoryForm", new CategoryForm("", "", true));
        return form(model, null);
    }

    @PostMapping
    String create(@Valid @ModelAttribute CategoryForm categoryForm, BindingResult errors,
                  Model model, RedirectAttributes redirect) {
        return save(new Category(), categoryForm, errors, model, redirect);
    }

    @GetMapping({"/{id}/edit", "/edit/{id}"})
    String edit(@PathVariable long id, Model model) {
        Category category = categoryService.find(id);
        model.addAttribute("categoryForm",
                new CategoryForm(category.getName(), category.getImageUrl(), category.isActive()));
        return form(model, category.getId());
    }

    @PostMapping("/{id}")
    String update(@PathVariable long id, @Valid @ModelAttribute CategoryForm categoryForm,
                  BindingResult errors, Model model, RedirectAttributes redirect) {
        return save(categoryService.find(id), categoryForm, errors, model, redirect);
    }

    @GetMapping({"/{id}/delete", "/delete/{id}"})
    String confirmDelete(@PathVariable long id, Model model) {
        Category category = categoryService.find(id);
        model.addAttribute("itemName", category.getName());
        model.addAttribute("deletePath", PATH + "/" + id + "/delete");
        model.addAttribute("listPath", PATH);
        return "admin/delete";
    }

    @PostMapping("/{id}/delete")
    String delete(@PathVariable long id, RedirectAttributes redirect) {
        categoryService.delete(categoryService.find(id));
        redirect.addFlashAttribute("notice", "Đã xóa danh mục.");
        return LIST;
    }

    private String save(Category category, CategoryForm input, BindingResult errors,
                        Model model, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return form(model, category.getId());
        }
        boolean adding = category.getId() == null;
        category.update(input);
        categoryService.save(category);
        redirect.addFlashAttribute("notice", adding ? "Đã thêm danh mục." : "Đã cập nhật danh mục.");
        return LIST;
    }

    private String form(Model model, Long id) {
        model.addAttribute("editing", id != null);
        model.addAttribute("formPath", id == null ? PATH : PATH + "/" + id);
        return "admin/category-form";
    }
}

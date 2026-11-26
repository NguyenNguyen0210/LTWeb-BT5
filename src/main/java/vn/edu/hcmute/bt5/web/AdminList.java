package vn.edu.hcmute.bt5.web;

import java.util.function.BiFunction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public final class AdminList {
    private static final int PAGE_SIZE = 10;

    private AdminList() {
    }

    public static String render(String keyword, int page, String path, String view,
                                BiFunction<String, Pageable, Page<?>> search,
                                Model model, RedirectAttributes redirect) {
        String query = keyword.strip();
        query = query.substring(0, Math.min(query.length(), 100));
        int index = Math.clamp((long) page - 1, 0, Integer.MAX_VALUE / PAGE_SIZE);
        Page<?> results = search.apply(query, PageRequest.of(index, PAGE_SIZE, Sort.by("id")));
        int lastPage = Math.max(1, results.getTotalPages());
        int validPage = Math.clamp(page, 1, lastPage);
        if (validPage != page) {
            redirect.addAttribute("q", query);
            redirect.addAttribute("page", validPage);
            return "redirect:" + path;
        }

        model.addAttribute("keyword", query);
        model.addAttribute("results", results);
        model.addAttribute("listPath", path);
        return view;
    }
}

package vn.edu.hcmute.bt5.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findByNameContaining(String keyword, Pageable pageable);

    Page<Category> findAll(Pageable pageable);

    Category find(long id);

    void save(Category category);

    void delete(Category category);
}

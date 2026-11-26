package vn.edu.hcmute.bt5.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where locate(lower(:keyword), lower(c.name)) > 0")
    Page<Category> findByNameContaining(String keyword, Pageable pageable);
}

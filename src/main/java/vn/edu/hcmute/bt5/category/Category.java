package vn.edu.hcmute.bt5.category;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private boolean active;

    protected Category() {
    }

    void update(CategoryForm form) {
        name = form.name().strip();
        imageUrl = form.imageUrl() == null ? "" : form.imageUrl().strip();
        active = form.active();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActive() {
        return active;
    }
}

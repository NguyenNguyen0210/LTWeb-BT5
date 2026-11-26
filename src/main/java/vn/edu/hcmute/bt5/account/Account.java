package vn.edu.hcmute.bt5.account;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Account {
    public enum Role { USER, ADMIN }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 128)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    protected Account() {
    }

    Account(String email, String fullName, String passwordHash, Role role) {
        update(email, fullName, passwordHash);
        this.role = role;
    }

    void update(String email, String fullName, String passwordHash) {
        this.email = email;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
    }

    long getVersion() {
        return version;
    }

    String getPasswordHash() {
        return passwordHash;
    }
}

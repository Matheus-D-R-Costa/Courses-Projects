package com.simple.bank_system.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.simple.bank_system.models.account.AccountModel;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountModel> accounts = new HashSet<>();

    public Set<AccountModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountModel> accounts) {
        this.accounts = accounts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

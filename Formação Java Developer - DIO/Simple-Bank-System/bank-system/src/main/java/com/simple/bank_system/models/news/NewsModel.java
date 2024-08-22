package com.simple.bank_system.models.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simple.bank_system.models.InformationModel;
import com.simple.bank_system.models.account.AccountModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "news")
public class NewsModel extends InformationModel {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "news")
    private List<AccountModel> accounts = new ArrayList<>();

    public List<AccountModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountModel> accounts) {
        this.accounts = accounts;
    }

}

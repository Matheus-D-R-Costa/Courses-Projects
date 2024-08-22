package com.simple.bank_system.models.account;

import com.simple.bank_system.models.card.CardModel;
import com.simple.bank_system.models.feature.FeatureModel;
import com.simple.bank_system.models.news.NewsModel;
import com.simple.bank_system.models.user.UserModel;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Accounts")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String number;

    @Column
    private String agency;

    @Column(precision = 13, scale = 2)
    private BigDecimal balance;

    @Column(precision = 13, scale = 2)
    private BigDecimal accountLimit;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserModel user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.EAGER)
    private List<CardModel> cards = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accountFeatures",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "featureId")
    )
    private List<FeatureModel> features = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accountNews",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "newId")
    )
    private List<NewsModel> news = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(BigDecimal accountLimit) {
        this.accountLimit = accountLimit;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<CardModel> getCards() {
        return cards;
    }

    public void setCards(List<CardModel> cards) {
        this.cards = cards;
    }

    public List<FeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureModel> features) {
        this.features = features;
    }

    public List<NewsModel> getNews() {
        return news;
    }

    public void setNews(List<NewsModel> news) {
        this.news = news;
    }

}

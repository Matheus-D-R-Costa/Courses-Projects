package com.simple.bank_system.service.user.impl;

import com.simple.bank_system.dtos.user.UserDto;
import com.simple.bank_system.models.account.AccountModel;
import com.simple.bank_system.models.card.CardModel;
import com.simple.bank_system.models.feature.FeatureModel;
import com.simple.bank_system.models.news.NewsModel;
import com.simple.bank_system.models.user.UserModel;
import com.simple.bank_system.repositories.user.UserRepository;
import com.simple.bank_system.service.exception.BusinessException;
import com.simple.bank_system.service.exception.IdNotFoundException;
import com.simple.bank_system.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserModel create(UserDto dto) {
        if (userRepository.existsByUsername(dto.username())) throw new BusinessException("This user already exists.");

        UserModel userModel = new UserModel();
        userModel.setUsername(dto.username());
        userModel.setAccounts(createAccounts(dto, userModel));

        return userRepository.save(userModel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> readAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserModel update(UserDto dto, Integer id) {
        Optional<UserModel> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) throw new IdNotFoundException(id);

        UserModel updatedUser = existingUser.get();
        updatedUser.setUsername(dto.username());
        updatedUser.setAccounts(createAccounts(dto, updatedUser));

        userRepository.save(updatedUser);

        return updatedUser;
    }

    @Transactional
    protected Set<AccountModel> createAccounts(UserDto dto, UserModel userModel) {

        Set<AccountModel> accountModels = dto.accounts().stream()
                .map(accountDto -> {
                    AccountModel accountModel = new AccountModel();

                    accountModel.setNumber(accountDto.number());
                    accountModel.setAgency(accountDto.agency());
                    accountModel.setBalance(accountDto.balance());
                    accountModel.setAccountLimit(accountDto.accountLimit());
                    accountModel.setUser(userModel);

                    //TODO: Stream para add cards
                    List<CardModel> cardModels = accountDto.cards().stream()
                            .map(cardDto -> {
                                CardModel cardModel = new CardModel();

                                cardModel.setNumber(cardDto.number());
                                cardModel.setCardLimit(cardDto.cardLimit());
                                cardModel.setAccount(accountModel);

                                return cardModel;
                            })
                            .collect(Collectors.toList());
                    accountModel.setCards(cardModels);

                    //TODO: Strean para add features
                    List<FeatureModel> featureModels = accountDto.features().stream()
                            .map(featureDto -> {
                                FeatureModel featureModel = new FeatureModel();

                                featureModel.setIcon(featureDto.icon());
                                featureModel.setDescription(featureDto.description());
                                featureModel.setAccounts(Stream.of(accountModel).toList()); // Talvez isso de uma merda.

                                return featureModel;
                            })
                            .collect(Collectors.toList());
                    accountModel.setFeatures(featureModels);

                    //TODO
                    List<NewsModel> newsModels = accountDto.news().stream()
                            .map(newsDto -> {
                                NewsModel newsModel = new NewsModel();

                                newsModel.setIcon(newsDto.icon());
                                newsModel.setDescription(newsDto.description());
                                newsModel.setAccounts(Stream.of(accountModel).toList()); // Talvez isso de uma merda.

                                return newsModel;
                            })
                            .collect(Collectors.toList());
                    accountModel.setNews(newsModels);

                    return accountModel;
                })
                .collect(Collectors.toSet());

        userModel.setAccounts(accountModels);
        return accountModels;
    }


    @Override
    @Transactional
    public void delete(Integer id) {
        if (!userRepository.existsById(id)) throw new IdNotFoundException(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel readById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));
    }
}
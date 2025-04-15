package com.xmall.user.domain.service.impl;

import com.xmall.user.domain.service.IUserExistenceChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserExistenceChecker implements IUserExistenceChecker {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String BLOOM_FILTER_KEY = "user:bloom:filter";
    private static final long EXPECTED_INSERTIONS = 100_000_000;
    private static final long[] MULTIPLIERS = {1, 31, 17, 13, 7, 5, 3};
    private static final double FALSE_POSITIVE_PROBABILITY = 0.001;
    private static final int NUM_HASH_FUNCTIONS = 7;

    @Override
    public boolean mightEmailExist(String email) {
        return mightContain("email:" + email);
    }

    @Override
    public boolean mightPhoneExist(String phone) {
        return mightContain("phone:" + phone);
    }

    @Override
    public boolean mightUserAccountExist(String userAccount) {
        return mightContain("userAccount" + userAccount);
    }

    @Override
    public void addEmail(String email) {
        add("email:" + email);
    }

    @Override
    public void addPhone(String phone) {
        add("phone:" + phone);
    }

    @Override
    public void addUserAccount(String userAccount) {
        add("userAccount:" + userAccount);
    }

    private void add(String value) {
        List<Long> indexes = getIndexes(value);
        indexes.forEach(index -> {
            String key = BLOOM_FILTER_KEY + ":" + (index / 1_000_000);
            redisTemplate.opsForValue().setBit(key, index % 1_000_000, true);
            redisTemplate.expire(key, 30, TimeUnit.DAYS);
        });
    }

    private boolean mightContain(String value) {
        List<Long> indexes = getIndexes(value);
        return indexes.stream().allMatch(index -> {
            String key = BLOOM_FILTER_KEY + ":" + (index / 1_000_000);
            return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, index % 1_000_000));
        });
    }

    private List<Long> getIndexes(String value) {
        int hash = value.hashCode();
        return Arrays.stream(MULTIPLIERS)
                .map(m -> Math.abs(hash * m % EXPECTED_INSERTIONS))
                .boxed()
                .collect(Collectors.toList());
    }
} 
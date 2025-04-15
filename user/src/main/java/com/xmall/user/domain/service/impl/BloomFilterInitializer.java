package com.xmall.user.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.user.domain.entity.UserInfoEntity;
import com.xmall.user.domain.service.IUserExistenceChecker;
import com.xmall.user.infrastructure.persistence.JpaUserInfoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BloomFilterInitializer {
    IUserExistenceChecker userExistenceChecker;
    JpaUserInfoRepository userInfoRepository;
    AtomicLong lastProcessedId = new AtomicLong(0);
    private static final int BATCH_SIZE = 1000;
    private static final int THREAD_POOL_SIZE = 10;
    private static final int ID_RANGE_SIZE = 100_000; // Process 100k IDs per thread
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("Application started, checking Bloom Filter initialization");
        if (lastProcessedId.get() == 0) {
            log.info("Bloom Filter not initialized, starting initialization");
            initializeBloomFilter();
        } else {
            log.info("Bloom Filter already initialized, last processed ID: {}", lastProcessedId.get());
        }
    }

    @Scheduled(fixedDelay = 3600000) // Run every hour
    @Transactional(readOnly = true)
    public void syncBloomFilter() {
        log.info("Starting Bloom Filter synchronization");
        long currentLastId = lastProcessedId.get();
        
        // Get max ID to process
        Long maxId = userInfoRepository.findMaxId()
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.FAILED;
                    errorCode.setMessage("No records found");
                    return new AppException(errorCode);
                });
            
        // Calculate number of ID ranges
        long totalRanges = (maxId - currentLastId + ID_RANGE_SIZE - 1) / ID_RANGE_SIZE;
        long rangesPerThread = (totalRanges + THREAD_POOL_SIZE - 1) / THREAD_POOL_SIZE;
        
        // Create tasks for each thread
        CompletableFuture<?>[] tasks = new CompletableFuture[THREAD_POOL_SIZE];
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            final long threadStartRange = i * rangesPerThread;
            final long threadEndRange = Math.min((i + 1) * rangesPerThread, totalRanges);
            
            tasks[i] = CompletableFuture.runAsync(() -> {
                processIdRanges(threadStartRange, threadEndRange, currentLastId);
            }, executorService);
        }
        
        // Wait for all tasks to complete
        CompletableFuture.allOf(tasks).join();
        
        // Update last processed ID
        lastProcessedId.set(maxId);
        log.info("Bloom Filter synchronization completed. Last processed ID: {}", maxId);
    }

    private void processIdRanges(long startRange, long endRange, long baseId) {
        for (long range = startRange; range < endRange; range++) {
            long startId = baseId + (range * ID_RANGE_SIZE);
            long endId = Math.min(startId + ID_RANGE_SIZE, baseId + ((range + 1) * ID_RANGE_SIZE));
            
            processIdRange(startId, endId);
        }
    }

    private void processIdRange(long startId, long endId) {
        int page = 0;
        while (true) {
            var users = userInfoRepository.findByIdRange(
                startId, 
                endId, 
                PageRequest.of(page, BATCH_SIZE)
            );
            
            if (users.isEmpty()) {
                break;
            }
            
            users.getContent().forEach(userInfo -> {
                if (userInfo.getEmail() != null) {
                    userExistenceChecker.addEmail(userInfo.getEmail());
                }
                if (userInfo.getPhone() != null) {
                    userExistenceChecker.addPhone(userInfo.getPhone());
                }
            });
            
            log.info("Thread {} processed batch {}-{} (page {})", 
                Thread.currentThread().getId(), startId, endId, page);
            
            if (!users.hasNext()) {
                break;
            }
            ++page;
        }
    }

    @Transactional(readOnly = true)
    public void initializeBloomFilter() {
        log.info("Starting initial Bloom Filter population");
        Long maxId = userInfoRepository.findMaxId()
            .orElseThrow(() -> {
                ErrorCode errorCode = ErrorCode.FAILED;
                errorCode.setMessage("No records found");
                return new AppException(errorCode);
            });
            
        long totalRanges = (maxId + ID_RANGE_SIZE - 1) / ID_RANGE_SIZE;
        long rangesPerThread = (totalRanges + THREAD_POOL_SIZE - 1) / THREAD_POOL_SIZE;
        
        CompletableFuture<?>[] tasks = new CompletableFuture[THREAD_POOL_SIZE];
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            final long threadStartRange = i * rangesPerThread;
            final long threadEndRange = Math.min((i + 1) * rangesPerThread, totalRanges);
            
            tasks[i] = CompletableFuture.runAsync(() -> {
                processIdRanges(threadStartRange, threadEndRange, 0L);
            }, executorService);
        }
        
        CompletableFuture.allOf(tasks).join();
        log.info("Bloom Filter initialization completed. Max ID: {}", maxId);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
} 
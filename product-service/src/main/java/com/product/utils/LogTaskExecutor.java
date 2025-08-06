package com.product.utils;

import org.springframework.stereotype.Component;
import com.product.entity.UserLog;
import com.product.repository.UserLogRepository;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component  // So Spring can autowire this
public class LogTaskExecutor implements Runnable {

    // Queue to hold logs temporarily before saving
    private final BlockingQueue<UserLog> logQueue = new LinkedBlockingQueue<>();

    // JPA repository to save logs
    private final UserLogRepository userLogRepository;

    // Constructor: Runs once on startup
    public LogTaskExecutor(UserLogRepository userLogRepository) {
        this.userLogRepository = userLogRepository;

        // Start background thread
        Thread thread = new Thread(this);  // 'this' refers to run() method
        thread.setDaemon(true);            // Background thread, auto-dies with app
        thread.start();
    }

    // Called from controller — adds log to the queue
    public void offerLog(UserLog userLog) {
        logQueue.offer(userLog);  // Adds to the end of queue
    }

    // Continuously running background thread
    @Override
    public void run() {
        while (true) {
            try {
                // Waits until a log is available
                UserLog log = logQueue.take();

                // Saves to database using JPA repository
                userLogRepository.save(log);
            } catch (Exception e) {
                System.err.println("Error saving user log: " + e.getMessage());
            }
        }
    }
}

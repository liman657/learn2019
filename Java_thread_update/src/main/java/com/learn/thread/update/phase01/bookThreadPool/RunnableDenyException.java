package com.learn.thread.update.phase01.bookThreadPool;

/**
 * autor:liman
 * createtime:2020/6/24
 * comment:
 */
public class RunnableDenyException extends RuntimeException {

    public RunnableDenyException(String message) {
        super(message);
    }
}

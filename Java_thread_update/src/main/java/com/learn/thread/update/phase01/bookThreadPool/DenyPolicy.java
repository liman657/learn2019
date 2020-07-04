package com.learn.thread.update.phase01.bookThreadPool;

/**
 * autor:liman
 * createtime:2020/6/24
 * comment:拒绝策略
 */
@FunctionalInterface
public interface DenyPolicy {

    void reject(Runnable runnable,ThreadPool threadPool);

    class DiscardDenyPolicy implements DenyPolicy{

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {

        }
    }

    class AbortDenyPolicy implements DenyPolicy{

        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            throw new RunnableDenyException("The runnable "+runnable+ " will be abort");

        }
    }

}

package com.learn.typeOfLock.SynchronizedDemo;

/**
 * autor:liman
 * createtime:2019/6/30
 * comment:
 */
public class SetPrivateNum {

    private static int num = 0;

    public synchronized static void add(String username) {
        try {
            if (username.equals("userA")) {
                System.out.println("userA start set private num");
                num = 100;
                Thread.sleep(2000);
                System.out.println("userA set private num over");

            } else {
                System.out.println("other user start private num");
                num = 200;
                Thread.sleep(2000);
                System.out.println("other user set private num over");
            }
            System.out.println("over !!! user " + username + " set num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addNum(String username){
        synchronized (this){
            try {
                if (username.equals("userA")) {
                    System.out.println("userA start set private num");
                    num = 100;
                    System.out.println("userA set private num over");
                    Thread.sleep(2000);

                } else {
                    System.out.println("other user start private num");
                    num = 200;
                    Thread.sleep(2000);
                    System.out.println("other user set private num over");
                }
                System.out.println("over !!! user " + username + " set num = " + num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadA extends Thread {
    private SetPrivateNum setPrivateNum;

    public ThreadA(SetPrivateNum setPrivateNum) {
        this.setPrivateNum = setPrivateNum;
    }

    @Override
    public void run() {
        setPrivateNum.add("userA");
    }
}

class ThreadB extends Thread {
    private SetPrivateNum setPrivateNum;

    public ThreadB(SetPrivateNum setPrivateNum) {
        this.setPrivateNum = setPrivateNum;
    }

    @Override
    public void run() {
        setPrivateNum.add("otherUser");
    }
}

class ThreadC extends Thread{
    private SetPrivateNum setPrivateNum;
    public ThreadC(SetPrivateNum setPrivateNum){
        this.setPrivateNum = setPrivateNum;
    }

    @Override
    public void run() {
        setPrivateNum.addNum("userA");
    }
}
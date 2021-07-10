package ru.geekbrains.jc3.hw4.les4;

//    1. Создать три потока,
//    каждый из которых выводит
//    определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
//    Используйте wait/notify/notifyAll.

public class HomeWorkFour {

    static Object object = new Object();
    static volatile char aChar = 'A';

    public static void main(String[] args) {
        System.out.println();
        new Thread(new WaitNotifyClass('A', 'B')).start();
        new Thread(new WaitNotifyClass('B', 'C')).start();
        new Thread(new WaitNotifyClass('C', 'A')).start();
    }


    static class WaitNotifyClass implements Runnable {
        private char thisChar;
        private char nextChar;

        public WaitNotifyClass(char thisChar, char nextChar) {
            this.thisChar = thisChar;
            this.nextChar = nextChar;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (object) {
                    try {
                        while (aChar != thisChar)
                            object.wait();
                        System.out.print(thisChar);
                        aChar = nextChar;
                        object.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
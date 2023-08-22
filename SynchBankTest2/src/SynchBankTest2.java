/**
 * 主程序入口
 */
public class SynchBankTest2 {

    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;

    public static void main(String[] args) {
        Bank bank = new Bank(NACCOUNTS, INITIAL_BALANCE);   // 只有一个银行
        for (int i = 0; i < NACCOUNTS; i ++ ) { // 该银行的用户进行转账行为
            Runnable r = new TransferRunnable(bank, i, INITIAL_BALANCE);
            Thread t = new Thread(r);
            t.start();
        }
    }
}

/**
 * Thread.sleep((int)(DELAY * Math.random())); //做一个随机响应时间
 * 正是因为 1.有这个随机响应时间，2.各个进程存取同一片空间， 所以并发访问导致该空间存取不同步而出错。
 * 解决方案：加Bank类的transfer加锁
 * 1.显式锁 ReentrantLock
 * 2.隐式锁 synchronized 关键字
 * 该项目使用了隐式锁
 */
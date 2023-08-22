import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 银行类，包含accounts
 */
public class Bank {

    private  double accounts[];
    private Lock lock;
    private Condition sufficientFunds;  // 条件对象
    /**
     * 构造银行类
     * @param n 用户数量
     * @param initialBalance 初始余额
     */
    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i ++ ) {
            accounts[i] = initialBalance;
        }
        lock = new ReentrantLock();
        sufficientFunds = lock.newCondition();

    }

    /**
     * 账户之间交易
     * @param from 出账人
     * @param to 入账人
     * @param amount 钱数
     */
    public void transfer(int from, int to ,double amount) throws InterruptedException {
        lock.lock();    // 上锁
        try{
            while (accounts[from] < amount)
                sufficientFunds.await();    // 如果该线程不满足条件，则等待条件满足（其他线程会使得条件满足）
            accounts[from] -= amount;
            accounts[to] += amount;
            System.out.println(Thread.currentThread()+"  :  " + " from " + from + " to " + to + " transfer " + amount + "    总钱数:" + getTotalBalance());
            sufficientFunds.signal();   // 发送信号，刷新一下条件
        } finally {
            lock.unlock();  // 解锁
        }

    }

    /**
     * @return 银行所有用户数
     */
    public int size() {
       return accounts.length;
    }

    /**
     *
     * @return 银行总钱数（所有客户的钱数）
     */
    public double getTotalBalance() {
        double sum = 0;
        for (int i = 0 ; i < accounts.length; i ++ ) {
            sum += accounts[i];
        }
        return sum;
    }

}

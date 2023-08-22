import java.awt.color.ICC_ColorSpace;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 银行类，包含accounts
 */
public class Bank {
    private  double accounts[];

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
    }

    /**
     * 账户之间交易
     * @param from 出账人
     * @param to 入账人
     * @param amount 钱数
     */
    public void transfer(int from, int to ,double amount) {
        if (accounts[from] < amount) return;
        accounts[from] -= amount;
        accounts[to] += amount;
        System.out.println(Thread.currentThread()+"  :  " + " from " + from + " to " + to + " transfer " + amount + "    总钱数:" + getTotalBalance());
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

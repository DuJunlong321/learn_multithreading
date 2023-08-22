/**
 * A runnable that transfers money from an account to other accounts in a bank.
 */
public class TransferRunnable implements Runnable{

    private Bank bank;
    private int fromAccount;
    private double maxAmount;
    private int DELAY = 10;

    /**
     *
     * @param b 银行
     * @param from 出账人
     * @param max 每次转账的最大金额
     */
    public TransferRunnable(Bank b, int from, double max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    @Override
    public void run() {
        try {
            while(true) {
                int toAccount = (int)(bank.size() * Math.random()); // 入账人
                double amount = maxAmount * Math.random();  // 转账金额
                bank.transfer(fromAccount, toAccount, amount);  // 银行转账
                Thread.sleep((int)(DELAY * Math.random())); //做一个随机响应时间
            }
        } catch (InterruptedException e) {

        }
    }
}

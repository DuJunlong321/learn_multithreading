import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 程序入口
 */
public class BounceThread {
    public static void main(String[] args) {
        // 事件分发线程 通过EventQueue.invokeLater在事件分发线程上执行一个匿名内部类的 run 方法
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BounceFrame bounceFrame = new BounceFrame();
                bounceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                bounceFrame.setVisible(true);
            }
        });

    }
}

/**
 * 窗口:拥有小球运动panel和 button panel
 */
class BounceFrame extends JFrame {
    private int DEFAULT_WIDTH = 450;
    private int DEFAULT_HEIGHT = 450;
    private int STEPS = 1000;
    private int DELAYS = 3;
    private BallComponent comp;

    // 构造函数
    public BounceFrame() {
        // 窗口初始化
        setTitle("BounceFrame");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // 添加小球运动面板
        comp = new BallComponent();
        add(comp, BorderLayout.CENTER);

        // 添加按钮面板
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Start", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBall();
            }
        });
        addButton(buttonPanel, "Close", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(buttonPanel, BorderLayout.SOUTH);

    }
    // 向容器中添加按钮
    public void addButton(Container c, String s, ActionListener listener) {
        JButton jButton = new JButton(s);
        c.add(jButton);
        jButton.addActionListener(listener);
    }
    // 添加一个小球到小球运动面板，并开始一个线程
    public void addBall() {
        Ball b = new Ball();    // new小球
        comp.add(b);    // 向面板上添加小球
        BallRunnable r = new BallRunnable(b, comp);
        Thread t = new Thread(r);
        t.start();

    }
}

/**
 * 弹跳线程
 */
class BallRunnable implements Runnable{

    private Ball ball;
    private Component component;
    public static final int STEPS = 1000;
    public static final int DELAY = 3;

    // 构造方法
    public BallRunnable(Ball aBall, Component aComponent) {
        ball = aBall;
        component = aComponent;
    }

    @Override
    public void run() {
        try{
            for (int i = 1; i <= STEPS; i ++ ) {
                ball.move(component.getBounds());   // 移动
                component.repaint();    // 小球移动之后重新绘制面板
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException e) {

        }
    }

}
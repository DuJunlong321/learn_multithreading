import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主程序入口
 */
public class Bounce {
    public static void main(String[] args) {
        // 通过EventQueue.invokeLater在事件分发线程上执行一个匿名内部类的 run 方法
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new BounceFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}


/**
 * 自定义窗口类
 */
class BounceFrame extends JFrame{
    private BallComponent comp; // 绘制小球的绘图面板实例
    private static final int  DEFAULT_WIDTH = 450;  // 窗口宽
    private static final int  DEFAULT_HEIGHT = 450; // 窗口高
    private static final int  STEPS = 1000;  // 弹跳次数
    private static final int  DELAY = 3;  // 每次绘制延迟时间
    // 构造函数
    public BounceFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle("Bounce");


        comp = new BallComponent();
        add(comp, BorderLayout.CENTER); // 将一个组件（comp）添加到容器中的特定位置（BorderLayout.CENTER）



        JPanel buttonPanel = new JPanel();  // 创建一个按钮面板：在上面进行点击
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
    // 添加按钮
    public void addButton(Container c, String title, ActionListener listener) {
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }
    // 添加一个小球到组件,并使其在面板上弹跳1000次
    public void addBall() {
        try{
            Ball ball = new Ball();
            comp.add(ball);

            for (int i = 1; i <= STEPS; i ++) {
                ball.move(comp.getBounds());
                comp.paint(comp.getGraphics());
                Thread.sleep(DELAY);    // 暂停当前线程，这里是Event Dispatch Thread，EDT
            }

        } catch (InterruptedException e) {

        }
    }
}


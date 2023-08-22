// 抽象窗口工具包
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 小球运动面板：在上面进行绘制小球
 */
public class BallComponent extends JPanel {

    private ArrayList<Ball> balls = new ArrayList<Ball>();
    /**
     * 向组件中添加一个球
     */
    public void add(Ball b) {
        balls.add(b);
    }

    /**
     * 绘制组件
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 擦除背景
        Graphics2D g2 = (Graphics2D) g;  // 获取一个 Graphics2D 对象 g2，它用于绘制2D图形
        for (Ball b:balls) {
            g2.fill(b.getShape()); // 使用 g2.fill() 方法来填充每个小球的形状
        }
    }
}

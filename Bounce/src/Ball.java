import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * 球：移动和弹跳
 */
public class Ball {

    private static final int XSIZE = 15;
    private static final int YSIZE = 15;
    private double x = 0 ;
    private double y = 0 ;
    private double dx = 1 ;
    private double dy = 1 ;
    /**
     * 移动到下一个位置，反转方向
     */
    public void move(Rectangle2D bounds) {
        x += dx;
        y += dy;
        if (x < bounds.getMinX()) {
            x = bounds.getMinX();
            dx = -dx;
        }
        // bounds.getMaxX() 是用于获取给定矩形边界的右边界（最大 X 坐标）的方法调用。这里的 bounds 是一个 Rectangle2D 类型的对象
        if (x + XSIZE >= bounds.getMaxX()) {
            x = bounds.getMaxX() -XSIZE;
            dx = -dx;
        }
        if (y < bounds.getMinY()) {
            y = bounds.getMinY();
            dy = -dy;
        }
        if (y + YSIZE >= bounds.getMaxY()) {
            y = bounds.getMaxY() - YSIZE;
            dy = -dy;
        }
    }

    /**
     * 获取当前位置的形状
     */
    public Ellipse2D getShape() {
        // 创建一个椭圆形状的操作。在这里，它创建了一个新的Ellipse2D.Double对象，表示一个椭圆形状，其中 (x, y) 是椭圆的左上角坐标，XSIZE 是椭圆的宽度，YSIZE 是椭圆的高度
        return new Ellipse2D.Double(x,y,XSIZE, YSIZE);
    }

}

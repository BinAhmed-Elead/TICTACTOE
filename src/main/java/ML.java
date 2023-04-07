import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ML implements MouseListener, MouseMotionListener {
    public Point position;
    public int button;
    public boolean dragging;
    public boolean clicked;
    public Point clickPosition;
    public ML(){
        position = new Point(-1,-1);
        dragging = false;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        button = e.getButton();
        clickPosition = e.getPoint();
        clicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        button = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragging = false;
        button = e.getButton();
        clickPosition = e.getPoint();
        clicked = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        position = new Point(-1,-1);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragging = true;
        position = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        position = e.getPoint();
    }
}

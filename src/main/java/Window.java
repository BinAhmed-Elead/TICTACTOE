import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window(){
        setSize(800,800);//Setting The Size Of The Frame
        setLocationRelativeTo(null);//Placing The Frame in The Middle Of The Screen
        setLayout(null);//Making Organising Components Manual
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Stopping The Program After Closing The Window
        setTitle("TicTacToe");//Setting The title of the Frame
        setVisible(true);//Making The Frame Appears in the screen
        Thread gameplayThread = new Thread(()->
        add(new GamePlay(this))
        );
//        gameplayThread.start();//Running The Gameplay in a Thread
        Thread mainmenuThread = new Thread(()->
                add(new MainMenu(this))
        );
        mainmenuThread.start();
    }
    public void paint(Graphics g){
        super.paint(g);
//        drawX(100,100,20,g);
    }

    public static void main(String[] args) {
        new Window();
    }
    public static void drawX(int x,int y,int size,Graphics g){
//        Graphics2D g2d = (Graphics2D) g;
//        Rectangle leftToRight = new Rectangle(x, y, size, size*4);
//        Rectangle rightToLeft = new Rectangle(x+leftToRight.width/2-(size*2), y+leftToRight.height/2-(size/2), size*4, size);
//        g2d.translate(leftToRight.x+(leftToRight.width/2),leftToRight.y+(leftToRight.height/2));
//        g2d.translate(leftToRight.x + leftToRight.width + size, rightToLeft.y - size*8);
//        g2d.rotate(Math.toRadians(45));
//        g2d.fill(leftToRight);
//        g2d.fill(rightToLeft);
////        g2d.drawLine(0,0,100,100);
    }
    public void sleep(int ms){
        try{Thread.sleep(ms);}catch (Exception e){}
    }
}

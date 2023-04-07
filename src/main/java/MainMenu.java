import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu extends JPanel implements ActionListener {
    Insets insets;//JFrame insets keeping Track of necessary offsets of the Frame
    Timer timer;//Generate an event Every x Time invoking >>actionPreformed(ActionEvent e)<<
    JLabel welcomingLabel;
    String welcomingLabelString;
    JLabel startingGameLabel;
    String startingGameLabelString;
    JLabel exitGameLabel;
    String exitGameLabelString;
    ML mouseListener;
    ArrayList<JLabel> labels;
    JFrame parent;
    public MainMenu(JFrame parent){
        this.parent = parent;
        insets = parent.getInsets();//Storing JFrame insets
        setSize(parent.getSize());//Setting The Size Of The JPanel >> This is not the optimal way to do so.
        setLayout(null);//LayOut is Responsible for Organising The Added Components. Making it NULL means You Want Manual Control Over it
        labels = new ArrayList<>();
        timer = new Timer(10,this);//invoking event every 10ms
        timer.start();//starts invoking events
        setBackground(Color.gray);
        welcomingLabelString = "WELCOME TO TICTACTOE GAME!";
        welcomingLabel = new JLabel(welcomingLabelString);
        add(welcomingLabel);
        startingGameLabelString = "Play";
        startingGameLabel = new JLabel(startingGameLabelString);
        add(startingGameLabel);
        exitGameLabelString = "Exit";
        exitGameLabel = new JLabel(exitGameLabelString);
        add(exitGameLabel);
        mouseListener = new ML();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        labels.add(startingGameLabel);
        labels.add(exitGameLabel);
    }
    public void paint(Graphics g){
        super.paint(g);
        Font font = new Font("Arial", 0, 36);//creating a Font
        g.setColor(Color.white);
        g.setFont(font);//Setting The Font
        welcomingLabel.setFont(font);//Setting The Font
        int welcomingWidth = g.getFontMetrics().stringWidth(welcomingLabelString);//Calculating How Long is This String pixelWise
        welcomingLabel.setBounds(getWidth()/2-welcomingWidth/2,90-font.getSize(),//Setting location x,y
                welcomingWidth,font.getSize());//Setting The Size
        g.drawLine(110,90,110+welcomingWidth,90);//Drawing A Line Under The WelcomingLabel
        font = new Font("Arial",0,60);//creating a Font
        g.setFont(font);
        int exitWidth = g.getFontMetrics().stringWidth(exitGameLabelString);//Calculating How Long is This String pixelWise
        int startWidth = g.getFontMetrics().stringWidth(startingGameLabelString);//Calculating How Long is This String pixelWise
        startingGameLabel.setFont(font);//setting The font;
        exitGameLabel.setFont(font);//setting The font;
        startingGameLabel.setSize(startWidth,font.getSize());
        exitGameLabel.setSize(exitWidth,font.getSize());
        int widthsSums = widthSums(labels);
        for(int i = 0 ; i < labels.size();i++){
            JLabel label = labels.get(i);
            if(inRange(mouseListener.position,label.getLocation(),label.getWidth(),label.getHeight()) ){
                if(label == exitGameLabel){
                    if(mouseListener.clicked)
                        System.exit(0);
                    else
                        label.setForeground(Color.red);
                }else {
                    if(mouseListener.clicked){
                        parent.remove(this);
                        parent.add(new GamePlay(parent));
                    }
                    else
                        label.setForeground(Color.green);
                }
            }else{
                label.setForeground(Color.white);
            }
            label.setLocation(110 + welcomingWidth/2 - startWidth/2,getHeight()/2 + font.getSize()*i - widthsSums/2);
        }

    }
    public int widthSums(ArrayList<JLabel> labels){
        int sum = 0;
        for(JLabel label : labels){
            sum+=label.getWidth();
        }
        return sum;
    }
    public boolean inRange(Point a , Point b , int width,int height){
        if(a.x >= b.x && a.x <= b.x + width)
            if(a.y >= b.y && a.y <= b.y + height)
                return true;
            else
                return false;
        else
            return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

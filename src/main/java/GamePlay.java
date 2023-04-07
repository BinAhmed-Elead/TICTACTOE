import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePlay extends JPanel implements ActionListener{
    JPanel[][] panels;//GRID of Placements (GAMEPLAY GRID)
    ML mouseListener;//Class To Handel MouseEvents
    Timer timer;//Generate an event Every x Time invoking >>actionPreformed(ActionEvent e)<<
    Insets insets;//JFrame insets keeping Track of necessary offsets of the Frame
    boolean isXTurn = true;//Keeping Track Of Whom Turn it is
    boolean running = true;//Keeping Track Of Game Status
    boolean AI = !true;//Does The AI Play or 1v1
    ArrayList<JPanel> xPlacements;//Keeping Track Of x Player Placements
    ArrayList<JPanel> oPlacements;//Keeping Track Of o Player Placements
    Color xColor = Color.red;
    Color oColor = Color.green;
    public GamePlay(JFrame parent){
        insets = parent.getInsets();//Storing JFrame insets
        setSize(parent.getSize());//Setting The Size Of The JPanel >> This is not the optimal way to do so.
        setLayout(null);//LayOut is Responsible for Organising The Added Components. Making it NULL means You Want Manual Control Over it
        xPlacements = new ArrayList<>();//initializing
        oPlacements = new ArrayList<>();//initializing
        panels = new JPanel[3][3];//initializing

        int xOffset = 100 - insets.left;//Offset Away From Left Border (For GRID placement)
        int yOffset = 150;//Offset Away From Top Border (For GRID placement)
        for(int i = 0 ; i < 3;i++){//Place The Panels in required Positions
            for(int j = 0 ; j < 3; j++){
                panels[i][j] = new JPanel();
                panels[i][j].setBounds(200*(i)+(5*i)+xOffset,200*(j)+(5*j)+yOffset,200,200);
                panels[i][j].setBackground(Color.black);
                add(panels[i][j]);
            }
        }
        setBackground(Color.gray);//BackGround Color of The JPanel
        mouseListener = new ML();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        timer = new Timer(10,this);
        timer.start();
//        new Thread(()->{while(true){sleep(2000); isXTurn = !isXTurn;}}).start();//A Thread To Switch Turns for Debugging
    }
    public void paint(Graphics g){
        super.paint(g);//invoking The Super Paint to Color The Background With Required Color
        char turn;//
//        System.out.println((mouseListener.clicked?"Click ,pos:"+mouseListener.clickPosition:"NotClicked"));
        if(isXTurn){//Store Character Value of Turn For The UI
            turn = 'X';
        }else{
            turn = 'O';
        }
        boolean xWon = false;
        boolean oWon = false;
        if(hasWon(xPlacements)){
            xWon = true;
        }else if(hasWon(oPlacements)){
            oWon = true;
        }
        int xPlacementSize = xPlacements.size();// the size of X placements over the Gameplay Grid
        int oPlacementSize = oPlacements.size();// the size of o placements over the Gameplay Grid
        if(AI&&!isXTurn){
                int rowRand;//generate Random Row
                int colRand;//generate Random Column
                JPanel random;//Storing The Chosen placement (GRID is 2D array of Panels)
            while(true){
                //when random is generated it's in form of fraction ex. 0.9421 so x10 is 9.421 cast to integer 9
                // then modular 3 because 3 is the size of the GRID
                rowRand = (int)(Math.random()*10)%3;
                colRand = (int)(Math.random()*10)%3;
                random = panels[rowRand][colRand];
                if(!xPlacements.contains(random)&& !oPlacements.contains(random)){//making sure the random Position is not containing any placement by X or O itself
                    System.out.println("true");
                    break;
                }else if(xPlacementSize + oPlacementSize == 9){// the gameplay GRID is 3x3 so if the sum of the positions placed equals 9 that means all positions were taken
                    /*>>DRAW<<*///making sure the AI doesn't stay in an infinite loop trying to generate placement that wasn't taken when all placements were TAKEN!
                    return;
                }
            }
            System.out.println(rowRand+" ,"+colRand);
            oPlacements.add(random);//Play in the generated Position
            isXTurn = !isXTurn;//switch turns
            return;
        }
        String message;
        if(xWon){
            message = "X Won!";
            System.out.println("Row: "+winningRow);
            System.out.println("Col: "+winningCol);
            if(winningCol > 0){
                drawWinningBar(panels[0][winningCol],true,g);//vertical
            }else if(winningRow > 0){
                drawWinningBar(panels[winningRow][0],false,g);//horizontal
            }
            running = false;
        }else if(oWon){
            message = "O Won!";
            running = false;
        }else if(xPlacementSize + oPlacementSize == 9){
            message = "Draw!";
            running = false;
        }else{
            message = "This is "+turn+" Turn";
        }

        Font font = new Font("",1,60);
        g.setFont(font);
        g.drawString(message,panels[0][0].getX()+panels[0][0].getWidth()/2,100);
        g.setFont(new Font("",1,152)); // change the font for the massage
        if(mouseListener.position == new Point(-1,-1))//in case the mouse was off the frame return avoiding unnecessary computations
            return;
        //looping over the 3x3 gameplay GRID
        for(int i = 0 ; i < panels.length;i++){
            for(int j = 0 ; j < panels[i].length;j++){
                Point pos;
                Dimension size = panels[i][j].getSize();//invoking the size of the panel
                for(int k = 0; k < xPlacementSize; k++ ){//Looping Over x Placements and draw Them EveryFrame
                    g.setColor(xColor);//Setting The Color as The xColor
                    g.drawString("X",xPlacements.get(k).getX()+(152/3),xPlacements.get(k).getY()+152);//Draw 'X' in The Position
                }for(int k = 0; k < oPlacementSize; k++ ){//Looping Over o Placements and draw Them EveryFrame
                    g.setColor(oColor);//Setting The Color as The oColor
                    g.drawString("O",oPlacements.get(k).getX()+(152/3)-10,oPlacements.get(k).getY()+152);//Draw 'O' in The Position
                }
                if(inRange(mouseListener.position,pos = panels[i][j].getLocation(),size.width)){//if The Mouse is Hovering Over The Panel
                    g.setColor(new Color(200,30,200,100));//Setting The Color Of Selected Panel
                    g.fillRect(pos.x,pos.y,200,200);//Drawing a Rect Over The Selected Panel >> Representing The Change Of Color When Hovering Over The Panel
                    if(xPlacements.contains(panels[i][j])||oPlacements.contains(panels[i][j])){//if The Panel has X or O Placed Do Not draw X or O Along With Color Change
                        mouseListener.clicked = false;//if mouse is click and this method returns , clicked is never rested causing problems
                        return;
                    }
                    g.setColor((isXTurn?xColor:oColor));//if its X turn Set xColor else oColor
                    if(running)//if The Game is running Draw X or O Over The Selected Panel
                        g.drawString((isXTurn?"X":"O"),pos.x+(152/3)-(!isXTurn? 10:0),pos.y+152);

                    if(mouseListener.clicked && running){//Handel Mouse Click
                        if(isXTurn){
                            if(!oPlacements.contains((panels[i][j]))&& !xPlacements.contains(panels[i][j])){//Making Sure That The Click Panel Doesn't Have X or O Placed
                                xPlacements.add(panels[i][j]);//Adding a Placement for X
                                System.out.println("X Has Won: "+hasWon(xPlacements));
                                System.out.println(Arrays.toString(isAboutToWin(xPlacements)));
                                isXTurn = !isXTurn;//Switching Turns
                            }
                        }else{
                            if(!xPlacements.contains(panels[i][j])&& !oPlacements.contains(panels[i][j])){//Making Sure That The Click Panel Doesn't Have X or O Placed
                                oPlacements.add(panels[i][j]);//Adding a Placement for O
                                System.out.println("O Has Won: "+hasWon(oPlacements));
                                System.out.println(Arrays.toString(isAboutToWin(oPlacements)));
                                isXTurn = !isXTurn;//Switching Turns
                            }
                        }
                    }
                }
            }
        }
        mouseListener.clicked = false; //Click Has Been Handled
    }
    private void drawWinningBar(JPanel panel,boolean vertical,Graphics g){
        int width = panel.getWidth();
        Point panelPos = panel.getLocation();
        g.setColor(Color.white);
        if(vertical)
            g.fillRect(panelPos.x + width/2,panelPos.y,width/16, 615);
        else
            g.fillRect(panelPos.x ,panelPos.y + width/2,615,width/16 );

    }
    int winningRow,winningCol;
    public boolean hasWon(ArrayList<JPanel> placements){
        winningRow = 0;
        winningCol = 0;
        for(int i = 0 ; i < 3;i++){
            boolean hasWon = true;
            for(int j = 0 ; j < 3;j++){
                if(!placements.contains(panels[i][j])){
                    hasWon = false;
                }
            }//Vertical Wins
            winningCol = i;
            if(hasWon){
//                System.out.println(row);
                return true;
            }
        }
        winningCol = 0;
        for(int i = 0 ; i < 3;i++){
                boolean hasWon = true;
                for(int j = 0 ; j < 3;j++){
                    if(!placements.contains(panels[j][i])){
                        hasWon = false;
                    }
                }//Horizontal Wins
                winningRow = i;
                if(hasWon){
//                System.out.println(col);
                    return true;
                }
        }
        winningRow = 0;
        //diagonal left to right
        boolean hasWon = true;
        for(int i = 0 ; i < 3;i++){
            if(!placements.contains(panels[i][i])){
                hasWon = false;
            }
        }
        winningRow = -1;
        if(hasWon){
            return true;
        }
        //diagonal right to left
        hasWon = true;
        for(int i = 0 ; i < 3 ;i++){
            if(!placements.contains(panels[2-i][i])){
                hasWon = false;
            }
        }
        winningCol = -1;
        if(hasWon){
            return true;
        }
        return false;
    }
    public int[] isAboutToWin(ArrayList<JPanel> placements){
        int row = -1,col = -1;

        for(int i = 0 ; i < 3 ;i++){
            int count = 0;
            for(int j = 0 ; j < 3;j++){
                if(!placements.contains(panels[i][j]))
                    count++;
            }
            if(count == 1)
                row = i;
        }
        for(int i = 0 ; i < 3 ;i++){
            int count = 0;
            for(int j = 0 ; j < 3;j++){
                if(!placements.contains(panels[j][i]))
                    count++;
            }
            if(count == 1)
                col = i;
        }
        return new int[]{row,col};
    }
    public boolean inRange(Point a , Point b , int size){
        if(a.x >= b.x && a.x <= b.x + size)
            if(a.y >= b.y && a.y <= b.y + size)
                return true;
            else
                return false;
        else
            return false;
    }
    public void sleep(int ms){
        try{Thread.sleep(ms);}catch (Exception e){}
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

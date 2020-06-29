import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;
import java.awt.event.*;


public class DynamicButton extends JFrame implements ActionListener{
    //public static int clickedAmt =0;
    public int[][] btnClickArr = new int[17][17];
    public JFrame frame;
    //public static String finaltxt = "tie";
    JLabel lbTest= new JLabel("Score");

    public DynamicButton(){
        super("Dynamic Buttons");
        setResizable(false);
        setSize(650, 800);
        AddButtons(15,15);
    }

    public void actionPerformed(ActionEvent event){
    }
    
    public void AddButtons(int rows, int columns){

        frame=new JFrame("Dynamic Buttons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,800); 
        frame.setLayout(null);  

        int totalAmt = rows*columns;
        int xCoordinate = 35;
        int yCoordinate = 0;
        int NumRows =1;
        int NumCols =1;

        int finalXcd=0;
        int finalycd=0;
    
        for (int i = 0; i<totalAmt; i++){
            if((i!=0)&&(i%columns ==0)){
                yCoordinate = yCoordinate+ 35;
                xCoordinate=35;
                NumRows++;
                NumCols=1;
            }

            JButton btn = new CreateRoundButton(NumRows+"-"+NumCols);
            btn.setBounds(xCoordinate,yCoordinate,35,35);
            btn.setOpaque(false);
            btn.setBackground(Color.LIGHT_GRAY);
           
            btn.setFont(new Font("Comic Sans", 0, 8));
            
            btn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //clickedAmt++;
                    boolean earlyWin = false;
                    String title = btn.getText();
                    String[] input = title.split("-",2);
                    int x = Integer.parseInt(input[0]);
                    int y = Integer.parseInt(input[1]);

                    boolean maximumRow = false;
                    if(y>14){
                        maximumRow = true;
                    }

                    btn.setBackground(Color.GREEN);
                    btnClickArr[x][y] =1;
                    if(checkWin(x,y,1)){
                        lbTest.setText("Green wins!");
                        earlyWin =true;
                        gameOver();
                    }
                    btn.setEnabled(false);

                    //then do
                    look4Button(x, y, maximumRow, earlyWin);                    
                }
            });

            frame.add(btn);
            xCoordinate = xCoordinate+ 35;
            NumCols++;
            finalXcd=xCoordinate;
            finalycd=yCoordinate;
        }

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBounds(finalXcd/2,finalycd+100,100,50);
        resetBtn.setBackground(Color.GRAY);
        lbTest.setBounds(finalXcd/2,finalycd+140,200,50);

        resetBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                for(int i = 0; i<=16; i++){
                    for(int j = 0; j<=16; j++){
                        btnClickArr[i][j] = 0;
                    }
                }    
                         
                for(Component c : frame.getContentPane().getComponents()) {
                    if(c instanceof JButton){
                        JButton bn = (JButton) c;
                        String titlex = bn.getText(); 
                        bn.setEnabled(true);
                        
                        if(titlex != "Reset"){
                            bn.setBackground(Color.LIGHT_GRAY);
                            bn.setOpaque(false);
                        } else{
                            bn.setBackground(Color.GRAY);
                            
                        }          
                    }
                    if(c instanceof JLabel){
                        lbTest.setText("Score");
                    }
                }                
            }
        });
    frame.add(resetBtn); 
    frame.add(lbTest);
    frame.setVisible(true); 
    }

    public static void SetBackGnd(JButton bttn){
        bttn.setBackground(Color.RED);
    }


    public boolean checkWin(int x, int y, int colorVal){
        int count =1;
        int i = 1;

        //check columns
        while((colorVal == btnClickArr[x-i][y]) && ((y<16)&& (x<16))){
            i++;
            count++;
        }
        i =1;
        while(colorVal==btnClickArr[x+i][y]){
            i++;
            count++;
        }
        if(count>=5){
            return true;
        }

        //check rows
        i =1;
        count =1;
        while((colorVal == btnClickArr[x][y-i]) && ((y<16)&& (x<16))){
            i++;
            count++;
        }
        i =1;
        while((colorVal==btnClickArr[x][y+i])&& ((y<16)&& (x<16))){
            i++;
            count++;
        }
        if(count>=5){
            return true;
        }
        
        //diagonal downwards
        i =1;
        count =1;
        while((colorVal == btnClickArr[x-i][y-i])&& ((y<16)&& (x<16))){
            i++;
            count++;
        }
        i=1;
        while((colorVal==btnClickArr[x+i][y+i])&& ((y<16)&& (x<16))){
            i++;
            count++;
        }
        if(count>=5){
            return true;
        }

        //diagonal upwards
        i =1;
        count =1;
        while((colorVal == btnClickArr[x+i][y-i])&& ((y<16)&& (x<16))){
            i++;
            count++;
        }
        i=1;
        while((colorVal==btnClickArr[x-i][y+i])&& ((y<16)&& (x<16))){
            i++;
            count++;
        }
        if(count>=5){
            return true;
        } else{
            return false;
        }
    }

    public void gameOver(){
        for(Component c : frame.getContentPane().getComponents()) {
            if(c instanceof JButton){
                JButton bn = (JButton) c;
                String namename = bn.getText();
                if(namename == "Reset"){
                    bn.setEnabled(true);
                }else{
                    bn.setEnabled(false);
                }
                
            }
        }
    }

    public void look4Button(int x, int y, boolean maxRow, boolean earlyWinInput){
        for(Component c : frame.getContentPane().getComponents()) {
            if(c instanceof JButton){
                JButton bn = (JButton) c;
                String btnName = bn.getText();
                String[] output = btnName.split("-",2);
                
                int output1 = Integer.parseInt(output[0]);
                int output2 = Integer.parseInt(output[1]);

                if(output1 ==x){
                    if(maxRow){
                        if(output2 ==y-1){
                            int newy1 = y-1;
                            bn.setBackground(Color.YELLOW);
                            bn.setEnabled(false);
                            btnClickArr[x][newy1] = 2;
                            
                            if((checkWin(x, newy1, 2))&& !(earlyWinInput)){
                                lbTest.setText("Yellow wins!");
                                gameOver();
                            }
                        } 
                    }else{
                       if(output2 ==y+1){
                            int newy2 = y+1;
                            bn.setBackground(Color.YELLOW);
                            bn.setEnabled(false);
                            btnClickArr[x][newy2]= 2;
                            if((checkWin(x, newy2, 2))&& !(earlyWinInput)){
                                lbTest.setText("Yellow wins!");
                                gameOver();
                            }
                        } 
                    }
                } 
                
            }
        }
    }

    public static void main(String[] args) {        
        DynamicButton dybtn = new DynamicButton();
    }
}
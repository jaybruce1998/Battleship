   import javax.swing.*;
   import java.awt.*;
  
   public class BattleshipPanel extends JPanel
   {
      String location;
      char[][][][] board;
      ImageIcon S, M, H;
      int yours, turn;
      public BattleshipPanel(char[][][][] b)
      {
         super();
         location=System.getProperty("user.dir")+"/";
         board=b;
         S=new ImageIcon(location+"b.png");
         M=new ImageIcon(location+"M.png");
         H=new ImageIcon(location+"H.png");
      }
      public void paintComponent(Graphics g)
      {
         yours=battleShip.yours();
         turn=battleShip.turn();
         for(int r=0; r<10; r++)
            for(int c=0; c<10; c++)
               if(board[turn][yours][c][r]=='M')
                  g.drawImage(M.getImage(), r*75, c*75, 75, 75, null);
               else
                  if(board[turn][yours][c][r]=='b')
                     g.drawImage(S.getImage(), r*75, c*75, 75, 75, null);
                  else
                     g.drawImage(H.getImage(), r*75, c*75, 75, 75, null);
         g.drawString(battleShip.message(), 0, 760);
      }
   }
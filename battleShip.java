   import javax.swing.*;
   import java.awt.event.*;
   public class battleShip
   {
      static char[][][][] boards=new char[2][2][10][10];
      static String[] names=new String[2];
      static String message="", inputValue="", dir;
      static int rDir, row, col, num, yours, turn;
      static boolean didSomething;
      private static JFrame frame=new JFrame();
      private static BattleshipPanel bp=new BattleshipPanel(boards);
      private static mouseListener ml=new mouseListener();
      public static void createPanel()
      {
         bp.addMouseListener(ml);
         frame.add(bp);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(768, 800);
         frame.setVisible(true);
      
      }
      public static void fillBoards()
      {
         yours=0;
         for(int p=0; p<boards.length; p++)
            for(int b=0; b<boards[0].length; b++)
               for(int r=0; r<boards[0][0].length; r++)
                  for(int c=0; c<boards[0][0][0].length; c++)
                     boards[p][b][r][c]='b';
      }
      public static void showBoard(int p)
      {
         System.out.println("    0 1 2 3 4 5 6 7 8 9");
         System.out.println("  ----------------------");
         for(int r=0; r<boards[0][0].length; r++)
         {
            System.out.print(r+" | ");
            for(int c=0; c<boards[0][0][0].length; c++)
               if(yours<1)
                  System.out.print(boards[p][0][r][c]+" ");
               else
                  if(p<1)
                     System.out.print(boards[1][1][r][c]+" ");
                  else
                     System.out.print(boards[0][1][r][c]+" ");
            System.out.println("|");
         }
         System.out.println("  ----------------------");
      }
      public static void setNum(String ship)
      {
         if(ship.equals("aircraft carrier"))
            num=5;
         if(ship.equals("battleship"))
            num=4;
         if(ship.equals("submarine")||ship.equals("destroyer"))
            num=3;
         if(ship.equals("patrol boat"))
            num=2;
      }
      public static boolean canPlaceUp(int p, int row, int c)
      {
         if(row-num<0)
            return false;
         for(int r=row; r>row-num; r--)
            if(boards[p][0][r][c]!='b')
               return false;
         return true;
      }
      public static boolean canPlaceDown(int p, int row, int c)
      {
         if(row+num>9)
            return false;
         for(int r=row; r<row+num; r++)
            if(boards[p][0][r][c]!='b')
               return false;
         return true;
      }
      public static boolean canPlaceLeft(int p, int r, int col)
      {
         if(col-num<-1)
            return false;
         for(int c=col; c>col-num; c--)
            if(boards[p][0][r][c]!='b')
               return false;
         return true;
      }
      public static boolean canPlaceRight(int p, int r, int col)
      {
         if(col+num>10)
            return false;
         for(int c=col; c<col+num; c++)
            if(boards[p][0][r][c]!='b')
               return false;
         return true;
      }
      public static void placeUp(int p, int row, int c, String ship)
      {
         for(int r=row; r>row-num; r--)
            boards[p][0][r][c]=ship.toUpperCase().charAt(0);
         didSomething=true;
      }
      public static void placeDown(int p, int row, int c, String ship)
      {
         for(int r=row; r<row+num; r++)
            boards[p][0][r][c]=ship.toUpperCase().charAt(0);
         didSomething=true;
      }
      public static void placeLeft(int p, int r, int col, String ship)
      {
         for(int c=col; c>col-num; c--)
            boards[p][0][r][c]=ship.toUpperCase().charAt(0);
         didSomething=true;
      }
      public static void placeRight(int p, int r, int col, String ship)
      {
         for(int c=col; c<col+num; c++)
            boards[p][0][r][c]=ship.toUpperCase().charAt(0);
         didSomething=true;
      }
      public static boolean canPlace(int p, int r, int c, String ship)
      {
         setNum(ship);
         return canPlaceUp(p, r, c)||canPlaceDown(p, r, c)||canPlaceLeft(p, r, c)||canPlaceRight(p, r, c);
      }
      public static void placeShip(int i, String ship)
      {
         row=10;
         col=10;
         dir="";
         didSomething=false;
         turn=i;
         while(row>9||!canPlace(i, row, col, ship))
         {
            while(row<0||row>9||col<0||col>9)
               bp.repaint();
            if(!canPlace(i, row, col, ship))
            {
               row=10;
               col=10;
            }
         }
         while(!didSomething)
         {
            while(dir.length()<1)
               bp.repaint();
            if(dir.equals("u"))
            {
               if(canPlaceUp(i, row, col))
                  placeUp(i, row, col, ship);
            }
            else
               if(dir.equals("d"))
               {
                  if(canPlaceDown(i, row, col))
                     placeDown(i, row, col, ship);
               }
               else
                  if(dir.equals("l"))
                  {
                     if(canPlaceLeft(i, row, col))
                        placeLeft(i, row, col, ship);
                  }
                  else
                     if(dir.equals("r"))
                     {
                        if(canPlaceRight(i, row, col))
                           placeRight(i, row, col, ship);
                     }
            if(!didSomething)
               dir="";
         }
      }
      public static boolean playerLost(int i)
      {
         for(int r=0; r<boards[0][0].length; r++)
            for(int c=0; c<boards[0][0][0].length; c++)
               if(boards[i][0][r][c]!='b')
                  return false;
         return true;
      }
      public static boolean sunk(int p, char ship)
      {
         for(int r=0; r<boards[0][0].length; r++)
            for(int c=0; c<boards[0][0][0].length; c++)
               if(p>0)
               {
                  if(boards[0][0][r][c]==ship&&boards[0][1][r][c]!='!')
                     return false;
               }
               else
                  if(boards[1][0][r][c]==ship&&boards[1][1][r][c]!='!')
                     return false;
         return true;
      }
      public static void sink(int p, char ship)
      {
         for(int r=0; r<boards[0][0].length; r++)
            for(int c=0; c<boards[0][0][0].length; c++)
               if(p>0)
               {
                  if(boards[0][0][r][c]==ship)
                     boards[0][0][r][c]='b';
               }
               else
                  if(boards[1][0][r][c]==ship)
                     boards[1][0][r][c]='b';
      }
      public static void setBoards(int p, int row, int col)
      {
         if(p>0)
            if(boards[0][0][row][col]=='b')
               boards[0][1][row][col]='M';
            else
            {
               boards[0][1][row][col]='!';
               if(sunk(p, boards[0][0][row][col]))
               {
                  if(boards[0][0][row][col]=='B')
                     message="Your battleship was sunk! ";
                  else
                     message="One of your ships was sunk! ";
                  sink(p, boards[0][0][row][col]);
               }
            }
         else
            if(boards[1][0][row][col]=='b')
               boards[1][1][row][col]='M';
            else
            {
               boards[1][1][row][col]='!';
               if(sunk(p, boards[1][0][row][col]))
               {
                  if(boards[1][0][row][col]=='B')
                     message="Your battleship was sunk! ";
                  else
                     message="One of your ships was sunk! ";
                  sink(p, boards[1][0][row][col]);
               }
            }
         frame.repaint();
      }
      public static void changeRandoms()
      {
         if(row<9)
            row++;
         else
         {
            row=0;
            if(col<9)
               col++;
            else
               col=0;
         }
      }
      public static void placeAIShip(String ship)
      {
         didSomething=false;
         row=(int)(Math.random()*10);
         col=(int)(Math.random()*10);
         rDir=(int)(Math.random()*4);
         while(!canPlace(1, row, col, ship))
            changeRandoms();
         while(!didSomething)
         {
            if(rDir==0)
               if(canPlaceUp(1, row, col))
                  placeUp(1, row, col, ship);
               else
                  rDir++;
            if(rDir==1)
               if(canPlaceLeft(1, row, col))
                  placeLeft(1, row, col, ship);
               else
                  rDir++;
            if(rDir==2)
               if(canPlaceRight(1, row, col))
                  placeRight(1, row, col, ship);
               else
                  rDir++;
            if(rDir==3)
               if(canPlaceDown(1, row, col))
                  placeDown(1, row, col, ship);
               else
                  rDir=0;
         }
      }
      public static void placeAIShips()
      {
         placeAIShip("aircraft carrier");
         placeAIShip("battleship");
         placeAIShip("submarine");
         placeAIShip("destroyer");
         placeAIShip("patrol boat");
      }
      public static void doAITurn()
      {
         row=(int)(Math.random()*10);
         col=(int)(Math.random()*10);
         while(boards[0][1][row][col]!='b')
            changeRandoms();
         setBoards(1, row, col);
      }
      public static void doTurn(int p)
      {
         row=10;
         col=10;
         if(p<1)
            turn=1;
         else
            turn=0;
         frame.repaint();
         while(row>9)
         {
            message+="It is "+names[p]+"'s turn!";
            while(row<0||row>9||col<0||col>9)
               bp.repaint();
            message="";
            if(p>0)
               if(boards[0][1][row][col]=='b')
                  setBoards(p, row, col);
               else
               {
                  row=10;
                  col=10;
               }
            else
               if(boards[1][1][row][col]=='b')
                  setBoards(p, row, col);
               else
               {
                  row=10;
                  col=10;
               }
         }
      }
      public static int yours()
      {
         return yours;
      }
      public static int turn()
      {
         return turn;
      }
      public static String message()
      {
         return message;
      }
      public static void click(int r, int c)
      {
         if(row>9)
         {
            row=r;
            col=c;
         }
         else
            if(r>row)
               dir="d";
            else
               if(r<row)
                  dir="u";
               else
                  if(c>col)
                     dir="r";
                  else
                     dir="l";
      }
      public static void playGame(boolean AIGame)
      {
         fillBoards();
         for(int i=1; i<names.length+1; i++)
         {
            if(i>1&&AIGame)
               names[i-1]="The AI";
            else
               names[i-1]=JOptionPane.showInputDialog("What is player "+i+"'s name?");
         }
         for(int p=0; p<boards.length; p++)
         {
            if(p>0&&AIGame)
               placeAIShips();
            else
            {
               placeShip(p, "aircraft carrier");
               placeShip(p, "battleship");
               placeShip(p, "submarine");
               placeShip(p, "destroyer");
               placeShip(p, "patrol boat");
            }
         }
         yours=1;
         while(!(playerLost(0)||playerLost(1)))
         {
            doTurn(0);
            if(!playerLost(1))
               if(AIGame)
                  doAITurn();
               else
                  doTurn(1);
         }
         if(playerLost(1))
            message=names[0]+" sunk all of "+names[1]+"'s ships!";
         else
            message=names[1]+" sunk all of "+names[0]+"'s ships!";
         frame.repaint();
      }
      public static void main(String[] args)
      {
         createPanel();
         while(!inputValue.equals("3"))
         {
            inputValue="";
            while(!(inputValue.contains("1")||inputValue.contains("2")||inputValue.equals("3")))
            {
               inputValue = JOptionPane.showInputDialog("What would you like to do?\n1) Play with a friend.\n2) Play with the AI.\n3) Exit.");
               if(inputValue==null||inputValue.equals(""))  
                  System.exit(0);
            }
            if(inputValue.contains("1"))
               playGame(false);
            if(inputValue.contains("2"))
               playGame(true);
         }
         System.exit(0);
      }
   }
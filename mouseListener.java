   import java.awt.event.*;
   import java.awt.*;
   import javax.swing.*;
   public class mouseListener extends MouseAdapter
   {
      int r, c;
      public void mousePressed(MouseEvent e)
      {
         r=(int)(e.getY())/75;
         c=(int)(e.getX())/75;
         if(r<10&&c<10)
            battleShip.click(r, c);		
      }
   }
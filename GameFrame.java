//imports
import javax.swing.JFrame;

public class GameFrame extends JFrame {

    //variabels i think it wil just have one

    GameFrame() {

      this.add(new GamePanel());

      this.setTitle("Running");

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      this.setResizable(false);

      this.pack();

      this.setVisible(true);

      this.setLocationRelativeTo(null);


    }



}

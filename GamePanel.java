/*
Made by Emmanuel Serrano. Just a fun little concept game I wanted to make.
It suppose to be based off the spaceship game where you have to kill all the
aliens things coming down.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{
  //variables
  static final int SCREEN_WIDTH = 600;
  static final int SCREEN_HEIGHT = 600;
  static final int UNIT_SIZE = 25;

  static final int rocksize = 3;
  static final int num_rocks = 3;

  static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;

  static final int DELAY = 100;

  //array for the spaceship
  final int x[] = new int[GAME_UNITS];
  final int y[] = new int[GAME_UNITS];
  ///////

  //array for the rocks
  final int Rx[] = new int[num_rocks];
  final int Ry[] = new int[num_rocks];
  /////////////////////

  //array for the shooting
  final int Sx[] = new int[10];
  final int Sy[] = new int[10];
  //////////////

  int idx_shooter = 0;//what shot we at
  int time = 0;//when we going to shoot

  char direction = 'R';//inital direction

  int rock_count;//how mnay rocks destroyed

  boolean running = false;
  Timer timer;
  Random random;

  //want three types of rocks.
  int rock1x;

  int rock2x;

  int rock3x;



  GamePanel(){
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

    this.setBackground(Color.black);

    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());

    startGame();
  }

  public void startGame(){
    //newRocks();
    running = true;
    timer = new Timer(DELAY, this);

    //ship coors
    x[0] = 0;
    x[1] = UNIT_SIZE;
    x[2] = 2 * UNIT_SIZE;
    y[0] = SCREEN_HEIGHT- UNIT_SIZE;
    y[1] = SCREEN_HEIGHT - (2 * UNIT_SIZE);
    //
    make_rocks();

    //rock3y;
    timer.start();

  }
  public void make_rocks(){
    rock1x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
    Rx[0] = rock1x;
    /*for(int i = 1; i%3 < rock_size - 1; i++){
      Rx[i] = Rx[i-1] + UNIT_SIZE;
    }*/

    rock2x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
    while(rock2x == rock1x){
      rock2x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))* UNIT_SIZE;
    }
    Rx[1] = rock2x;
    /*for(int i = 4; i%3 < rock_size - 1; i++){
      Rx[i] = Rx[i-1] + UNIT_SIZE;
    }*/

    rock3x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))* UNIT_SIZE;
    while(rock3x == rock1x || rock3x == rock2x){
      rock3x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
    }
    Rx[2] = rock3x;
    /*for(int i = 7; i%3 < rock_size - 1; i++){
      Rx[i] = Rx[i-1] + UNIT_SIZE;
    }*/
  }
  //public void newRunner(){
    //run_man = new Runner();
  //}

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
  }
  public void draw(Graphics g) {
    if(running) {


      g.setColor(Color.white);//spaceship
      g.fillRect(x[0],y[0],UNIT_SIZE, UNIT_SIZE);//idk if this is right

      g.setColor(Color.white);
      //x[1] = UNIT_SIZE;
      g.fillRect(x[1],y[0],UNIT_SIZE, UNIT_SIZE);

      g.setColor(Color.white);
      //x[2] = 2 * UNIT_SIZE;
      g.fillRect(x[2],y[0],UNIT_SIZE, UNIT_SIZE);


      g.setColor(Color.white);
      g.fillRect(x[1],y[1], UNIT_SIZE, UNIT_SIZE);

      g.setColor(Color.red);
      g.fillRect(Rx[0], Ry[0], 3* UNIT_SIZE, 3* UNIT_SIZE);
      g.setColor(Color.blue);
      g.fillRect(Rx[1], Ry[1], 3 * UNIT_SIZE, 3 * UNIT_SIZE);
      g.setColor(Color.green);
      g.fillRect(Rx[2], Ry[2], 3 * UNIT_SIZE, 3 * UNIT_SIZE);

      for(int i = 0; i < 10; i++){
        g.setColor(Color.pink);
        if(Sy[i] != 0){
          //g.setColor(Color.pink);
          g.fillRect(Sx[i], Sy[i], UNIT_SIZE,UNIT_SIZE);
        }
      }



    //score title
      g.setColor(Color.white);//color brown(150, 75, 0)
      g.setFont( new Font("Ink Free",Font.BOLD, 40));
      FontMetrics metrics = getFontMetrics(g.getFont());//somehow helps
      g.drawString("Score: " + rock_count,/*rock_count,*/
        (SCREEN_WIDTH - metrics.stringWidth("Score: " + rock_count))/2,
          g.getFont().getSize());


    }
    else {
      gameOver(g);
    }

  }

  public void newRock(int i) {//might make a class for this as well
        Rx[i] = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        Ry[i] = 0;
  }


  //public void runner(){//might make a class for this

  //}

  public void checkCollison(){
    //add the rock collisons
    if(x[0] < 0) {
      if(direction != 'S'){
        x[0] = 0;
        x[1] = UNIT_SIZE;
        x[2] = 2 * UNIT_SIZE;
      }
      direction = 'S';

    }
    if (x[2] > SCREEN_WIDTH - (UNIT_SIZE)) {
      if(direction != 'S'){
        x[0] = SCREEN_WIDTH - (3 * UNIT_SIZE);
        x[1] = SCREEN_WIDTH - (2 * UNIT_SIZE);
        x[2] = SCREEN_WIDTH - (UNIT_SIZE);
      }
      direction = 'S';
    }

    //if rock hits spaceship
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 3; j++){
        if(Rx[i] == x[j] && Ry[i] == y[0]){
          running = false;
        }
      }
    }

    //if rock hits ground
    for(int i = 0; i < 3; i++){
      if(Ry[i] == SCREEN_HEIGHT){
        newRock(i);
        rock_count++;
      }
    }
    //if shoot hits
    for(int i = 0; i < 10; i++){
        for(int j = 0; j < 3; j++){
          if(Sx[i] == Rx[j] && Sy[i] == Ry[j]){
            newRock(j);
            Sx[i] = 0;
            Sy[i] = 0;
            rock_count++;
          }
      }
    }


    if (running == false) {
      timer.stop();
    }
  }

  //public void Score() {//score and highscore might have it own class

  //}

  public void move(){//we have to move the snake

    switch(direction) {
      case 'L':
        x[0] = x[0] - UNIT_SIZE;
        x[1] = x[1] - UNIT_SIZE;
        x[2] = x[2] - UNIT_SIZE;
        break;
      case 'R':
        x[0] = x[0] + UNIT_SIZE;
        x[1] = x[1] + UNIT_SIZE;
        x[2] = x[2] + UNIT_SIZE;
        break;
      case 'S':
        break;
      }
  }
  //public void checkHighScore() {// will be called in gameover

  //}

  public void gameOver(Graphics g) {
    g.setColor(Color.red);
    g.setFont( new Font("Ink Free", Font.BOLD, 40));
    FontMetrics metrics1 = getFontMetrics(g.getFont());//somehow helps
    g.drawString("Score: " + rock_count,
      (SCREEN_WIDTH - metrics1.stringWidth("Score: " + rock_count))/2,
        g.getFont().getSize());

  // g is our paintbush
      //write text on screen
    g.setColor(Color.red);
    g.setFont( new Font("Ink Free", Font.BOLD, 75));
    FontMetrics metrics2 = getFontMetrics(g.getFont());//somehow helps
      // put it in middle
    g.drawString("Game Over",
      (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
      //actually put it in the middle

    g.setColor(Color.red);
    g.setFont( new Font("Ink Free", Font.BOLD, 20));
    FontMetrics metrics3 = getFontMetrics(g.getFont());
    g.drawString("Press Enter to play again",
      (SCREEN_WIDTH - metrics3.stringWidth("Press Enter to play again"))/2,
        (SCREEN_HEIGHT/2 + SCREEN_HEIGHT/4));
  }

  //public void checkRocks(){

  //}


  public void Shoot(){
    if(idx_shooter == 10){
      idx_shooter = 0;
    }
    Sx[idx_shooter] = x[0];
    Sy[idx_shooter] = SCREEN_HEIGHT - (UNIT_SIZE);

    Sx[idx_shooter + 1] = x[2];
    Sy[idx_shooter + 1] = SCREEN_HEIGHT - (UNIT_SIZE);

    idx_shooter = idx_shooter + 2;
  }
  public void moveShots(){

    for(int i = 0; i < 10; i ++){
      if(Sy[i] > 0){
        Sy[i] = Sy[i] - UNIT_SIZE;
      }
    }

    /*for(int i = 0; i < 10; i ++){
      if(Sy[i] < 0){
        Sy[i] = 0;
        Sx[i] = 0;
      }
    }*/
  }
  public void moveRocksDown(){

      for(int i = 0; i < 3; i++){
        Ry[i] = Ry[i] + UNIT_SIZE;
      }

      //if(destoy()) //check if a rock has been shot and if so break
        //break;
    }


  @Override
  public void actionPerformed(ActionEvent e) {

    if(running) {


      move();
      if((time % 10) == 0){
        Shoot();
        //time ++;
      }
      time++;
      checkCollison();
      //moveShots();
      moveRocksDown();
      checkCollison();
      //moveRocksDown();
      moveShots();
      checkCollison();
    }
    repaint();

  }
  public class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      switch(e.getKeyCode()) {
      	                           //VK_LEFT means the left arrow key on
      case KeyEvent.VK_LEFT:			//keyboard making sure we can not crash back
        direction = 'L';
        break;

      case KeyEvent.VK_RIGHT:
        direction = 'R';
        break;

      /*case KeyEvent.VK_DOWN://a manually way to shut the game down
        //running = false;
        newRock(2);
        break;*/

      case KeyEvent.VK_ENTER://restart button
        if(!running){
          new GameFrame();
        }
      }
    }
  }
}

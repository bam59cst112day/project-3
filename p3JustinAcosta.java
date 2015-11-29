// Project 3 CST 112 EVE
// by Justin Acosta

//BAM:  Nice job!  A+

//Globals
float top, bottom, left, right, middle;  // edges and middle of felt
float xpos, ypos;                        // x and y positions of balls
int score;                               // integer for the games score  
int count = 0;                           // count for animation
boolean wall, rat, bird;                 // on and off for wall, rat and bird. Defined in reset()
boolean cue;                             // controlls cue ball movement. Defined in reset() 
Cloud[] nimbus;                          //Array of clouds called nimbus
// Objects
Pool table;     // pool table object
Ball redBall, greenBall, blueBall, yellowBall, cueBall;  // Ball objects, 4 balls and 1 cue ball
Button resetButton, wallButton, birdButton, ratButton;   // Button objects for reset, wall, bird and rat
Rodent mouse;  // Rodent object
Grass g;       // Grass object
Cloud c;       // Cloud object
Bird eagle;    // Bird object


//initial size 700, 500. includes array for clouds
void setup() {
  size(900, 700); 
  reset();
  nimbus = new Cloud[7];  // array nimbus length is 7
  float cloudX = 0;       // starting postion of cloud
  for (int i=0; i < nimbus.length; i++) {  // loop to fill array nimbus
    nimbus[i] = new Cloud( cloudX, height/4, 30, 30); // nimbus is filled with cloud objects
    cloudX += 100;  // distance between clouds
  }
}
// Starting positions
void reset() {
  score = 0;
  wall = true;              // wall exists on start and reset
  rat = false;              // rat does not start on screen
  bird = false;             // bird does not start on screen
  cue = false;              // cue does not move at start, cue moves when true 
  top= height*34/100;       // top edge of felt     
  bottom= height*88/100;    // bottom edge of felt
  left= width*8/100;        // left edge of felt
  right=width*89/100;       // right edge of felt
  middle= left + (right-left)/2;  //center of felt
  xpos = random(middle+50, right-50);  // x position for balls
  ypos = random(top+50, bottom-50);    // y position for balls
  //Ball positions, colors and number
  redBall =    new Ball(xpos,       ypos, color(255, 0, 0), "1");              // Red ball
  greenBall =  new Ball(xpos+30, ypos+30, color(0, 255, 0), "2");      // Green ball
  blueBall =   new Ball(xpos-30, ypos-30, color(0, 150, 255), "3");     // Blue ball
  yellowBall = new Ball(xpos+30, ypos-30, color(255, 255, 0), "4");   // Yellow ball
  cueBall =    new Ball(width/4, height*60/100, color(255), "");       // Cue ball
  //Rodent and Bird positions 
  mouse = new Rodent(0, height*82/100, color(150));               
  eagle = new Bird(100, height*25/100, 80);
}

// Draw
void draw() {
  background(0, 100, 150);
  count += 1;
  scene();
  action();
  info();
  showButtons();
}

// Shows the scene, table, grass, clouds, rat and bird
void scene() {
  table = new Pool();  // table is a new Pool object
  g = new Grass();     // g is a Grass object
  table.show();        // show table
  g.show();            // show grass
  mouse.show();        // show rat
  mouse.move();        // move rat
  // loop that goes through the nimbus array
  for (int i=0; i < 7; i++) { nimbus[i].show();}    // show clouds
  eagle.show();        // show bird
  eagle.move();        // move bird
}

// shows buttons and their text
void showButtons() {
  //Locals for Button text and button position  
  String clear= "Reset               (r)";  // buttonReset text
  String toggle= "Wall               (w)";  // buttonWall text
  String rodent= "Rat                (m)";  // buttonRat text
  String bombs= "Bird (b)      Bomb ( j )";  // bird and bomb button
  float resetX1, resetX2, wallX1, wallX2, rodentX1, rodentX2, birdX1, birdX2;
  float buttonY1, buttonY2;
  //Button Positions (X1, Y1) and (X2, Y2) 
  resetX1 = width*4/100;      resetX2 = width*20/100;     // Reset button x1, x2
  wallX1 = width*21/100;      wallX2 = width*37/100;      //  wall button x1, x2
  rodentX1 = width*38/100;    rodentX2 = width*54/100;    // rodent button x1, x2
  birdX1 = width*55/100;      birdX2 = width*71/100;      // bird button x1, x2
  buttonY1 = height*6/100;    buttonY2 = height*15/100;   //  All 4 buttons have the same y1, y2
  // creating 4 button objects
  resetButton= new Button(resetX1, buttonY1, resetX2, buttonY2, color(100, 200, 100), clear); // resets table
  wallButton= new Button(wallX1, buttonY1, wallX2, buttonY2, color(150, 0, 255), toggle);   // removes wall
  ratButton= new Button(rodentX1, buttonY1, rodentX2, buttonY2, color(150), rodent);      //  calls rat
  birdButton= new Button(birdX1, buttonY1, birdX2, buttonY2, color(255, 0, 0), bombs);    // calls bird
  // shows button by calling the button class 'show' method
  resetButton.show();
  wallButton.show();
  ratButton.show();
  birdButton.show();
}

// Shows each ball, moves them, handles collisions between each ball and rat
void action() {
  // Collisions between balls
  collisions( redBall, greenBall);
  collisions( redBall, blueBall);
  collisions( redBall, yellowBall);
  collisions( redBall, cueBall);
  collisions( greenBall, cueBall);
  collisions( greenBall, blueBall);
  collisions( greenBall, yellowBall);
  collisions( blueBall, yellowBall);
  collisions( blueBall, cueBall);
  collisions( yellowBall, cueBall);
  // Collisions between Rat and balls  
  ratHit( mouse, redBall);
  ratHit( mouse, greenBall);
  ratHit( mouse, blueBall);
  ratHit( mouse, yellowBall);
  // show and move ball  
  redBall.show();
  redBall.move();
  greenBall.show();
  greenBall.move();
  blueBall.show();
  blueBall.move();
  yellowBall.show();
  yellowBall.move();
  cueBall.show();
  cueBall.moveCue();  // cue ball has different move method
}

// code to make balls collide and swap speeds
void collisions(Ball p, Ball q) {
  float tmp;
  if (dist(p.x, p.y, q.x, q.y) < 30) {
    tmp = p.dx;    p.dx = q.dx;    q.dx = tmp;
    tmp = p.dy;    p.dy = q.dy;    q.dy = tmp;
    score += 1;  // score increase by 1 for each collision

    if (dist(cueBall.x, cueBall.y, q.x, q.y) < 30) {  // cue ball collisions
      cue = true; /* if cue ball is hit, it is given a DX and DY value. Defined in Ball.moveCue()
                     this stops one ball from always being at rest */
      // swap speeds between cue ball and other balls               
      tmp = cueBall.cueDX;    cueBall.cueDX = q.dx;    q.dx = tmp;
      tmp = cueBall.cueDY;    cueBall.cueDY = q.dy;    q.dy = tmp;
    }
  }
}

// Rat and Ball collisions
void ratHit(Rodent p, Ball q) { 
  if (dist(mouse.x, mouse.y, q.x, q.y) < 30) {
    p.dx = random(-6, 6);   // Rat goes in random directions when it hits a ball
    p.dy = random(-6, 6);
    q.dx = 0;              // Ball stops moving when hit by rat
    q.dy = 0;
    score -= 10;           // deduct 10 points when rat hits ball
  }
}

// Displays name, info, and score
void info() {
  String name = "Justin Acosta";                                      // My name
  String title = "Project 3  CST 112 EVE";                           // Title 
  String click= "click a ball to reset its position!";              // click ball text
  String press= "press key 1, 2, 3 or 4 to reset respective ball!";// key 1,2,3 text

  if (score < 0) {score = 0;}    // score cannot be negative

  fill(0);
  textSize(20);
  text(name, width*4/100, height*98/100);       //name 
  text(title, width*4/100, height*4/100);       //title
  text("Score", middle, height*4/100);          //score text
  text(score, middle+80, height*4/100);         //score display
  textSize(15);
  text(click, middle, height*96/100);       // click ball text
  text(press, middle, height*99/100);       // press 1,2,3 text
  textSize(12);  // reset text size to default
}
// Handles key presses
void keyPressed() {
  if (key == 'w') {wall= !wall;}     // w removes wall
  if (key == 'r') {reset();}         // r resets table
  if (key == 'm') {rat = !rat;}      // m calls rat
  if (key == 'b') {bird = !bird;}    // b calls bird
  if (key == 'j') {eagle.bombDrop();}// j drops bomb from bird
  
  // 1 resets red ball position and speed, -5 points
  if (key == '1') {                
    redBall.x = xpos;        redBall.dx = random(-6, 6); 
    redBall.y = ypos;        redBall.dy = random(-6, 6);
    score -= 5;
  }
  // 2 resets green ball position and speed, -5 points
  if (key == '2') {                
    greenBall.x = xpos+30;    greenBall.dx = random(-6, 6);
    greenBall.y = ypos+30;    greenBall.dy = random(-6, 6);
    score -= 5;
  }  
  // 3 resets blue ball position and speed, - 5 points
  if (key == '3') {               
    blueBall.x = xpos-30;     blueBall.dx = random(-6, 6);  
    blueBall.y = ypos-30;     blueBall.dy = random(-6, 6);
    score -= 5;
  }     
  // 4 resets yellow ball position and speed, - 5 points
  if (key == '4') {
    yellowBall.x = xpos+30;   yellowBall.dx = random(-6, 6);
    yellowBall.y = ypos-30;   yellowBall.dy = random(-6, 6);
    score -= 5;
  }
}
// Click on buttons, balls, rat
void mousePressed() {
  // Reset button
  if (mouseButton == LEFT &&       // Click the reset button to reset table
    mouseX > resetButton.x1 &&
    mouseX < resetButton.x2 &&
    mouseY > resetButton.y1 &&
    mouseY < resetButton.y2) {
    reset();
  }
  // Wall Button
  if (mouseButton == LEFT &&      // click the wall button to toggle the wall on and off
    mouseX > wallButton.x1 &&
    mouseX < wallButton.x2 &&
    mouseY > wallButton.y1 &&
    mouseY < wallButton.y2) {
    wall = !wall;
  }
  // Rat Button
  if (mouseButton == LEFT &&     //  click the rat button to toggle the rat on and off
    mouseX > ratButton.x1 &&
    mouseX < ratButton.x2 &&
    mouseY > ratButton.y1 &&
    mouseY < ratButton.y2) {
    rat = !rat;
  }
  // Bird button
  if (mouseButton == LEFT &&     //  click the bird button to call the bird, click again to drop bombs
    mouseX > birdButton.x1 &&
    mouseX < birdButton.x2 &&
    mouseY > birdButton.y1 &&
    mouseY < birdButton.y2  ) {
    bird = true;
    if (bird = true)           // if the bird is on screen, the bird button will drop bombs
      eagle.bombDrop();
  }
  // Click Rat
  if (mouseButton == LEFT &&    // left click rat reset its position and speed, + 50 points
    (dist(mouse.x, mouse.y, mouseX, mouseY) < 70)) { 
    mouse.x = 0; 
    mouse.dx = 6;                    // resets rats speed to 6
    mouse. y = random(top, bottom);  // Rat will have random y when called again
    score += 50;
  } 
  // Click balls
  // Red ball
  if (mouseButton == LEFT &&    // left click red ball to reset it, - 5 points
    (dist(redBall.x, redBall.y, mouseX, mouseY) < 50)) { 
    redBall.x = xpos;             
    redBall.y = ypos;
    redBall.dx = random(-6, 6);    
    redBall.dy = random(-6, 6);
    score -= 5;
  }
  // Green ball
  if (mouseButton == LEFT &&    // left click green ball to reset it, - 5 points
    (dist(greenBall.x, greenBall.y, mouseX, mouseY) < 50)) { 
    greenBall.x = xpos + 30;         
    greenBall.y = ypos + 30;
    greenBall.dx = random(-6, 6);    
    greenBall.dy = random(-6, 6);
    score -= 5;
  }
  // Blue ball
  if (mouseButton == LEFT &&    // left click blue ball to reset it, - 5 points
    (dist(blueBall.x, blueBall.y, mouseX, mouseY) < 50)) { 
    blueBall.x = xpos - 30;      
    blueBall.y = ypos - 30;
    blueBall.dx = random(-6, 6);
    blueBall.dy = random(-6, 6);
    score -= 5;
  }
  // Yellow ball
  if (mouseButton == LEFT &&    // left click yellow ball to reset it, - 5 points
    (dist(yellowBall.x, yellowBall.y, mouseX, mouseY) < 50)) { 
    yellowBall.x = xpos + 30;      
    yellowBall.y = ypos - 30;
    yellowBall.dx = random(-6, 6);
    yellowBall.dy = random(-6, 6);
    score -= 5;
  }
}

// Ball class
class Ball {
  // locals for ball class
  float x, y, dx, dy;   // used for position and speed
  float cueDX, cueDY;   // speed of cue ball
  color c;              // used for ball color
  String number= "";    // used for the number of the ball
  // Ball Constructor
  Ball(float tempX, float tempY, color tempC, String tempNumber ) { 
    x = tempX;    y = tempY;  // initialize the constructor
    c = tempC;    
    number = tempNumber;
    dx = random(-6, 6);     // balls will all have random speed
    dy = random(-6, 6);
    cueDX = random(-6, 6);
    cueDY = random(-6, 6);
  }
  // Method that shows a ball 
  void show() {
    fill(c);
    ellipse(x, y, 30, 30);
    fill(0);                  // text for balls is black
    textSize(20);
    text(number, x-5, y+7);   
    textSize(12);
  }

  // Method that moves and bounces balls of walls
  void move() {
    x += dx;  
    y += dy;

    if (wall) {  // bounce of wall         
      if (x < middle + 35 || x > right - 15) dx *= -1;   
      if (y < top + 15 || y > bottom - 15) dy *= -1;
    } else {  //bounce off left if wall is gone 
      if (x < left + 15  || x > right - 15) dx *= -1;         
      if (y < top + 15 || y > bottom - 15) dy *= -1;
    }
  }

  //  Method that moves and bounces cueball
  void moveCue() {
    if (cue == true) {  // cue is at rest at start. if cue is hit by other balls it moves.
      x += cueDX;
      y += cueDY;
    }
    if (x < left + 15  || x > right - 15) {cueDX *= -1;}  // bounce cue ball off walls
    if (y < top + 15 || y > bottom - 15) {cueDY *= -1;}   // cue ignores wall
  }
}
// button class
class Button {
  //locals for button class
  float x1, y1, x2, y2;  // used for position
  color c;               // used for color, each button can have a different color
  String z;              // used for button text, each button can have different text
  //Button constructor
  Button(float tempX1, float tempY1, float tempX2, float tempY2, color tempC, String tempZ) {
    x1 = tempX1;     y1 = tempY1;
    x2 = tempX2;     y2 = tempY2;
    c = tempC;       z = tempZ;
  }
  // shows button
  void show() {
    rectMode(CORNERS);
    fill(c);
    rect(x1, y1, x2, y2);
    fill(0);                      // button text is black
    textSize(15);
    text(z, x1 + 35, y1, x2, y2);
    textSize(12);
  }
}

// pool table class
class Pool {
  // shows table
  void show() {
    rectMode(CORNERS);  // rect mode is corners
    fill(#432805);
    rect( width*4/100, height*29/100, width*93/100, height*93/100);     //boarder
    fill(100, 200, 100);
    rect(left, top, right, bottom);            //felt

    strokeWeight(20);                          //stroke weight of wall
    stroke(150, 0, 255);                       // wall color
    if (wall) {                                  //wall
      line(middle, top + 6, middle, bottom - 6);
      fill(0);
      textSize(20);
      text("W", middle - 7, height/2);         // wall text
      text("A", middle - 7, height/2 + 30);
      text("L", middle - 7, height/2 + 60);
      text("L", middle - 7, height/2 + 90);
      textSize(12);
    }
    strokeWeight(1);                           //reset stroke weight and color
    stroke(0);
  }
}
// rodent class
class Rodent {
  float x, y, w, h;
  float dx = 6, dy = 0;
  color c;
  Rodent(float tempX, float tempY, color tempC) {
    x = tempX;    y = tempY;       
    c = tempC;
  }
  // shows rat
  void show() {
    if (rat == true) {
      fill(c);
      ellipse(x, y, 50, 30);     //body
      fill(255, 200, 200);
      ellipse(x + 30, y - 15, 30, 10);  //nose
      fill(c);
      ellipse(x + 20, y - 20, 10, 30);  // ear
      ellipse(x + 30, y - 20, 10, 30);  //ear
      fill(255, 200, 200);
      ellipse(x + 20, y - 20, 5, 20);  // inner ear
      ellipse(x + 30, y - 20, 5, 20);  // inner ear
      fill(c);
      ellipse(x + 25, y - 15, 30, 20);  // head
      stroke(0);
      strokeWeight(4);
      point(x + 23, y - 18);   //eye
      point(x + 33, y - 18);   //eye
      stroke(255, 200, 200);
      line(x - 20, y - 10, x - 30, y - 30);   // tail
      stroke(0); // leg color
      if (count/30 % 2 == 0) {           //leg animation
        line(x - 15, y + 10, x - 10, y + 25);    //legs
        line(x + 15, y + 10, x + 10, y + 25);
      } else {
        line(x - 15, y + 10, x - 25, y + 25);    //legs
        line(x + 15, y + 10, x + 25, y + 25);
      }
    }
    strokeWeight(1);
    stroke(0);
  }

  void move() {
    if (rat == true) { // rat only moves when on screen
      x += dx; 
      y += dy;
    } else {
      x = 0; // if rat is off screen its x position returns to 0
    }
    if (x > width || x < 0) {dx *=  -1;}    // bounce rat off  screen
    if (y > bottom  || y < top) { dy *= -1;}  // bounce rat off top and bottom of pool table
  }
}
// grass class
class Grass {
  float y1 = height*90/100, y2 = height;  // grass y position
  void show() {
    for ( float x = 0; x < width; x += 30) {  //loop that makes grass until it hits the width
      strokeWeight(5);
      stroke(0, 100, 0);
      line(x, y1, x, y2);
    }
    strokeWeight(1); // reset stroke and stroke weight
    stroke(0);
  }
}
// cloud class
class Cloud {
  float x, y, w, h; 
// cloud constructor
  Cloud(float tempX, float tempY, float tempW, float tempH) {
    x = tempX;    y = tempY;
    w = tempW;    h = tempH;
  }
  // shape of cloud
  void show() {
    noStroke();
    fill(255);
    ellipse(x, y, w, h);
    ellipse(x, y - 25, w, h);
    ellipse(x + 25, y, w, h);
    ellipse(x + 25, y -25, w, h);
    ellipse(x + 12, y - 15, w+50, h);
    stroke(0);
    x = ( x + random( 3 ) ) % width;   // move clouds randomly
  }
}
//Bird class.  Birds can drop bombs
class Bird {
  float x, y, dx = 3, dy = random(-.5, .5); // position and speed of bird
  float w; // width of bird
  float bombY = 0, bombDY = 0;  // position and speed of bombs
  float drop = 9.81 / 60;       // rate of bomb drop
  // bird constructor
  Bird(float tempX, float tempY, float tempW) {
    x = tempX;    y = tempY;    w = tempW;
  }
  void show() {
    if (bird == true) {
      fill(255, 0, 0);
      triangle(x, y, x - w, y - 20, x - w, y +10);  // bird body
      if (count /30 % 2 == 0) {   // wing animation
        fill(255, 0, 0);
        triangle(x - 40, y - 5, x - 60, y + 40, x - 60, y - 5);   // wing down
      } else {
        fill(255);
        triangle(x - 40, y - 5, x - 60, y - 50, x - 60, y - 5);   // wing up
      }
    }
    // show bomb
    if (bombDY > 0) {  // bomb shows only when it moves
      if (count / 30 % 2 == 0) { // bomb flash
        fill(255, 0, 0);         //  flash red
      } else {
        fill(#FFB905);           //  flash orange
      }
      ellipse(x - 30, bombY, 30, 50); // bomb shape
    }
  }
// moves bird
  void move() {
    if (bird == true) {  // bird only moves when on screen
      x += dx; 
      y += dy;
    } else {
      x = 0;        // bird x position resets to 0 when off screen
    }
    if (x > width + 200) { x = 0;}  //  bird comes back if it goes horizontaly off screen
    if (y < height*20/100 || y > top - 30) { dy *= -1;} // bird bounces up and down

    if (bombDY > 0 ) {   
      bombDY += drop;  // increase bomb speed
      bombY += bombDY; // makes the bomb move down
      if (bombY > height) {  // if the bomb goes off screen, it stops moving, which makes it disapear
        bombY = 0;
        bombDY = 0;
      }
    }
  }

// makes the bomb drop
  void bombDrop() {
    bombY = y + 10;  //  bomb postion is just below bird
    bombDY = drop;   //  gives the bombDY a value, which makes the bomb appear.
  }
}

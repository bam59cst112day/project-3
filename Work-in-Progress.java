// Project 3 CST 112 EVE
// by Justin Acosta
//Work in Progress

/* Redo M2 with classes
Use a for loop to display blades of grass evenly along the bottom of screen
Use a for loop to display a random number of clouds moving left to right
A new random number of clouds from 1-7 is computed when clouds go off screen right
4 pool balls with a different color and number, random velocity
1 cue ball, not numbered, no velocity
balls bounce and collide
Add buttons that reset balls, wall
Add buttons that spawns an animated bird that drops bombs when button is clicked again
Add button for animated rat, rat collides with balls, stoping them, rat keeps going
Add scoring
Add 1 point for each ball collision
Subtract 10 when rat hits ball
Clicking on a ball should reset only that ball
Clicking ball deducts 5 points
Make rat clickable, rat diappears when clicked, add 50 points
*/
//Globals
float top, bottom, left, right, middle;  // edges and middle of felt
float tableR, tableG, tableB;
int score;
boolean wall, rat;  
// Declaring Objects, 4 balls and 1 cue ball
Ball redBall, greenBall, blueBall, yellowBall, cueBall;  

void setup(){
 size(700, 500); 
 reset();
 
}
// Starting positions
void reset(){
  score = 0;
  wall = true;              // wall exists on start and reset
  rat = false;              // rat does not start on screen
  top= height*34/100;       // top edge of felt     
  bottom= height*88/100;    // bottom edge of felt
  left= width*8/100;        // left edge of felt
  right=width*89/100;       // right edge of felt
  middle= left + (right-left)/2;  //center of felt
  tableR = 100; 
  tableG = 200;
  tableB=100;
  //Ball positions, colors and number
  redBall= new Ball(random(middle+50, right-50),random(top+50, bottom-50), color(255,0,0), "1");
  greenBall= new Ball(random(middle+50, right-50),random(top+50, bottom-50), color(0,255,0), "2");
  blueBall= new Ball(random(middle+50, right-50),random(top+50, bottom-50), color(0,150,255), "3");
  yellowBall= new Ball(random(middle+50, right-50),random(top+50, bottom-50), color(255,255,0), "4");
  cueBall= new Ball(width/4, height*60/100, color(255), "");

}


void draw(){
  background(0, 100, 150);
  table();
  action();
  info();
  
}
// Shows Table and Wall
void table(){
  rectMode(CORNERS);  // rect mode is corners
  fill(#432805);
  rect( width*4/100, height*29/100, width*93/100, height*93/100);     //boarder
  fill(tableR, tableG, tableB);
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
// Shows each ball, moves them, and have them collide
void action(){
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
  redBall.show();
  redBall.move();
  greenBall.show();
  greenBall.move();
  blueBall.show();
  blueBall.move();
  yellowBall.show();
  yellowBall.move();
  cueBall.show();
  cueBall.moveCue();
  
}
// code to make balls collide and swap speeds
void collisions(Ball p, Ball q){
  if(dist(p.x, p.y, q.x, q.y) < 30){
    float tmp;
    tmp = p.dx;  p.dx = q.dx;  q.dx = tmp;
    tmp = p.dy;  p.dy = q.dy;  q.dy = tmp;
    score += 1;  // score increase by 1 for each collision
    
  if(dist(cueBall.x, cueBall.y, q.x, q.y) < 30){  // cue ball collisions
    tmp = cueBall.cueDX;  cueBall.cueDX = q.dx;  q.dx = tmp;
    tmp = cueBall.cueDY;  cueBall.cueDY = q.dy;  q.dy = tmp;
    
  }
  }
}

// Displays name, info, and score
void info(){
String name = "Justin Acosta";                                 // My name
String title = "Project 3  CST 112 EVE";                              // Title Midterm 2
String toggle= "press 'w' or click here to toggle the wall!";  // buttonWall text
String clear= "press 'r' or click here to reset the table!";   // buttonReset text
String rodent= "press 'm' or click here to call rat!";         // buttonRat text
String click= "click a ball to reset its position!";           // click ball text
String press= "press key 1, 2, or 3 to reset respective ball!";// key 1,2,3 text
  
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

// Ball class
class Ball{
  // locals for ball class
  float x, y, dx, dy;   // used for position and speed
  float cueX, cueY, cueDX, cueDY;
  color c;              // used for ball color
  String number= "";    // used for the number of the ball
// Ball Constructor
 Ball(float tempX, float tempY, color tempC, String tempNumber ){ 
    x = tempX;    y = tempY;  // initialize the constructor
    c = tempC;
    number = tempNumber;
    dx = random(-6,6);     // balls will all have random speed
    dy = random(-6,6);
    cueDX = 0;
    cueDY = 0;
 }
 // Method that shows a ball 
 void show(){
  fill(c);
  ellipse(x, y, 30, 30);
  fill(0);
  textSize(20);
  text(number, x-5, y+7);
  textSize(12);
 }

// Method that moves and bounces balls of walls
void move(){
  x += dx;
  y += dy;
  
  if (wall) {           
    if (x < middle + 35 || x > right - 15) dx *= -1;   
    if (y < top + 15 || y > bottom - 15) dy *= -1;
} else {  //bounce off left if wall is gone 
    if (x < left + 15  || x > right - 15) dx *= -1;         
    if (y < top + 15 || y > bottom - 15) dy *= -1;
    }
  }
//  Method that moves and bounces cueball
 void moveCue(){
  x += cueDX;
  y += cueDY;
 
    if (x < left + 15  || x > right - 15){cueDX *= -1;}
    if (y < top + 15 || y > bottom - 15){cueDY *= -1;}
 }
}

void keyPressed(){
 if (key == 'w'){wall= !wall;}
 if (key == 'r'){reset();}
}

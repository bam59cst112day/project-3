float tmp;

float cueX,cueY,cueDX,cueDY;
float orangeX,orangeY,orangeDX,orangeDY;
float purpleX,purpleY,purpleDX,purpleDY;
float greenX,greenY,greenDX,greenDY;

int x=0;
int speed=1;


void setup(){
size(680,440);
background(200,200,0);
left=65;
right= width-50;
top= 100;
bottom=height-60;
middle= left+(right-left)/2;
reset();
}

void reset(){
  cueX=left +(right-left)/2;
  cueY=top+(bottom-top)/2;
  orangeX=left+(right-left)/2;
  orangeY=top+(bottom-top)/2;
  purpleX=left+(right-left)/2;
  purpleY=top+(bottom-top)/2;
  greenX=left+(right-left)/2;
  greenY=top+(bottom-top)/2;
  ;
  //random positions
  cueX=random(middle,right); cueY=random(left,bottom);{
  orangeX=random(middle,right); orangeY=random(left,bottom);
 purpleX=random(middle,right); purpleY=random(top,bottom);
 greenX=random(middle,right); greenY=random(top,bottom);
  }
  
  //random speed
 orangeDX= random(1,3); orangeDY=random(1,3);{
  purpleDX=random(1,3); purpleDY=random(1,3);
  greenDX=random(1,3); greenDY=random(1,3);}
}


  void draw(){
    x=x+speed;
    
  if ((orangeDX < width) || (orangeDX>30)){
speed = speed * -1;
  }
  
if((purpleDX> width)||(purpleDX<30)){
speed=speed*-1;
}

if((greenDX>width)|| (greenDX<30)){
speed=speed*-1;
}

 
 

 
  background(255,255,200); 
  rectMode(CORNERS);
    fill(50,205,50);
       rect (left+40,top+20,right+20,bottom+20);
       
   
     bounce();
    show();
    collision();
    
    

  stroke(0,127,0);
  line(middle,top+10,middle,bottom-10);
  }
  
  
  

  


void bounce(){
  
  
 cueX+=cueDX; if((cueX<left) ||(cueX>right)) cueDX*=-1;
 cueY+=cueDY; if((cueY<left) ||(cueY>right)) cueDY*=1;
    ;
    
purpleX+=purpleDX;  if((purpleX<left)||(purpleX>right)) purpleDX*=-1;
purpleY+=purpleDY; if((purpleY<left)||(purpleY>right)) purpleDY*=-1;
    ;
  
  greenX+=greenDX;  if((greenX<left)||(greenX>right)) greenDX*=-1;
 greenY+=greenDY; if((greenY<left)|| (greenY>right)) greenDY*=-1;
 ;

    orangeX+=orangeDX; if((orangeX<left)||(orangeX>right)) orangeDX*=-1;
    orangeY+=orangeDY; if((orangeY<left)||(orangeY>right)) orangeDY*=-1;
    ;
    

}

//show balls
void show(){
  fill(255);        ellipse(cueX,cueY,30,30);
  
  fill(204,110,0);  ellipse(orangeX,orangeY,30,30);
  
  fill(90,80,250);  ellipse(purpleX,purpleY,30,30);
  
  fill(50,200,0);   ellipse(greenX,greenY,30,30);  
}


//collision
void collision(){

if (dist(orangeX,orangeY,purpleX,purpleY)<30);
float tmp;
tmp=orangeDX; orangeDX=purpleDX; purpleDX=tmp;
tmp=orangeDY; orangeDY=purpleDY; purpleDY=tmp;
;

if(dist(greenX,greenY,purpleX,purpleY)<30)
  tmp=greenDX; greenDX=purpleDX; purpleDX=tmp;
  tmp=greenDY; greenDY=purpleDY; purpleDY=tmp;
;

if(dist(orangeX,orangeY,greenX,greenY)<30)
  tmp=orangeDX; orangeDX=greenDX; greenDX=tmp;
  tmp=orangeDY; orangeDY=greenDY; greenDY=tmp;
;

if(dist(cueX,cueY,orangeX,orangeY)<30)
  tmp=cueDX; cueDX=orangeDX; orangeDX=tmp;
  tmp=cueDY; cueDY=orangeDY; orangeDY=tmp;
  ;
  if(dist(cueX,cueY,purpleX,purpleY)<30)
  tmp=cueDX; cueDX=purpleDX;purpleDX=tmp;
  tmp=cueDY; cueDY=purpleDY;purpleDY=tmp;
  ;
  if(dist(cueX,cueY,greenX,greenY)<30)
  tmp=cueDX; cueDX=greenDX; greenDX=tmp;
  tmp=cueDY; cueDY=greenDY; greenDY=tmp;
}

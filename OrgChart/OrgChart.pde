int d = 40;
 
float xo;
float yo;
float zoom = 1;
float angle = 0;
 
void setup () {
  size (600, 200);
  xo = width/2;
  yo = height/2;
  smooth();
  noStroke();
}
 
void draw() {
  background(#1F7B9B);
  translate (xo, yo);
  scale (zoom);
  rotate (angle);
 
  fill(120);
  ellipse(-200, 0, d, d);
  fill(180);
  ellipse(-100, 0, d, d);
  fill(220);
  ellipse (0, 0, d, d);
  fill (180);
  ellipse (100,0,d,d);
  fill (120);
  ellipse (200,0,d,d);
}
 
void keyPressed() {
  if (key == CODED) {
    if (keyCode == UP) {
      zoom += .03;
    }
    else if (keyCode == DOWN) {
      zoom -= .03;
    }
    else if (keyCode == RIGHT) {
      angle += .03;
    }
    else if (keyCode == LEFT) {
      angle -= .03;
    }
  }
  if (key == 32)
  {
    angle = 0;
    zoom = 1;
    xo = width/2;
    yo = height/2;
  }
}
 
void mouseDragged(){
  xo= xo + (mouseX - pmouseX);
  yo = yo + (mouseY - pmouseY);
}
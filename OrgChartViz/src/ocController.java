import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import com.iragreenberg.*;

public class ocController extends PApplet {

	/**
	 *
	 * 
	 */
	//public ocIcon icon;
	public ocCollection collection;

	public ocIcon parentIcon;
	public ocIcon childIcon1;
	public ocIcon childIcon2;

	public void setup() {
		//surface.setResizable(true);
		//icon = new ocIcon(this, new PVector(200, 100), 23.0f, ocIconDetail.RECTANGLE);
		collection = new ocCollection(this, 6);
//		parentIcon = new ocIcon(this, new PVector(200, 100), 23.0f, ocIconDetail.CIRCLE);
//		childIcon1 = new ocIcon(this, new PVector(100, 300), 23.0f, ocIconDetail.CIRCLE, parentIcon);
//		childIcon2 = new ocIcon(this, new PVector(300, 300), 23.0f, ocIconDetail.CIRCLE, parentIcon);
		surface.setResizable(true);
		
	}

	public void draw() {
		background(255);
		//translate(width/2, height/2);
		//icon.display();
		collection.display();
		//println(icon.isHit(mouseX, mouseY));

//		parentIcon.display();
//		childIcon1.display();
//		childIcon2.display();
	}

	public void settings() {
		size(1220, 900);
		
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "ocController" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
	
	public void mouseReleased(){
		//icon.setIsDraggable(false);
		collection.setIsDraggable(false);

		//parentIcon.setIsDraggable(false);
		//childIcon1.setIsDraggable(false);
		//childIcon2.setIsDraggable(false);
	}
}

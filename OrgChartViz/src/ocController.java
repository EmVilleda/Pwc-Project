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
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

import com.iragreenberg.*;

public class ocController extends PApplet {
	public ocIcon icon;
	public ocCollection collection;

	public void setup() {
		//surface.setResizable(true);
		//icon = new ocIcon(this, new PVector(200, 100), 23.0f, ocIconDetail.RECTANGLE);
		collection = new ocCollection(this, 6);

		surface.setResizable(true);
	}

	public void draw() {
		background(255);
		collection.display();
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
		collection.setIsDraggable(false);
	}

}


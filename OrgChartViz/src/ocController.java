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

	public ocCollection collection;

	public void setup() {
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

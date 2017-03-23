package com.iragreenberg;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class ocIcon {

	//Data structure to make tree
	private List<ocIcon> children = new ArrayList<ocIcon>();
	private ocIcon parent = null;

	//Data included in an ocIcon
	public PApplet p;
	public PVector pos, initPos, startPos, currPos;
	public float radius, initRadius, zoomRadius;
	public ocIconDetail shape;
	public ArrayList<PVector> vecs;
	public int detail = 36;
	public boolean isResettable;

	public PVector offset = new PVector();
	public float offsetDamping = .9125f;

	public boolean isReleased = false;
	public boolean isDraggable;
	public boolean isExpandable;
	public boolean canZoom = false;

	PFont font;

	public ocIcon() {
	}

	//Parent
	public ocIcon(PApplet p, PVector pos, float radius, ocIconDetail shape) {
		//Setup
		this.p = p;
		this.pos = pos;
		initPos = new PVector(pos.x, pos.y);
		startPos = new PVector(initPos.x, initPos.y);
		currPos = initPos;
		// spd = new PVector
		this.radius = initRadius = radius;
		zoomRadius = radius;
		this.shape = shape;
		vecs = new ArrayList<PVector>();
		float theta = 0.0f;

		//Nodes
		switch (shape) {
			case CIRCLE:
				theta = 0;
				for (int i = 0; i < detail; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / detail;
				}
				break;
			case TRIANGLE:
				theta = -p.PI / 2.0f;
				for (int i = 0; i < 3; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / 3;
				}
				break;
			case SQUARE:
				theta = -p.PI / 4.0f;
				for (int i = 0; i < 4; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / 4;
				}
				break;
			case RECTANGLE:
				theta = -p.PI / 4.0f;
				for (int i = 0; i < 4; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f * 1.5f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / 4;
				}
				break;
			default:
				break;
		}
	}

	//Child ocIcons
	public ocIcon(PApplet p, PVector pos, float radius, ocIconDetail shape, ocIcon parent) {
		this.p = p;
		this.pos = pos;
		initPos = new PVector(pos.x, pos.y);
		startPos = new PVector(initPos.x, initPos.y);
		currPos = initPos;
		// spd = new PVector
		this.radius = initRadius = radius;
		this.shape = shape;
		this.parent = parent;
		vecs = new ArrayList<PVector>();
		float theta = 0.0f;
		switch (shape) {
			case CIRCLE:
				theta = 0;
				for (int i = 0; i < detail; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / detail;
				}
				break;
			case TRIANGLE:
				theta = -p.PI / 2.0f;
				for (int i = 0; i < 3; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / 3;
				}
				break;
			case SQUARE:
				theta = -p.PI / 4.0f;
				for (int i = 0; i < 4; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / 4;
				}
				break;
			case RECTANGLE:
				theta = -p.PI / 4.0f;
				for (int i = 0; i < 4; i++) {
					vecs.add(new PVector(p.cos(theta) * 1.0f * 1.5f, p.sin(theta) * 1.0f));
					theta += p.TWO_PI / 4;
				}
				break;
			default:
				break;
		}
	}

	public List<ocIcon> getChildren() {
		return children;
	}

	public void setParent(ocIcon parent) {
		this.parent = parent;
	}

	public void addChild(ocIcon child) {
		child.setParent(this);
		this.children.add(child);
	}

	public void addChild(PApplet p, PVector pos, float radius, ocIconDetail shape) {
		ocIcon child = new ocIcon(p, pos, radius, shape);
		child.setParent(this);
		children.add(child);
	}

	public PVector getVector() {
		return this.pos;
	}

	public float getRadius() {
		return this.radius;
	}

	public void display() {
		//Menu
		p.pushMatrix();
		p.fill(251, 231, 240);
		p.stroke(0);
		p.rect(90, p.height - 220, 300, 170);

		font = p.createFont("American Typewriter", 30, true);
		p.textFont(font, 16);
		p.fill(94, 35, 35);
		p.text("User controls:", 100, p.height - 200);
		p.text("Press and click on node to drag", 100, p.height - 180);
		p.text("r with mouse over: expand node", 100, p.height - 160);
		p.text("x: increase node radius", 100, p.height - 140);
		p.text("z: decrease node radius", 100, p.height - 120);
		p.text("r: reset size", 100, p.height - 100);
		p.text("Move image with arrow keys", 100, p.height - 80);
		p.text("Press spacebar to reset position", 100, p.height - 60);
		p.popMatrix();

		//Draw icons
		p.fill(253, 181, 43);
		p.strokeWeight(1.0f / radius);
		if(parent == null)
			p.fill(200, 200, 200);
		else
			p.fill(255, 200, 45);

		p.strokeWeight(1.0f/radius);
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		p.scale(zoomRadius);
		p.beginShape();
		for (PVector v : vecs) {
			p.vertex(v.x, v.y);
		}
		p.endShape(PApplet.CLOSE);
		p.popMatrix();

		//UI
		if (p.mousePressed && isHit()) {
			isDraggable = true; // set state NOT actual position of sprite
		} else {
			pos.x = initPos.x + offset.x;
			pos.y = initPos.y + offset.y;
			offset.mult(offsetDamping);
			//ocCollection.isSystemHitSafe = true;
		}

		if (p.mousePressed && isHit()){
			currPos.x = pos.x;
			currPos.y = pos.y;
		} else  {
			pos.x = currPos.x;
			pos.y = currPos.y;
			offset.mult(offsetDamping);
			//ocCollection.isSystemHitSafe = true;
		}

		if (isDraggable) {
			pos.x = p.mouseX;
			pos.y = p.mouseY;
			offset = new PVector(p.mouseX - initPos.x, p.mouseY - initPos.y);
			//ocCollection.isSystemHitSafe = false;
		}
		//offset = new PVector(p.mouseX-initPos.x, p.mouseY-initPos.y);
		//ocCollection.isSystemHitSafe = false;
		if (p.keyPressed) {
			if (p.key == ' ') { //reset to x and y positions
				isResettable = true;
				currPos.x = startPos.x;
				currPos.y = startPos.y;
			} else if (p.key == 'r') { //reset radius
				zoomRadius = radius;
			}

			if (p.key == p.CODED) {
				setCanZoom(true);

				if (p.keyCode == p.RIGHT) {
					p.scale(currPos.x += 20);
					//p.scale(currPos.y += 20);
				} else if (p.keyCode == p.LEFT) {
					p.scale(currPos.x -= 20);
					//p.scale(currPos.y += 20);
				} else if (p.keyCode == p.UP) {
					p.scale(currPos.y -= 20);

					//p.scale(currPos.y += 20);
				} else if (p.keyCode == p.DOWN) {
					p.scale(currPos.y += 20);
				}
			}
			if (p.key == 'x') {
				if (zoomRadius < 100) {
					p.scale(zoomRadius += 0.5);
				} else if (zoomRadius >= 100) {
					p.scale(zoomRadius);
				}
			} else if (p.key == 'z') {
				if (zoomRadius <= 20) {
					p.scale(zoomRadius);
				} else if (zoomRadius > 20) {
					p.scale(zoomRadius -= 0.5);
				}
			}
		}
	}

	public boolean isHit() {
		if (p.dist(p.mouseX, p.mouseY, pos.x, pos.y) < radius) {

			return true;
		}
		return false;
	}

	public void setIsDraggable(boolean isDraggable) {
		this.isDraggable = isDraggable;
	}

	public void setIsExpandable(boolean isExpandable) {
		this.isExpandable = isExpandable;
	}

	public void setCanZoom(boolean canZoom) { this.canZoom = canZoom; }

}
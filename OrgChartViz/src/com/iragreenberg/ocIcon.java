package com.iragreenberg;

import java.util.List;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class ocIcon {

	/**
	 *
	 */
	//Data structure to make tree
	private List<ocIcon> children = new ArrayList<ocIcon>();
	private ocIcon parent = null;

	//Data included in an ocIcon
	public PApplet p;
	public PVector pos, initPos, startPos, currPos;
	public float radius, initRadius;
	public ocIconDetail shape;
	public ArrayList<PVector> vecs;
	public int detail = 36;
	public boolean isBeingHit = false;
	public boolean isResettable;
	public float spd = 10.2f;
	public float expandSpd = 10.2f;
	public float expansionRadius = 0.0f;
	public PVector resetVector = new PVector();
	public PVector temp = new PVector();

	public PVector offset = new PVector();
	public float offsetDamping = .9125f;
	
	public boolean isReleased = false;
	public boolean isDraggable;
	public boolean isExpandable;

	public ocIcon() {
	}

	//Child Parent
	public ocIcon(PApplet p, PVector pos, float radius, ocIconDetail shape) {
		this.p = p;
		this.pos = pos;
		initPos = new PVector(pos.x, pos.y);
		startPos = new PVector(initPos.x, initPos.y);
		currPos = initPos;
		// spd = new PVector
		this.radius = initRadius = radius;
		this.shape = shape;
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
		parent.addChild(this);
		this.parent = parent;
	}

	public void addChild(PApplet p, PVector pos, float radius, ocIconDetail shape) {
		ocIcon child = new ocIcon(p, pos, radius, shape);
		child.setParent(this);
		this.children.add(child);
	}

	public void addChild(ocIcon child) {
		child.setParent(this);
		this.children.add(child);
	}

	public PVector getVector() {
		return this.pos;
	}

	public float getRadius() {
		return this.radius;
	}

	public void display() {
		p.fill(255, 200, 45);
		p.strokeWeight(1.0f/radius);
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		p.scale(radius);
		p.beginShape();
		for (PVector v : vecs) {
			p.vertex(v.x, v.y);
		}
		p.endShape(PApplet.CLOSE);
		p.popMatrix();

		if (p.mousePressed && isHit()) {
			isDraggable = true; // set state NOT actual position of sprite
			currPos.x = pos.x;
			currPos.y = pos.y;
		} else {
				pos.x = currPos.x;
				pos.y = currPos.y;
				offset.mult(offsetDamping);
				//ocCollection.isSystemHitSafe = true;
		}

		if (isDraggable){
			pos.x = p.mouseX;
			pos.y = p.mouseY;
			//offset = new PVector(p.mouseX-initPos.x, p.mouseY-initPos.y);
			//ocCollection.isSystemHitSafe = false;
		}

		if (p.keyPressed)
		{
			isResettable = true;

//			System.out.println(pos.x);
//			System.out.println(initPos.x);
//			System.out.println(startPos.x);

			//Return to current position
			currPos.x = startPos.x;
			currPos.y = startPos.y;
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


}

package com.iragreenberg;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class ocIcon {

	/**
	 *
	 */
	public PApplet p;
	public PVector pos, initPos;
	public float radius, initRadius;
	public float growthFactor = 0.0f;
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

	public ocIcon(PApplet p, PVector pos, float radius, ocIconDetail shape) {
		this.p = p;
		this.pos = pos;
		initPos = new PVector(pos.x, pos.y);
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

//		if (p.mousePressed && isHit()) {
//			isDraggable = true; // set state NOT actual position of sprite
//		} else {
//				pos.x = initPos.x + offset.x;
//				pos.y = initPos.y + offset.y;
//				offset.mult(offsetDamping);
//				//ocCollection.isSystemHitSafe = true;
//		}
//		
//		if (isDraggable){
//			pos.x = p.mouseX;
//			pos.y = p.mouseY;
//			offset = new PVector(p.mouseX-initPos.x, p.mouseY-initPos.y);
//			//ocCollection.isSystemHitSafe = false;
//		}

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

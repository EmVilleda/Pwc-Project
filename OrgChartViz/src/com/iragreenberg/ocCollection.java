package com.iragreenberg;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class ocCollection {

	public int entities = 4; // default
	public ocIcon rootIcon;
	private PApplet p;
	public float margin = 100.0f;
	public boolean isSystemHitSafe = true;
	public boolean isSystemExpandableSafe = true;
	public float expandSpeed = 30.5f;

	public ocCollection() {
	}

	// PApplet p, PVector pos, float radius, ocIconDetail shape
	public ocCollection(PApplet p, int entities) {
		this.p = p;
		this.entities = entities;
		float iconW = p.width / (entities * 2);
		float gap = (p.width - margin * 2.0f - iconW * entities) / (entities - 1);

		if (entities > 1) {
			rootIcon = new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.RECTANGLE);
			for (int i = 0; i < entities - 1; i++) {
				rootIcon.addChild(p, new PVector(margin + iconW / 1.0f + (iconW + gap) * (i), p.height / 1.9f), iconW / 2.0f, ocIconDetail.TRIANGLE);
			}
		}
		else {
			rootIcon = new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.RECTANGLE);
		}

		// set all nodes draggable - TO DO: add UI Controls
		rootIcon.setIsDraggable(false);
		for (int i = 0; i < entities-1; i++) {
			rootIcon.getChildren().get(i).setIsDraggable(false);
		}
	}

	public void display() {

		/*
            //connect icons
			if (i + 1 < entities) {
				p.stroke(0);
				p.strokeWeight(1);
				p.line(icon.pos.x + icon.radius, icon.pos.y, icons.get(i+1).pos.x - icon.radius, icons.get(i+1).pos.y);
				//TO DO: make it so that icons are still draggable and expandable when connected
			}

		}*/

		if (p.mousePressed && rootIcon.isHit() && isSystemHitSafe) {
			rootIcon.isDraggable = true; // set state NOT actual position of sprite
			isSystemHitSafe = false;
		} else {
			rootIcon.pos.x = rootIcon.initPos.x + rootIcon.offset.x;
			rootIcon.pos.y = rootIcon.initPos.y + rootIcon.offset.y;
			rootIcon.offset.mult(rootIcon.offsetDamping);
		}

		if (rootIcon.isDraggable) {
			rootIcon.pos.x = p.mouseX;
			rootIcon.pos.y = p.mouseY;
			rootIcon.offset = new PVector(p.mouseX - rootIcon.initPos.x, p.mouseY - rootIcon.initPos.y);
		}


		if (rootIcon.isHit() && isSystemExpandableSafe) {
			rootIcon.isExpandable = true;
			isSystemExpandableSafe = false;
		} else {
			rootIcon.radius = rootIcon.initRadius;
			rootIcon.offset.mult(rootIcon.offsetDamping);
		}

		p.pushMatrix();
		rootIcon.setIsExpandable(true);
		if (rootIcon.isExpandable && rootIcon.radius < 100 && rootIcon.isHit()) {
			rootIcon.radius += expandSpeed;
		}
		rootIcon.display();
		p.popMatrix();

		for (int i = 0; i < entities-1; i++) {
			ocIcon icon = rootIcon.getChildren().get(i);

			if (p.mousePressed && icon.isHit() && isSystemHitSafe) {
				icon.isDraggable = true; // set state NOT actual position of sprite
				isSystemHitSafe = false;
			} else {
				icon.pos.x = icon.initPos.x + icon.offset.x;
				icon.pos.y = icon.initPos.y + icon.offset.y;
				icon.offset.mult(icon.offsetDamping);
			}

			if (icon.isDraggable) {
				icon.pos.x = p.mouseX;
				icon.pos.y = p.mouseY;
				icon.offset = new PVector(p.mouseX - icon.initPos.x, p.mouseY - icon.initPos.y);
			}


			if (icon.isHit() && isSystemExpandableSafe) {
				icon.isExpandable = true;
				isSystemExpandableSafe = false;
			} else {
				icon.radius = icon.initRadius;
				icon.offset.mult(icon.offsetDamping);
			}

			p.pushMatrix();
			icon.setIsExpandable(true);
			if (icon.isExpandable && icon.radius < 100 && icon.isHit()) {
				icon.radius += expandSpeed;
			}
			icon.display();
			p.popMatrix();
		}
	}


	public void setAreIconsDraggable(int id) {

	}

	public void setIsDraggable(boolean isDraggable) {
		if (!isDraggable) {
			isSystemHitSafe = false;
		}
		rootIcon.setIsDraggable(isSystemHitSafe);
		for (int i = 0; i < entities-1; i++) {
			rootIcon.getChildren().get(i).setIsDraggable(isSystemHitSafe);
		}
	}

	public void setIsExpandable(boolean isExpandable) {
		if (!isExpandable) {
			isSystemExpandableSafe = true;
		}
		rootIcon.setIsExpandable(isSystemExpandableSafe);
		for (int i = 0; i < entities-1; i++) {
			rootIcon.getChildren().get(i).setIsExpandable(isSystemExpandableSafe);
		}
	}

}

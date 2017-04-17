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
	public float expandSpeed = 30.0f;
	public float initScaleFactor; // initially scales nodes per node count
	public boolean canZoom = true;

	public ocCollection() {}

	// PApplet p, PVector pos, float radius, ocIconDetail shape
	public ocCollection(PApplet p, int entities) {
		this.p = p;
		this.entities = entities;
		float iconW = p.width / (entities * 2);
		float gap = (p.width - margin * 2.0f - iconW * entities) / (entities);

		if (entities > 1 && gap > 100) { //if there are too many shapes to properly be displayed on screen (shapes will be too small, gaps too small))
			iconW = 50;
			gap = 50;
			rootIcon = new ocIcon(p, new PVector(margin + iconW / 1.0f , p.height / 2.0f - 100), iconW / 2.0f, ocIconDetail.TRIANGLE);
			for (int i = 0; i < entities; i++) {
				rootIcon.addChild(p, new PVector(margin + iconW / 1.0f + (iconW + gap) * (i), p.height / 1.9f), iconW / 2.0f, ocIconDetail.RECTANGLE);
			}
			rootIcon.isParent();

			for (int i = 0; i < entities; i++) {
				ocIcon children = rootIcon.getChildren().get(i);
				children.addChild(p, new PVector(500 + 100*i, 100), iconW / 2.0f, ocIconDetail.RECTANGLE);
				rootIcon.getChildren().get(i).isParent();
			}
			System.out.println("CASE 1");
		} else if (entities > 1 && gap <= 100) {
			rootIcon = new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.TRIANGLE);
			for (int i = 0; i < entities; i++) {
				rootIcon.addChild(p, new PVector(margin + iconW / 1.0f + (iconW + gap) * (i), p.height / 1.9f), iconW / 2.0f, ocIconDetail.RECTANGLE);
				rootIcon.isChild();
				System.out.println("CASE 2");
			}

		}

		else if(entities == 1) {
			rootIcon = new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.RECTANGLE);
			rootIcon.isParent();
			System.out.println("CASE 3");
		}
		else {
			for (int i = 0; i < entities; i++) {
				rootIcon = new ocIcon(p, new PVector(margin + iconW / 2.0f + (iconW + 100) * (i), p.height / 2.0f + (iconW + 100)), 30, ocIconDetail.RECTANGLE);
			}
			System.out.println("CASE 4");
		}

		rootIcon.setIsDraggable(false);
		for (int i = 0; i < entities; i++) {
			rootIcon.getChildren().get(i).setIsDraggable(false);
		}
	}

	public void display() {

		for (int i = 0; i < entities; i++) {
			ocIcon icon = rootIcon.getChildren().get(i);
			//ocIcon icon2 = rootIcon.getChildren().get(i).getChildren().get(0);

			if (p.mousePressed && icon.isHit() && isSystemHitSafe) {
				icon.isDraggable = true; // set state NOT actual position of sprite
//				icon2.isDraggable = true;
				isSystemHitSafe = false;
			} else {
				icon.pos.x = icon.initPos.x + icon.offset.x;
				icon.pos.y = icon.initPos.y + icon.offset.y;
				icon.offset.mult(icon.offsetDamping);

//				icon2.pos.x = icon.initPos.x + icon.offset.x;
//				icon2.pos.y = icon.initPos.y + icon.offset.y;
//				icon2.offset.mult(icon.offsetDamping);
			}

			if (icon.isDraggable) {
				icon.pos.x = p.mouseX;
				icon.pos.y = p.mouseY;
				icon.offset = new PVector(p.mouseX - icon.initPos.x, p.mouseY - icon.initPos.y);
			}

//			if (icon2.isDraggable) {
//				icon.pos.x = p.mouseX;
//				icon.pos.y = p.mouseY;
//				icon.offset = new PVector(p.mouseX - icon.initPos.x, p.mouseY - icon.initPos.y);
//			}

			if (icon.isHit() && isSystemExpandableSafe) {
				icon.isExpandable = true;
				isSystemExpandableSafe = false;
			} else {
				icon.radius = icon.initRadius;
				icon.offset.mult(icon.offsetDamping);
			}

//			if (icon2.isHit() && isSystemExpandableSafe) {
//				icon2.isExpandable = true;
//				isSystemExpandableSafe = false;
//			} else {
//				icon2.radius = icon2.initRadius;
//				icon2.offset.mult(icon2.offsetDamping);
//			}

			p.pushMatrix();

			icon.setIsExpandable(true);
//			icon2.setIsExpandable(true);
			if (icon.isExpandable && icon.radius < 100 && icon.isHit()) {
				icon.radius = icon.initRadius + expandSpeed;
			}
//			if (icon2.isExpandable && icon2.radius < 100 && icon2.isHit()) {
//				icon2.radius = icon2.initRadius + expandSpeed;
//			}

			//only drawling 5 lines because it starts at 1; need to fix this
			if (i < entities) {
				p.stroke(94, 35, 35);
				p.strokeWeight(1);
				p.line(rootIcon.pos.x + rootIcon.zoomRadius - expandSpeed, rootIcon.pos.y, rootIcon.getChildren().get(i).pos.x - icon.zoomRadius + expandSpeed, rootIcon.getChildren().get(i).pos.y);
			}
			icon.display();
//			icon2.display();
			p.popMatrix();
		}

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
	}


	public void setAreIconsDraggable(int id) {}

	public void setIsDraggable(boolean isDraggable) {
		if (!isDraggable) {
			isSystemHitSafe = false;
		}
		rootIcon.setIsDraggable(isSystemHitSafe);
		for (int i = 0; i < entities; i++) {
			rootIcon.getChildren().get(i).setIsDraggable(isSystemHitSafe);
//			rootIcon.getChildren().get(i).getChildren().get(i).setIsDraggable(isSystemHitSafe);
		}
	}

	public void setIsExpandable(boolean isExpandable) {
		if (!isExpandable) {
			isSystemExpandableSafe = true;
		}
		rootIcon.setIsExpandable(isSystemExpandableSafe);
		for (int i = 0; i < entities; i++) {
			rootIcon.getChildren().get(i).setIsExpandable(isSystemExpandableSafe);
//			rootIcon.getChildren().get(i).getChildren().get(i).setIsExpandable(isSystemExpandableSafe);
		}
	}

	public void setCanZoom(boolean canZoom) {
		for(int i = 0; i < rootIcon.getChildren().size(); i++) {
			rootIcon.getChildren().get(i).setCanZoom(canZoom);
		}
		for(int i = 0; i < rootIcon.getChildren().get(i).getChildren().size(); i++) {
			rootIcon.getChildren().get(i).setCanZoom(canZoom);
//			rootIcon.getChildren().get(i).getChildren().get(i).setCanZoom(canZoom);
		}
	}
//	public void drawLines(){
//		for (int i = 0; i < rootIcon.getChildren().size(); i++) {
//			ocIcon icon = rootIcon.getChildren().get(i);
//			p.stroke(0);
//			p.strokeWeight(1);
//			p.line(icon.pos.x, icon.pos.y, rootIcon.getChildren().get(i + 1).pos.x, rootIcon.getChildren().get(i + 1).pos.y);
//		}
//	}
}
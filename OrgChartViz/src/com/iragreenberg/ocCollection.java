package com.iragreenberg;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class ocCollection {

	public int entities = 4; // default
	//public ArrayList<ocIcon> icons = new ArrayList<ocIcon>();
	public ocIcon rootIcon;
	public ArrayList<Boolean> areIconsDraggable = new ArrayList<Boolean>();
	private PApplet p;
	public float margin = 100.0f;
	public boolean isSystemHitSafe = true;
	public boolean isSystemExpandableSafe = true;
	public float expandSpeed = 30.5f;
	public float initScaleFactor; // initially scales nodes per node count

	public ocCollection() {
	}

	// PApplet p, PVector pos, float radius, ocIconDetail shape
	public ocCollection(PApplet p, int entities) {
		this.p = p;
		this.entities = entities;
		float iconW = p.width / (entities * 2);
		float gap = (p.width - margin * 2.0f - iconW * entities) / (entities - 1);

		// if > 1 node, automatically evenly space along x-axis
		/*if (entities > 1) {
			icons.add(new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f,
					ocIconDetail.CIRCLE));
			for (int i = 1; i < entities - 1; i++) {
				icons.add(new ocIcon(p, new PVector(margin + iconW / 2.0f + (iconW + gap) * (i), p.height / 2.0f),
						iconW / 2.0f, ocIconDetail.CIRCLE));
			}
			icons.add(new ocIcon(p, new PVector(p.width - margin - iconW / 2.0f, p.height / 2.0f), iconW / 2.0f,
					ocIconDetail.CIRCLE));
			// center singleton case
		} else {
			icons.add(new ocIcon(p, new PVector(p.width / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.CIRCLE));
		}

		// set all nodes draggable - TO DO: add UI Controls
		for (int i = 0; i < icons.size(); i++) {
			icons.get(i).setIsDraggable(false);
		}*/

		if (entities > 1) {
			rootIcon = new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.CIRCLE);
			for (int i = 0; i < entities - 1; i++) {
				rootIcon.addChild(p, new PVector(margin + iconW / 1.0f + (iconW + gap) * (i), p.height / 1.9f), iconW / 2.0f, ocIconDetail.CIRCLE);
			}
			rootIcon.addChild(p, new PVector(p.width - margin - iconW / 2.0f, p.height / 1.9f), iconW / 2.0f, ocIconDetail.CIRCLE);
			// center singleton case
		}
		else {
			rootIcon = new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.CIRCLE);
		}

		// set all nodes draggable - TO DO: add UI Controls
		rootIcon.setIsDraggable(false);
		for (int i = 0; i < entities-1; i++) {
			rootIcon.getChildren().get(i).setIsDraggable(false);
		}
	}

	public void display() {

		/*for (int i = 0; i < icons.size(); i++) {

			ocIcon icon = icons.get(i);

			if (p.mousePressed && icon.isHit() && isSystemHitSafe) {
				icon.isDraggable = true; // set state NOT actual position of
											// sprite
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
            icons.get(i).setIsExpandable(true);
			if (icon.isExpandable && icon.radius < 100 && icon.isHit()) {
				icon.radius += expandSpeed;
			}

			icons.get(i).display();

            p.popMatrix();

            //connect icons
			if (i + 1 < entities) {
				p.stroke(0);
				p.strokeWeight(1);
				p.line(icon.pos.x + icon.radius, icon.pos.y, icons.get(i+1).pos.x - icon.radius, icons.get(i+1).pos.y);
				//TO DO: make it so that icons are still draggable and expandable when connected
			}


		}*/

		if (p.mousePressed && rootIcon.isHit() && isSystemHitSafe) {
			rootIcon.isDraggable = true; // set state NOT actual position of
			// sprite
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

			if (p.mousePressed && rootIcon.getChildren().get(i).isHit() && isSystemHitSafe) {
				rootIcon.getChildren().get(i).isDraggable = true; // set state NOT actual position of
				// sprite
				isSystemHitSafe = false;
			} else {
				rootIcon.getChildren().get(i).pos.x = rootIcon.getChildren().get(i).initPos.x + rootIcon.getChildren().get(i).offset.x;
				rootIcon.getChildren().get(i).pos.y = rootIcon.getChildren().get(i).initPos.y + rootIcon.getChildren().get(i).offset.y;
				rootIcon.getChildren().get(i).offset.mult(rootIcon.getChildren().get(i).offsetDamping);
			}

			if (rootIcon.getChildren().get(i).isDraggable) {
				rootIcon.getChildren().get(i).pos.x = p.mouseX;
				rootIcon.getChildren().get(i).pos.y = p.mouseY;
				rootIcon.getChildren().get(i).offset = new PVector(p.mouseX - rootIcon.getChildren().get(i).initPos.x, p.mouseY - rootIcon.getChildren().get(i).initPos.y);
			}


			if (rootIcon.getChildren().get(i).isHit() && isSystemExpandableSafe) {
				rootIcon.getChildren().get(i).isExpandable = true;
				isSystemExpandableSafe = false;
			} else {
				rootIcon.getChildren().get(i).radius = rootIcon.getChildren().get(i).initRadius;
				rootIcon.getChildren().get(i).offset.mult(rootIcon.getChildren().get(i).offsetDamping);
			}

			p.pushMatrix();
			rootIcon.getChildren().get(i).setIsExpandable(true);
			if (rootIcon.getChildren().get(i).isExpandable && rootIcon.getChildren().get(i).radius < 100 && rootIcon.getChildren().get(i).isHit()) {
				rootIcon.getChildren().get(i).radius += expandSpeed;
			}

			rootIcon.getChildren().get(i).display();

			p.popMatrix();

			//connect icons
			/*if (i + 1 < entities-1) {
				p.stroke(0);
				p.strokeWeight(1);
				p.line(each.pos.x + each.radius, each.pos.y, each.get(i+1).pos.x - each.radius, each.get(i+1).pos.y);
				//TO DO: make it so that icons are still draggable and expandable when connected
			}*/


		}
	}


	public void setAreIconsDraggable(int id) {

	}

	public void setIsDraggable(boolean isDraggable) {
		if (!isDraggable) {
			isSystemHitSafe = true;
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

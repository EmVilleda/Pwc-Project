package com.iragreenberg;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class ocCollection {

	public int entities = 4; // default
	public ArrayList<ocIcon> icons = new ArrayList<ocIcon>();
	public ArrayList<Boolean> areIconsDraggable = new ArrayList<Boolean>();
	private PApplet p;
	public float margin = 100.0f;
	public boolean isSystemHitSafe = true;
	public boolean isSystemExpandableSafe = true;
	public float expandSpeed = 6.5f;
	// public float initScaleFactor; // initially scales nodes per node count

	public ocCollection() {
	}

	// PApplet p, PVector pos, float radius, ocIconDetail shape
	public ocCollection(PApplet p, int entities) {
		this.p = p;
		float iconW = p.width / (entities * 2);
		float gap = (p.width - margin * 2.0f - iconW * entities) / (entities - 1);

		// if > 1 node, automatically evenly space along x-axis
		if (entities > 1) {
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
		}
	}

	public void display() {

		for (int i = 0; i < icons.size(); i++) {

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
				// icon.radius = icon.initRadius;
				// icon.offset.mult(icon.offsetDamping);
			}

			p.pushMatrix();
			if (icon.isExpandable && icon.radius < 100) {
				icon.radius += expandSpeed;
			}

			icons.get(i).display();
			p.popMatrix();
		}
	}

	public void setAreIconsDraggable(int id) {

	}

	public void setIsDraggable(boolean isDraggable) {
		if (!isDraggable) {
			isSystemHitSafe = true;
		}
		for (int i = 0; i < icons.size(); i++) {
			icons.get(i).setIsDraggable(isDraggable);
		}
	}

	public void setIsExpandable(boolean isExpandable) {
		if (!isExpandable) {
			isSystemExpandableSafe = true;
		}
		for (int i = 0; i < icons.size(); i++) {
			icons.get(i).setIsExpandable(isExpandable);
		}
	}

}

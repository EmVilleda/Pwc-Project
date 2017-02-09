package com.iragreenberg;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class ocCollection {

	public int entities = 12;
	public ArrayList<ocIcon> icons = new ArrayList<ocIcon>();
	public ArrayList<Boolean> areIconsDraggable = new ArrayList<Boolean>();
	private PApplet p;
	public float margin = 100.0f;
	public boolean isSystemHitSafe = true;
	public boolean isSystemExpandableSafe = true;
	public float nodeExpansionRate = 3.0f;
	public float nodeExpansionFactor = 3.0f;

	public ocCollection() {
	}

	// PApplet p, PVector pos, float radius, ocIconDetail shape
	public ocCollection(PApplet p, int entities) {
		this.p = p;
		float iconW = 60.0f;
		float gap = (p.width - margin * 2.0f - iconW * entities) / (entities - 1);

		if (entities > 1) {
			icons.add(new ocIcon(p, new PVector(margin + iconW / 2.0f, p.height / 2.0f), iconW / 2.0f,
					ocIconDetail.CIRCLE));
			for (int i = 1; i < entities - 1; i++) {
				icons.add(new ocIcon(p, new PVector(margin + iconW / 2.0f + (iconW + gap) * (i), p.height / 2.0f),
						iconW / 2.0f, ocIconDetail.CIRCLE));
			}
			icons.add(new ocIcon(p, new PVector(p.width - margin - iconW / 2.0f, p.height / 2.0f), iconW / 2.0f,
					ocIconDetail.CIRCLE));
		} else {
			icons.add(new ocIcon(p, new PVector(p.width / 2.0f, p.height / 2.0f), iconW / 2.0f, ocIconDetail.CIRCLE));
		}

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

			if (icon.isHit()) {
				expand(i);
				//icon.isExpandable = true;
			} else {
				icon.radius = icon.initRadius;
			}

			p.pushMatrix();
//			if (icon.isExpandable) {
//
//				//expand(i);
//			}

			icons.get(i).display();
			p.popMatrix();
		}
	}

	// grow all nodes based on proximity to hit node
	public void expand(int id) {
		if (icons.get(id).radius < icons.get(id).initRadius * nodeExpansionFactor) {
			for (int i = 0; i < icons.size(); i++) {
				if (i == id) {
					icons.get(i).radius += nodeExpansionRate;
				} 
					icons.get(i).radius += nodeExpansionRate;

			}
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

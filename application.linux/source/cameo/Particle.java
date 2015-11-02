package cameo;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
	PApplet p;
	
	PVector pos;
	float deltaX;
	float deltaY;
	
	float sizeX;
	float sizeY;
	
	float alphaX;
	float alphaY;
	
	float lineAlphaX;
	float lineAlphaY;
	
	float lineAlpha;
	float lineSize;
	
	boolean canUpdate;
	
	Particle(){}
	
	Particle(float _x, float _y, PApplet _p){
		p = _p;
		pos = new PVector(_x, _y);	
		sizeX = 5;
		sizeY = 5;
		
		deltaX = 0;
		deltaY = 0;
		
		canUpdate = true;
	}
	
	void update(){
		if(canUpdate){
			deltaY = PApplet.sin(Cameo.cosValY)*Cameo.cosCoeffY;
			sizeY = Cameo.sizeX;
			alphaY = Cameo.alphaY;
			lineAlphaY = Cameo.lineAlphaY;
			
			deltaX = PApplet.cos(Cameo.cosValX)*Cameo.cosCoeffX;
			sizeX = Cameo.sizeX;
			alphaX = Cameo.alphaX;
			lineAlphaX = Cameo.lineAlphaX;
			
			lineAlpha = Cameo.lineAlpha;
			lineSize = Cameo.lineSize;	
		}
	}
	
	void display(){
		
		p.noStroke();
		p.fill(255, alphaY);

		//p.stroke(0, alphaY);
		float y = pos.y+deltaY;

		p.rect(pos.x, y, sizeY, sizeY);
		
		p.fill(255, alphaX);
		//p.stroke(0, alphaX);
		float x = pos.x+deltaX;

		p.rect(x, pos.y, sizeX, sizeX);
		
		p.strokeWeight(Cameo.lineSize);
		p.stroke(255, lineAlphaY);
		p.line(pos.x, y, pos.x, pos.y);
		
		p.stroke(255, lineAlphaX);
		p.line(pos.x, pos.y, x, pos.y);
		
		p.stroke(255, lineAlpha);
		p.line(pos.x, y, x, pos.y);
	}
}

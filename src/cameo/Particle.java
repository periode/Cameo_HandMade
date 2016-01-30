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
	
	static float fillX = 0;
	static float fillY = 0;
	
	float thetaX;
	float thetaY;
	
	float alphaX;
	float alphaY;
	
	float lineAlphaX;
	float lineAlphaY;
	
	float lineAlpha;
	float lineSize;
	
	boolean canUpdate;
	
	static float thetaX_stabilizer = 1;
	static float thetaY_stabilizer = 1;
	
	Particle(){}
	
	Particle(float _x, float _y, PApplet _p){
		p = _p;
		pos = new PVector(_x, _y);	
		sizeX = 1;
		sizeY = 1;
		
		alphaX = 0;
		alphaY = 0;
		
		deltaX = 0;
		deltaY = 0;
		
		canUpdate = true;
	}
	
	void update(){
		if(canUpdate){
			deltaY = PApplet.sin(Cameo.cosValY)*Cameo.cosCoeffY;
			sizeY = Cameo.sizeY;
			thetaY = Cameo.thetaY;
			alphaY = Cameo.alphaY;
			lineAlphaY = Cameo.lineAlphaY;
			
			deltaX = PApplet.cos(Cameo.cosValX)*Cameo.cosCoeffX;
			sizeX = Cameo.sizeX;
			thetaX = Cameo.thetaX;
			alphaX = Cameo.alphaX;
			lineAlphaX = Cameo.lineAlphaX;
			
			lineAlpha = Cameo.lineAlpha;
			lineSize = Cameo.lineSize;
		}
	}
	
	void display(){
		p.rectMode(PApplet.CENTER);
		p.strokeWeight(lineSize);
		p.stroke(255, alphaY);
		p.fill(255, fillY);

		float y = pos.y+deltaY;
		
		p.pushMatrix();
		p.translate(pos.x, y);
		p.rotate(-thetaY*thetaY_stabilizer);
		p.rect(0, 0, sizeY, sizeY);
		p.popMatrix();
		
		p.fill(255, fillX);
		p.stroke(255, alphaX);
		float x = pos.x+deltaX;

		p.pushMatrix();
		p.translate(x, pos.y);
		p.rotate(thetaX*thetaX_stabilizer);
		p.rect(0, 0, sizeX, sizeX);
		p.popMatrix();
		
		p.strokeWeight(Cameo.lineSize);
		p.stroke(255, lineAlphaY);
		p.line(pos.x, y, pos.x, pos.y);
		
		p.stroke(255, lineAlphaX);
		p.line(pos.x, pos.y, x, pos.y);
		
		p.stroke(255, lineAlpha);
		p.line(pos.x, y, x, pos.y);
	}
}

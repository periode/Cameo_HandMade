package cameo;

import processing.core.PApplet;
import processing.core.PVector;

public class Square {
	PApplet p;
	PVector origin;
	float w;
	float h;
	PVector[] v;
	
	int colorOut;
	int colorIn;
	
	float scaleIn;
	float scaleOut;
	float scaleIncIn;
	float scaleIncOut;

	boolean canBeat;
	
	Square(PVector _origin, float _w, float _h, float _distortion, int _colOut, PApplet _p){
		p = _p;
		origin = _origin;
		w = _w*0.25f;
		h = _h*0.25f;
		w = PApplet.min(w, h);
		v = new PVector[4];
		v[0] = new PVector(-w*0.5f, -h*0.5f);
		v[1] = new PVector(+w*0.5f, -h*0.5f);
		v[2] = new PVector(+w*0.5f, +h*0.5f);
		v[3] = new PVector(-w*0.5f, +h*0.5f);
		
		colorOut = _colOut;
		colorIn = p.color(0);
		
		scaleIn = 0;
		scaleIncIn = 0.065f;
		scaleOut = 0;
		scaleIncOut = 0.225f;
		canBeat = false;
	}
	
	
	void update(){
		if(scaleOut < 1)
			scaleOut += scaleIncOut;
		else
			scaleOut = 1;
		
		scaleOut = p.min(scaleOut, 1);
	}
	
	void display(){
		p.fill(colorOut);
		p.stroke(0);
		//p.strokeWeight(1);
		p.noStroke();
		p.pushMatrix();
		p.translate(origin.x, origin.y);
		p.rotate(PApplet.PI/4);
		p.scale(scaleOut);
//		p.beginShape();
//		for(int i = 0; i < v.length; i++){
//			p.vertex(v[i].x, v[i].y);
//		}
//		p.endShape(PApplet.CLOSE);
		p.ellipse(0, 0, w, h);
		p.popMatrix();
		
		//p.println(scaleOut);
		if(canBeat)
			beat();
	}
	
	void beat(){
		if(scaleIn < 1){
			p.fill(0);
			p.stroke(255);
			//p.strokeWeight(1);
			p.noStroke();
			p.pushMatrix();
			p.translate(origin.x, origin.y);
			p.scale(scaleIn);
			p.beginShape();
			for(int i = 0; i < v.length; i++){
				p.vertex(v[i].x, v[i].y);
			}
			p.endShape(PApplet.CLOSE);
			p.popMatrix();
			
			scaleIn += scaleIncIn;
		}else{
			canBeat = false;
			scaleIn = 0;
		}
		
	}
}

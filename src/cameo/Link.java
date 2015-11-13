package cameo;

import processing.core.PApplet;
import processing.core.PVector;

public class Link {
	PApplet p;
	
	int type;
	
	PVector pos;
	PVector origin;
	PVector current1;
	PVector current2;
	PVector destination1;
	PVector destination2;
	
	float speed;
	
	int color;
	float alpha;
	float sw;
	
	float lerpVal;
	float lerpInc;
	
	boolean canConnect;
	boolean decrease;
	
	float currentVariation;
	float currentOffset;
	float originOffset;
	
	float currentOVal;
	float currentOPeriod;
	float originOVal;
	float originOPeriod;
	
	float currentDVal;
	float currentDPeriod;
	float originDVal;
	float originDPeriod;
	
	boolean canUpdate;
	
	Link(){
		
	}
	
	Link(PVector _o, PVector _d, float _s, int _c, int _dir, PApplet _p){
		p = _p;
		type = _dir;
		pos = _o;
		if(type == 0){
			origin = new PVector(pos.x + Cameo.xStep*0.5f, pos.y);
			current1 = origin;
			current2 = origin;
			destination1 = new PVector(pos.x + Cameo.xStep, pos.y);
			destination2 = pos;
		}else{
			origin = new PVector((pos.x+_d.x)*0.5f, (pos.y+_d.y)*0.5f);
			current1 = origin;
			current2 = origin;
			destination1 = _d;	
			destination2 = pos;
		}
		speed = _s;
		color = _c;
		alpha = 255;
		sw = 1;
		
		lerpVal = 0;
		lerpInc = speed;
		
		currentDPeriod = (int)(p.random(0, 4))*(90);
		originDPeriod = (int)(p.random(0, 4))*(90);
		
		currentOPeriod = (int)(p.random(0, 4))*(90);
		originOPeriod = (int)(p.random(0, 4))*(90);
		
		canConnect = true;
		decrease = false;
		canUpdate = true;
	}
	
	void update(){
		if(canUpdate){
			if(lerpVal < 1){
				current1 = PVector.lerp(origin, destination1, lerpVal);
				current2 = PVector.lerp(origin, destination2, lerpVal);
				lerpVal += lerpInc;
			}else if(lerpVal >= 1-lerpInc){
				if(canConnect)
					connect();
			}
	
			if(type == 0){
				lerpInc = Cameo.linksOLerpInc;
				sw = Cameo.linksOStrokeWeight;
				alpha = Cameo.linksOBrightness;
				currentOffset = PApplet.cos(currentOVal + currentOPeriod*Cameo.linksOCurrentPeriod)*Cameo.linksOCurrentCoeff;
				currentOVal += Cameo.linksOCurrentInc;
				originOffset = PApplet.cos(originOVal + originOPeriod*Cameo.linksOOriginPeriod)*Cameo.linksOOriginCoeff;
				originOVal += Cameo.linksOOriginInc;
				
			}else{
				lerpInc = Cameo.linksDLerpInc;
				sw = Cameo.linksDStrokeWeight;
				alpha = Cameo.linksDBrightness;
				currentOffset = PApplet.cos(currentDVal + currentDPeriod*Cameo.linksDCurrentPeriod)*Cameo.linksDCurrentCoeff;
				currentDVal += Cameo.linksDCurrentInc;
				originOffset = PApplet.cos(originDVal + originDPeriod*Cameo.linksDOriginPeriod)*Cameo.linksDOriginCoeff;
				originDVal += Cameo.linksDOriginInc;
			}
			
			if(decrease)
				decrease();
		}
	}
	
	void decrease(){
		lerpVal -= 0.075f;
		if(lerpVal < 0){
			if(type == 0){
				if(!Cameo.intro && pos.x < 960)
					Cameo.linksOPos.add(pos);
				Cameo.linksO.remove(this);
			}else{
				if(!Cameo.intro && pos.x < 1120)
					Cameo.linksDPos.add(pos);
				Cameo.linksD.remove(this);
			}
		}
	}
	
	void connect(){
		for(int i = 0; i < Cameo.comets.size(); i++){
			Comet c1 = Cameo.comets.get(i);
			PVector p1 = c1.pos;
			
			if(vicinity(p1, destination1)){//there is a comet at point 1
				for(int j = 0; j < Cameo.comets.size(); j++){
					Comet c2 = Cameo.comets.get(j);
					PVector p2 = c2.pos;
					
					if(vicinity(p2, destination2)){//there is one on the other end!
						Cameo.cometsL.add(new Comet(p1, Cameo.xStep, c1.color, 1, this.p)); //add both with the same colors!
						Cameo.cometsL.add(new Comet(p2, Cameo.xStep, c2.color, 1, this.p)); //add both with the same colors!
						canConnect = false;
					}
				}
			}
//			else if(p == destination2){
//				for(int j = 0; j < Cameo.comets.size(); j++){
//					PVector p2 = Cameo.comets.get(j).pos;
//					
//					if(p2 == destination1){
//						//add a large comet!
//					}
//				}
//			}
		}
	}
	
	boolean vicinity(PVector a, PVector b){
		if(PApplet.dist(a.x, a.y, b.x, b.y) < 10){
			return true;
		}else{
			return false;
		}
	}
	
	void display(){
		p.strokeCap(PApplet.PROJECT);
		p.strokeWeight(sw);
		p.stroke(color, alpha);
		p.line(origin.x, origin.y+originOffset, current1.x, current1.y+currentOffset);
		p.line(origin.x, origin.y+originOffset, current2.x, current2.y-currentOffset);
	}
}

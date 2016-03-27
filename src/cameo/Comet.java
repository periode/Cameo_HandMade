package cameo;

import processing.core.PApplet;
import processing.core.PVector;

public class Comet {
	PApplet p;

	PVector pos;

	int type;
	
	static float sw;
	static float swinc;
	static float swcoeff;

	//----SHAPE
	float rad;
	float maxRad;
	float lerpValRad;
	float lerpIncRad;
	int sides;
	int inc;
	int maxVertex;
	
	float max_arc;
	float innerRad;

	//----VARIATIONS
	float currentVariations[];
	float startVariations[];
	float targetVariations[];
	float lerpValVariations[];
	float lerpIncVariations;


	//----ROTATION
	float currentRotation;
	float startRotation;
	float targetRotation;
	float lerpValRotation;
	float lerpIncRotation;

	int color;
	float alphaFill;
	float alphaStroke;
	float alphaVar;
	float alphaVarCoeff;

	float scale;
	float scaleVar;
	float scaleVarCoeff;

	boolean disappear;
	float scaleDisappear;
	boolean canUpdate;

	Comet(){}

	Comet(PVector _pos, float _rad, int _col, int _t, PApplet _p){
		p = _p;
		pos = _pos;
		type = _t;
		if(type == 0){
			maxRad = _rad*0.25f;
		}else{
			maxRad = _rad*0.65f;
		}

		rad = 0;

		lerpValRad = 0f;
		lerpIncRad = 0.1f;

		sides = 5;
		inc = 360/sides;

		currentVariations = new float[sides];
		startVariations  = new float[sides];
		targetVariations  = new float[sides];

		lerpValVariations = new float[sides];

		for(int i = 0; i < currentVariations.length; i++){
			float r = p.random(0, maxRad*0.75f);
			currentVariations[i] = r;
			startVariations[i] = r;
			targetVariations[i] = r;

			lerpValVariations[i] = 0;	
		}
		lerpIncVariations = 0.1f;

		currentRotation = 0;
		startRotation = 0;
		targetRotation = 0;

		lerpValRotation = 0;
		lerpIncRotation = 0.05f;
		
		max_arc = 0;
		innerRad = 0;

		color = _col;
		alphaFill = 255;
		alphaVar = (int)(p.random(0, 4))*(90);
		alphaVarCoeff = 0;
		scale = 1;
		scaleVar = (int)(p.random(0, 4))*(90);
		scaleVarCoeff = 0;

		disappear = false;
		scaleDisappear = 0.075f;
		canUpdate = true;

		maxVertex = 180;
	}

	void update(){
		//----BLOOM
		if(canUpdate){
			if(type == 0){
				lerpIncRad = Cameo.cometsLerpInc;
				//				alphaFill = Cameo.cometsBrightness+Cameo.cometsBrightness*Cameo.cometsBrightnessCoeff;
				//				alphaStroke = Cameo.cometsBrightnessStroke+Cameo.cometsBrightnessStroke*Cameo.cometsBrightnessCoeff;
			}else{
				lerpIncRad = Cameo.cometsLLerpInc;
				//				alphaFill = Cameo.cometsLBrightness+Cameo.cometsLBrightness*Cameo.cometsLBrightnessCoeff;
				//				alphaStroke = Cameo.cometsLBrightnessStroke+Cameo.cometsLBrightnessStroke*Cameo.cometsLBrightnessCoeff;
			}

			if(lerpValRad < 1)
				lerpValRad += lerpIncRad;

			rad = PApplet.lerp(0, maxRad, lerpValRad);

			//----VARIATIONS
			for(int i = 0; i < lerpValVariations.length; i++){
				if(lerpValVariations[i] < 1){
					lerpValVariations[i] += lerpIncVariations;
				}else{
					lerpValVariations[i] = 1;
					startVariations[i] = targetVariations[i];
				}

				currentVariations[i] = PApplet.lerp(startVariations[i], targetVariations[i], lerpValVariations[i]);
			}

			//----ROTATION
			if(lerpValRotation < 1)
				lerpValRotation += lerpIncRotation;
			else{
				lerpValRotation = 1;
				startRotation = targetRotation;
			}

			currentRotation = PApplet.lerp(startRotation, targetRotation, lerpValRotation);

			//----SCALE
			if(!disappear){
				if(type == 0){
					scale = 1+PApplet.cos(PApplet.radians(Cameo.cometsScale+scaleVar*scaleVarCoeff))*0.25f*Cameo.cometsScaleInc;
					alphaFill = Cameo.cometsBrightness+Cameo.cometsBrightness*PApplet.map(PApplet.cos(Cameo.cometsBrightnessVal+alphaVar*alphaVarCoeff), -1, 1, 0, 1)*Cameo.cometsBrightnessVariation;//
					//					alphaFill = Cameo.cometsBrightness+Cameo.cometsBrightness*Cameo.cometsBrightnessCoeff;
					alphaStroke = Cameo.cometsBrightnessStroke+Cameo.cometsBrightnessStroke*Cameo.cometsBrightnessCoeff;
				}else{
					scale = 1+PApplet.cos(PApplet.radians(Cameo.cometsScale+scaleVar*scaleVarCoeff))*0.25f*Cameo.cometsScaleInc;	
					alphaFill = Cameo.cometsLBrightness+Cameo.cometsLBrightness*PApplet.map(PApplet.cos(Cameo.cometsLBrightnessVal+alphaVar*alphaVarCoeff), -1, 1, 0, 1)*Cameo.cometsLBrightnessVariation;
					//					alphaFill = Cameo.cometsLBrightness+Cameo.cometsLBrightness*Cameo.cometsLBrightnessCoeff;
					alphaStroke = Cameo.cometsLBrightnessStroke+Cameo.cometsLBrightnessStroke*Cameo.cometsLBrightnessCoeff;
				}
			}


		}
		
		if(max_arc < PApplet.TWO_PI)
			max_arc += 0.1f;
		
		if(innerRad < rad*scale)
			innerRad += 0.5f;
		

		if(disappear){
			disappear();
		}
	}

	void disappear(){
		scale -= scaleDisappear;
		if(scale < 0.005f){
			if(type == 0){
				if(!Cameo.intro)
					Cameo.cometsPos.add(pos);
				Cameo.comets.remove(this);
			}else{
				if(!Cameo.intro)
					Cameo.cometsLPos.add(pos);
				Cameo.cometsL.remove(this);
			}

			disappear = false;
		}
	}

	void display(){
		sw = 2+(PApplet.cos(p.millis()*swinc)+1)*swcoeff;


//		if(type == 0)
			p.fill(color, alphaFill);
		p.stroke(color, alphaStroke);
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		p.rotate(PApplet.radians(currentRotation));

		if(type == 0){
			
			p.beginShape();
			p.strokeJoin(PApplet.ROUND);
			int j = 0;
			for(int i = 0; i < maxVertex; i += inc){
				p.vertex(PApplet.cos(PApplet.radians(i))*rad*scale+currentVariations[j], PApplet.sin(PApplet.radians(i))*rad*scale+currentVariations[j]);
				j++;
			}
			p.endShape(PApplet.CLOSE);

		}else{
			p.rotateX(p.millis()*0.0001f+targetRotation);
			p.rotateY(p.millis()*0.0002f);
			//			int j = 0;
			//			for(int i = 0; i < maxVertex; i += inc){
			p.strokeWeight(sw);
//			p.ellipse(0, 0, rad*scale, rad*scale);
			p.arc(0, 0, innerRad, innerRad, 0, max_arc);
			p.arc(0, 0, rad*scale, rad*scale, 0, max_arc);
			//				j++;
			//			}

		}
		p.popMatrix();

	}

}

package cameo;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import themidibus.MidiBus;


public class Cameo extends PApplet {

	//----MIDI
	MidiBus midi_beatstep;
	MidiBus midi_kontrol;
	int channel = 0;
	int pitch = 0;
	int velocity = 0;
	int note;
	int value = 0;
	
	//----COLORS
	int background_color;
	int foreground_color;
	static int[] colors;
	
	//----GRID
	ArrayList<PVector> grid;
	int cols;
	int rows;
	static float xStep;
	float yStep;
	ArrayList<Integer> grid_color;
	
	//----NOISE
	static float cosValX;
	static float cosValY;
	float noiseY;
	float cosIncX;
	float cosIncY;
	static float cosCoeffX;
	static float cosCoeffY;
	
	//----SQUARES
	ArrayList<Square> squares;
	
	//---- LINKS
	static ArrayList<Link> linksO;
	static ArrayList<Link> linksD;
	
	static ArrayList<PVector> linksOPos;
	static ArrayList<PVector> linksDPos;
	
	static float linksOStrokeWeight;
	static float linksOBrightness;
	static float linksOLerpInc;
	
	static float linksDStrokeWeight;
	static float linksDBrightness;
	static float linksDLerpInc;
	
	static float linksOOriginInc;
	static float linksOOriginCoeff;
	static float linksOOriginPeriod;
	
	static float linksOCurrentInc;
	static float linksOCurrentCoeff;
	static float linksOCurrentPeriod;
	
	static float linksDOriginInc;
	static float linksDOriginCoeff;
	static float linksDOriginPeriod;
	
	static float linksDCurrentInc;
	static float linksDCurrentCoeff;
	static float linksDCurrentPeriod;
	
	
	//----PARTICLES
	ArrayList<Particle> particles;
	
	static float alphaX;
	static float sizeX;
	static float thetaX;
	float rotationX;
	static float lineAlphaX;
	
	static float alphaY;
	static float sizeY;
	static float thetaY;
	float rotationY;
	static float lineAlphaY;
	
	static float lineAlpha;
	static float lineSize;
	
	//----COMETS
	static ArrayList<Comet> comets;
	static ArrayList<PVector> cometsPos;
	
	static ArrayList<Comet> cometsL;
	static ArrayList<PVector> cometsLPos;
	
	static float cometsScale;
	static float cometsScaleInc;
	static float cometsBrightness;
	static float cometsBrightnessStroke;
	static float cometsBrightnessCoeff;
	static float cometsBrightnessVal;
	static float cometsBrightnessInc; 
	static float cometsBrightnessVariation;
	float cometsMaxAlpha;
	static float cometsLerpInc;
	static float cometsVertexRange;
	
	static float cometsLScale;
	float cometsLScaleInc;
	static float cometsLBrightness;
	static float cometsLBrightnessStroke;
	static float cometsLBrightnessCoeff;
	static float cometsLBrightnessVal;
	static float cometsLBrightnessInc;
	static float cometsLBrightnessVariation;
	float cometsLMaxAlpha;
	static float cometsLLerpInc;
	static float cometsLVertexRange;
	
	float cometsMaxScale;
	
	//----INTRO
	static boolean curtain = true;
	static boolean intro = true;
	ArrayList<PVector> introGrid;
	ArrayList<Particle> introParticles;
	ArrayList<Comet> introComets;
	ArrayList<Link> introLinks;
	
	boolean end;
	
	public void setup() {
		colorMode(HSB, 360, 100, 100);
		
		//----MIDI
		MidiBus.list();
		midi_beatstep = new MidiBus(this, "Arturia BeatStep", 1);
		midi_beatstep.setBusName("beatstep");
		
		midi_kontrol = new MidiBus(this, "SLIDER/KNOB", 2);
		midi_kontrol.setBusName("kontrol");
		
		println("--------------");
		println("-"+midi_beatstep.getBusName());
		println("handMade.setup(this);");
		println("comets = new ArrayList<Comet>();");
		println(">>done");
		println("dusts = new ArrayList<Dust>();");
		println(">>done");
		println("links = new ArrayList<Link>();");
		println(">>done");
		println("handMade.start();");
		println("--------------");
		
		//----COLORS
		background_color = 0;
		foreground_color = 255;
		colors = new int[5];
		//RGB
		colors[0] = color(242, 235, 144);
		colors[1] = color(241, 208, 225);
		colors[2] = color(141, 195, 223);
		colors[3] = color(163, 209, 181);
		colors[4] = color(245, 192, 140);
		
		//HSB
		colors[0] = color(56, 41, 95);
		colors[1] = color(329, 14, 95);
		colors[2] = color(200, 37, 87);
		colors[3] = color(143, 22, 82);
		colors[4] = color(30, 43, 96);
		
		//----ORBITS
		cosValX = 0;
		cosValY = 0;
		noiseY = 0;
		cosIncX = 0f;
		cosIncY = 0f;
		cosCoeffX = 0.1f;
		cosCoeffY = 0.1f;
		
		alphaX = 255;
		lineAlphaX = 0;
		sizeX = 1;
		thetaX = 0;
		rotationX = 0;
		
		alphaY = 255;
		lineAlphaY = 0;
		sizeY = 1;
		thetaY = 0;
		rotationY = 0;
		
		lineAlpha = 0;
		lineSize = 0;
		
		squares = new ArrayList<Square>();
		
		//----LINKS
		linksO = new ArrayList<Link>();
		linksD = new ArrayList<Link>();
		
		linksOPos = new ArrayList<PVector>();
		linksDPos = new ArrayList<PVector>();
		
		linksOBrightness = 255;
		linksDBrightness = 255;
		
		//----PARTICLES
		particles = new ArrayList<Particle>();
		
		
		//----COMETS
		comets = new ArrayList<Comet>();
		cometsPos = new ArrayList<PVector>();
		cometsL = new ArrayList<Comet>();
		cometsLPos = new ArrayList<PVector>();
		
		cometsScale = 1;
		cometsScaleInc = 0f;
		cometsVertexRange = xStep*0.25f*0.3f;
		cometsBrightness = 255;
		cometsBrightnessStroke = 255;
		cometsBrightnessInc = 0.1f;
		cometsLerpInc = 0.005f;
		
		cometsLScale = 1;
		cometsLScaleInc = 0f;
		cometsLVertexRange = xStep*0.65f*0.2f;
		cometsLBrightness = 255;
		cometsLBrightnessStroke = 255;
		cometsLBrightnessInc = 0.1f;
		cometsLLerpInc = 0.005f;
		
		cometsMaxScale = 0;
		
		
		//---GRID
		cols = 8;
		rows = 5;
		
		xStep = width/cols;
		yStep = height/rows;
		grid_color = new ArrayList<Integer>();
		grid = new ArrayList<PVector>();
		for(float x = xStep; x < width; x += xStep){
			for(float y = yStep; y < height; y += yStep){
				grid.add(new PVector(x, y));
				particles.add(new Particle(x, y, this));
				linksOPos.add(new PVector(x, y));
				linksDPos.add(new PVector(x, y));
				cometsPos.add(new PVector(x, y));
				cometsLPos.add(new PVector(x, y));
			}
		}
		
		//----INTRO
		introGrid = new ArrayList<PVector>();
		introParticles = new ArrayList<Particle>();
		introLinks = new ArrayList<Link>();
		introComets = new ArrayList<Comet>();
		
		cols = 8;
		rows = 2;
		
		xStep = width/cols;
		yStep = height/rows;
		for(float x = xStep; x < width; x += xStep){
			for(float y = yStep; y < height; y += yStep){
				introGrid.add(new PVector(x, y));
				introParticles.add(new Particle(x, y, this));
			}
		}
		
		//reset it for repopulation purposes
		cols = 8;
		rows = 5;
		
		xStep = width/cols;
		yStep = height/rows;
		
		end = false;
	}
	
	public void settings(){
		//size(1024, 768);
		fullScreen(P3D);
	}
	
	public void update(){
		keepInRangeElements();
		
		for(int i = 0; i < squares.size(); i++){
			squares.get(i).update();
		}
		
		for(int i = 0; i < linksO.size(); i++){
			linksO.get(i).update();
		}
		
		for(int i = 0; i < linksD.size(); i++){
			linksD.get(i).update();
		}
		
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).update();
		}
		
		for(int i = 0; i < cometsL.size(); i++){
			cometsL.get(i).update();
		}
		
		for(int i = 0; i < comets.size(); i++){
			comets.get(i).update();
		}
		
		if(intro){
			for(int i = 0; i < introParticles.size(); i++){
				introParticles.get(i).update();
			}
		}
		
		thetaX += rotationX;
		thetaY += rotationY;
		
		cosValX += cosIncX;
		cosValY += cosIncY;
		
		cometsScale += 7.5f;
		cometsLScale += cometsLScaleInc;
		

		cometsBrightnessVal += cometsBrightnessInc;
		cometsLBrightnessVal += cometsLBrightnessInc;
	}

	public void draw() {
//		scale(0.5f);
//		translate(750, 320);
//		scale(0.65f);
//		translate(440, 150);
		update();
		noCursor();
		background(0);
		colorMode(HSB);
		rectMode(CENTER);
		strokeCap(SQUARE);
		fill(255);
		strokeWeight(1);
		stroke(255);
		
		if(intro){
			for(int i = 0; i < introParticles.size(); i++){
				introParticles.get(i).display();
			}
		}else{
			for(int i = 0; i < particles.size(); i++){
				particles.get(i).display();
				//text(" "+particles.get(i).pos, particles.get(i).pos.x, particles.get(i).pos.y);
			}
		}
		
		for(int i = 0; i < squares.size(); i++){
			squares.get(i).display();
		}
		
		for(int i = 0; i < cometsL.size(); i++){
			cometsL.get(i).display();
		}
		
		for(int i = 0; i < comets.size(); i++){
			comets.get(i).display();
		}
		
		for(int i = 0; i < linksO.size(); i++){
			linksO.get(i).display();
		}
		
		for(int i = 0; i < linksD.size(); i++){
			linksD.get(i).display();
		}
		
//		debug();

		if(end){
			fill(0);
			rect(0, 0, width, height);
		}
//		stroke(255);
//		strokeWeight(1);
//		for(int i = 1; i < particles.size(); i+=3){
//			for(int j = 0; j < particles.size(); j+=3){
//				Particle p1 = particles.get(i);
//				Particle p2 = particles.get(j);
//				line(p1.pos.x, p1.pos.y, p2.pos.x, p2.pos.y);
//			}
//		}
		
		if(curtain){
			fill(0);
			rect(0, 0, width*2, height*2);
//			textAlign(CENTER);
//			textSize(64);
//			fill(255);
//			text("h a n d _ m a d e", width*0.5f, height*0.5f);
		}
	}
	
	public void addSquare(){
		int index = (int)(random(grid.size()));
		PVector p = grid.get(index);
		int color = colors[(int)(random(colors.length))];
		squares.add(new Square(p, xStep, yStep, 0, color, this));
	}
	
	public void singleBeat(){
		int index = (int)(random(squares.size()));
		squares.get(index).canBeat = true;
	}
	
	public void moveVertex(int type, int number){
		if(type == 0 && comets.size() > 0){
			if(number == 0){
				int index = (int)(random(comets.size()));
				Comet c = comets.get(index);
				
				int i = (int)random(c.sides);
				
				c.targetVariations[i] += random(-cometsVertexRange, cometsVertexRange*2);
				c.lerpValVariations[i] = 0;
			}else{
				int i = (int)random(comets.get(0).sides);
				for(int index = 0; index < comets.size(); index++){
					comets.get(index).targetVariations[i] += random(-cometsVertexRange, cometsVertexRange*2);
					comets.get(index).lerpValVariations[i] = 0;
				}
			}
		}else if(type == 1 && cometsL.size() > 0){
			if(number == 0){
				int index = (int)(random(cometsL.size()));
				Comet c = cometsL.get(index);
				
				int i = (int)random(c.sides);
				
				c.targetVariations[i] += random(-cometsLVertexRange, cometsLVertexRange);
				c.lerpValVariations[i] = 0;
			}else{
				int i = (int)random(cometsL.get(0).sides);
				for(int index = 0; index < cometsL.size(); index++){
					cometsL.get(index).targetVariations[i] += random(-cometsLVertexRange, cometsLVertexRange*2);
					cometsL.get(index).lerpValVariations[i] = 0;
				}
			}
		}
	}
	
	public void resetVertex(int type, int number){
		if(type == 0 && comets.size() > 0){
			if(number == 0){
				for(int i = 0; i < comets.size(); i++){
					for(int j = 0; j < comets.get(i).targetVariations.length; j++){
						comets.get(i).targetVariations[j] = 0;
						comets.get(i).lerpValVariations[j] = 0;
					}
				}
			}
		}else if(type == 1 && cometsL.size() > 0){
			if(number == 0){
				for(int i = 0; i < cometsL.size(); i++){
					for(int j = 0; j < cometsL.get(i).targetVariations.length; j++){
						cometsL.get(i).targetVariations[j] = 0;
						cometsL.get(i).lerpValVariations[j] = 0;
					}
				}
			}
		}
	}
	
	public void rotateComet(int type, int number){
		if(type == 0 && comets.size() > 0){
			if(number == 0){
				int index = (int)(random(comets.size()));
				Comet c = comets.get(index);
				c.targetRotation += 90;
				c.lerpValRotation = 0;
			}else{
				for(int i = 0; i < comets.size(); i++){
					comets.get(i).targetRotation += 90;
					comets.get(i).lerpValRotation = 0;
				}
			}
		}else if(type == 1 && cometsL.size() > 0){
			if(number == 0){
				int index = (int)(random(cometsL.size()));
				Comet c = cometsL.get(index);
				c.targetRotation += 180;
				c.lerpValRotation = 0;
			}else{
				for(int i = 0; i < cometsL.size(); i++){
					cometsL.get(i).targetRotation += 180;
					cometsL.get(i).lerpValRotation = 0;
				}
			}
		}
		
	}
	
	public void resetLerp(int type, int num){
		if(type == 0){
			for(int i = 0; i < comets.size(); i++){
				if(comets.get(i).canUpdate)
					comets.get(i).lerpValRad = 0;
			}
		}else{
			if(num == 1){
				for(int i = 0; i < cometsL.size(); i++){
					if(cometsL.get(i).canUpdate)
						cometsL.get(i).lerpValRad = 0;
				}
			}else{
				cometsL.get((int)random(cometsL.size())).lerpValRad = 0;
			}

		}
	}
	
	public void addLink(int dir){
		if(linksO.size() == 0){
			linksOPos.clear();
			repopulate(linksOPos);
		}
		
		if(linksD.size() == 0){
			linksDPos.clear();
			repopulate(linksDPos);
		}
		
		if(dir == 0 && linksOPos.size() > rows-2){ //----ORTHOGONAL
			if(linksOPos.size() == rows-1){
				clearLinks(0);
				return;
			}
			if(!intro){
				int index = max(0, (int)(random(linksOPos.size()-rows)));
				int index2 = max(0, min(index+1, grid.size()));
				PVector orig = linksOPos.get(index);
				PVector dest = grid.get(index2);
				linksOPos.remove(orig);
				
				int color = colors[(int)(random(colors.length))];
				
				linksO.add(new Link(orig, dest, 0.1f, color, dir, this));
			}else{
				int index = max(0, (int)(random(introGrid.size()-rows)));
				int index2 = max(0, min(index+1, introGrid.size()));
				PVector orig = introGrid.get(index);
				PVector dest = introGrid.get(index2);
				
				int color = colors[(int)(random(colors.length))];
				
				linksO.add(new Link(orig, dest, 0.1f, color, dir, this));
			}

		}else if(dir == 1 && linksDPos.size() > 0){ //----DIAGONAL
			if(linksDPos.size() == 1){
				clearLinks(1);
				return;
			}
			int index = max(0, (int)(random(linksDPos.size()-rows)));
			PVector orig = linksDPos.get(index);
			linksDPos.remove(orig);
			
			int index2 = max(0, min((int)(random(grid.size())), grid.size()-1));
			PVector dest = grid.get(index2);
			
			
			int color = colors[(int)(random(colors.length))];
			
			linksD.add(new Link(orig, dest, 0.1f, color, dir, this));
		}
	}
	
	public void resetLink(int type, int number){
		if(type == 0 && linksO.size() > 0){
			if(number == 0){
				linksO.get((int)(random(linksO.size()))).lerpVal = 0;	
			}else{
				for(int i = 0; i < linksO.size(); i++){
					linksO.get(i).lerpVal = 0;	
				}
			}
		}else if(type == 1 && linksD.size() > 0){
			if(number == 0){
				linksD.get((int)(random(linksD.size()))).lerpVal = 0;	
			}else{
				for(int i = 0; i < linksD.size(); i++){
					linksD.get(i).lerpVal = 0;	
				}
			}
		}
	}
	
	public void addComet(int dir){
		if(dir == 0 && cometsPos.size() == 0)
			clearComets(0);
		
		if(dir == 1 && cometsLPos.size() == 0)
			clearComets(1);
		
		if(dir == 0 && cometsPos.size() > 0){
			PVector p;
			if(!intro){
				int index = (int)(random(cometsPos.size()));
				p = cometsPos.get(index);
				cometsPos.remove(p);
				int color = colors[(int)(random(colors.length))];
				
				comets.add(new Comet(p, xStep, color, dir, this));
			}else{
				int index = (int)(random(introGrid.size()));
				p = introGrid.get(index);
				int color = colors[(int)(random(colors.length))];
				
				comets.add(new Comet(p, xStep*0.5f, color, dir, this));
			}
		}else if(dir == 1 && cometsLPos.size() > 0){
			PVector p;
			if(!intro){
				int index = (int)(random(cometsLPos.size()));
				p = cometsLPos.get(index);
				cometsLPos.remove(p);
				int color = colors[(int)(random(colors.length))];
				
				cometsL.add(new Comet(p, xStep, color, dir, this));
			}else{
				int index = (int)(random(introGrid.size()));
				p = introGrid.get(index);
				int color = colors[(int)(random(colors.length))];
				
				cometsL.add(new Comet(p, xStep*0.5f, color, dir, this));
			}
		}
	}
	
	public void clearSquares(){
		squares.clear();
	}
	
	public void clearLinks(int dir){
		if(dir == 0){
			for(int i = 0; i < linksO.size(); i++){
				linksO.get(i).decrease = true;
			}
		}else{
			for(int i = 0; i < linksD.size(); i++){
				linksD.get(i).decrease = true;
			}
		}	
	}
	
	public void clearComets(int dir){
		if(dir == 0){
			for(int i = 0; i < comets.size(); i++){
				comets.get(i).disappear = true;
			}
			//repopulate(cometsPos);
		}else{
			for(int i = 0; i < cometsL.size(); i++){
				cometsL.get(i).disappear = true;
			}
			//repopulate(cometsLPos);
		}	
	}
	
	public void repopulate(ArrayList<PVector> al){
		for(float x = xStep; x < width; x += xStep){
			for(float y = yStep; y < height; y += yStep){
				al.add(new PVector(x, y));
			}
		}
	}
	
	public void removeComet(int type){
		if(type == 0 && comets.size() > 0){
			int index = (int)random(comets.size());
			comets.get(index).disappear = true;
		}else if(type == 1 && cometsL.size() > 0){
			int index = (int)random(cometsL.size());
			cometsL.get(index).disappear = true;
		}
	}
	
	public void removeLink(int type){
		if(type == 0 && linksO.size() > 0){
			int index = (int)random(linksO.size());
			linksO.get(index).decrease = true;
		}else if(type == 1 && linksD.size() > 0){
			int index = (int)random(linksD.size());
			linksD.get(index).decrease = true;
		}
	}
	
	public void selectComet(float v, int type){
		if(type == 0){
			if(v < -1 && comets.size() > 0){
				for(int i = 0; i < comets.size(); i++){
					comets.get(i).canUpdate = false;
				}
				int i = (int)random(comets.size());
				comets.get(i).canUpdate = true;
			}else if(v > 1){
				for(int i = 0; i < comets.size(); i++){
					comets.get(i).canUpdate = true;
				}
			}	
		}else{
			if(v < -1 && cometsL.size() > 0){
				for(int i = 0; i < cometsL.size(); i++){
					cometsL.get(i).canUpdate = false;
				}
				int i = (int)random(cometsL.size());
				cometsL.get(i).canUpdate = true;
			}else if(v > 1){
				for(int i = 0; i < cometsL.size(); i++){
					cometsL.get(i).canUpdate = true;
				}
			}
		}
	}
	
	public void selectOne(String s){
		if(s == "particle"){
			for(int i = 0; i < particles.size(); i++){
				particles.get(i).canUpdate = false;
			}
			int i = (int)(random(particles.size()));
			particles.get(i).canUpdate = true;
		}else if(s == "particleAdd"){
			int i = (int)(random(particles.size()));
			particles.get(i).canUpdate = true;
		}else if(s == "particleIntro"){
			for(int i = 0; i < introParticles.size(); i++){
				introParticles.get(i).canUpdate = false;
			}
			int i = (int)(random(introParticles.size()));
			introParticles.get(i).canUpdate = true;
		}else if(s == "particleAddIntro"){
			int i = (int)(random(introParticles.size()));
			introParticles.get(i).canUpdate = true;
		}else if(s == "linksO"){
			for(int i = 0; i < linksO.size(); i++){
				linksO.get(i).canUpdate = false;
			}
			int i = (int)(random(linksO.size()));
			linksO.get(i).canUpdate = true;
		}else if(s == "linksOAdd"){
			int i = (int)(random(linksO.size()));
			linksO.get(i).canUpdate = true;
		}else if(s == "linksD"){
			for(int i = 0; i < linksD.size(); i++){
				linksD.get(i).canUpdate = false;
			}
			int i = (int)(random(linksD.size()));
			linksD.get(i).canUpdate = true;
		}else if(s == "linksDAdd"){
			int i = (int)(random(linksD.size()));
			linksD.get(i).canUpdate = true;
		}
	}
	
	public void selectAll(String s){
		if(s == "particle"){
			for(int i = 0; i < particles.size(); i++){
				particles.get(i).canUpdate = true;
			}
		}else if(s == "particleIntro"){
			for(int i = 0; i < introParticles.size(); i++){
				introParticles.get(i).canUpdate = true;
			}
		}else if(s == "linksO"){
			for(int i = 0; i < linksO.size(); i++){
				linksO.get(i).canUpdate = true;
			}
		}else if(s == "linksD"){
			for(int i = 0; i < linksD.size(); i++){
				linksD.get(i).canUpdate = true;
			}	
		}
	}
	
	public void stepScale(float v){
		cometsMaxScale += v*0.01f;
		cometsMaxScale = constrain(cometsMaxScale, 0, 1);
		for(int i = 0; i < comets.size(); i++){
			comets.get(i).scaleVarCoeff = cometsMaxScale;
		}
		for(int i = 0; i < cometsL.size(); i++){
			cometsL.get(i).scaleVarCoeff = cometsMaxScale;
		}
	}
	
	public void stepAlpha(int type, float v){
		if(type == 0){
			cometsMaxAlpha += v*0.01f;	
			cometsMaxAlpha = constrain(cometsMaxAlpha, 0, 1);
			for(int i = 0; i < comets.size(); i++){
				comets.get(i).alphaVarCoeff = cometsMaxAlpha;
			}
		}else{
			cometsLMaxAlpha += v*0.01f;
			cometsLMaxAlpha = constrain(cometsLMaxAlpha, 0, 1);
			for(int i = 0; i < cometsL.size(); i++){
				cometsL.get(i).alphaVarCoeff = cometsLMaxAlpha;
			}
		}
	}
	
	public void debug(){
		textAlign(LEFT);
		textSize(10);
		fill(100, 255, 100);
		text("framerate: "+frameRate, 10, 10);
		text("ch: "+channel, 10, 20);
		text("pitch: "+pitch, 10, 30);
		text("vel: "+velocity, 10, 40);
		text("note: "+note, 10, 50);
		text("val: "+value, 10, 60);
		text("squares: "+squares.size(), 10, 70);
		text("linksOPos: "+linksOPos.size(), 10, 80);
		text("linksDPos: "+linksDPos.size(), 10, 90);
		text("grid: "+grid.size(), 10, 100);
		text("cometsScaleInc: "+cometsScaleInc, 10, 110);
		text("cometsLScaleInc: "+cometsLScaleInc, 10, 120);
		text("cometsScale: "+cometsScale, 10, 130);
		text("cometsLScale: "+cometsLScale, 10, 140);
		text("comets: "+comets.size(), 10, 150);
		text("cometspos: "+cometsPos.size(), 10, 160);
	}
	
	public void noteOn(int c, int p, int v, long t, String s){
		channel = c;
		pitch = p;
		velocity = v;
		
		if(s == "kontrol"){
			switch(p){
			case 0:
				if(!intro)
					selectOne("particle");
				else
					selectOne("particleIntro");
				break;
			case 1:
				if(!intro)
					selectOne("particleAdd");
				else
					selectOne("particleAddIntro");
				break;
			case 16:
				if(!intro)
					selectAll("particle");
				else
					selectAll("particleIntro");
				break;
			case 20:
				clearComets(0);
				break;
			case 21:
				clearLinks(0);
				break;
			case 22:
				clearComets(1);
				break;
			case 23:
				clearLinks(1);
				break;
			case 41:
				curtain = !curtain;
				break;
			default:
				break;
			}
		}
		
		if(s == "beatstep"){
			if(c == 0){
				switch(p){
				case 44:
					addComet(0);
					break;
				case 45:
					resetLerp(0, 1);
					break;
				case 46:
					moveVertex(0, 1);
					break;
				case 47:
					resetVertex(0, 0);
					break;
				case 48:
					rotateComet(0, 0);
					break;
				case 49:
					increaseRadius(0, 0);
					break;
				case 50:
					increaseRadius(1, 0);
					break;
				case 51:
					resetRadius(0, 0);
					break;
				case 36://----SECOND ROW
					addComet(1);
					break;
				case 37:
					resetLerp(1, 0);
					break;
				case 38:
					resetLerp(1, 1);
					break;
				case 39:
					rotateComet(1, 1);
					break;
				case 40:
					rotateComet(1, 0);
					break;
				case 41:
					increaseRadius(1, 0);
					break;
				case 42:
					increaseRadius(1, 1);
					break;
				case 43:
					resetRadius(1, 0);
					break;
				default:
					break;
				}
			}else if(c == 1){
				switch(p){
				case 44:
					addLink(0); //----ORTHOGONAL
					break;
				case 45:
					resetLink(0, 0); //----RESET ONE
					break;
				case 46:
					resetLink(0, 1); //----RESET ALL
					break;
				case 47:
					selectOne("linksO");
					break;
				case 48:
					selectOne("linksOAdd");
					break;
				case 49:
					selectAll("linksO");
					break;
				case 50:
					removeLink(0);
					break;
				case 51:
					clearLinks(0);
					break;
				case 36://----SECOND ROW
					addLink(1); //----DIAGONAL
					break;
				case 37:
					resetLink(1, 0); //----RESET ONE
					break;
				case 38:
					resetLink(1, 1); //----RESET ALL
					break;
				case 39:
					selectOne("linksD");
					break;
				case 40:
					selectOne("linksDAdd");
					break;
				case 41:
					selectAll("linksD");
					break;
				case 42:
					removeLink(1);
					break;
				case 43:
					clearLinks(1);
					break;
				default:
					break;
				}
			}
		}
		
	}
	
	public void controllerChange(int c, int n, int v, long t, String s){
		channel = c;
		note = n;
		value = v;
		
		if(s == "kontrol"){
			switch(n){
			case 0://--------------------------------FADERS
				cosIncX = map(v, 0, 127, 0.00025f, 0.1f);
				break;
			case 1:
				cosIncY = map(v, 0, 127, 0.00025f, 0.1f);
				break;
			case 2:
				alphaX = map(v, 0, 127, 0, 255);
				
				break;
			case 3:
				alphaY = map(v, 0, 127, 0, 255);
				
				break;
			case 4:
				sizeX = map(v, 0, 127, 1, 80);
				break;
			case 5:
				sizeY = map(v, 0, 127, 1, 80);
				break;
			case 6:
				rotationX = map(v, 0, 127, 0, 0.1f);
				if(v == 0)
					Particle.thetaX_stabilizer = 0;
				else
					Particle.thetaX_stabilizer = 1;
				break;
			case 7:
				rotationY = map(v, 0, 127, 0, 0.1f);
				if(v == 0)
					Particle.thetaY_stabilizer = 0;
				else
					Particle.thetaY_stabilizer = 1;
				break;
			case 8://--------------------------------KNOBS
				cosCoeffX = map(v, 0, 127, 0, 135);
				break;
			case 9:
				cosCoeffY = map(v, 0, 127, 0, 135);
				break;
			case 10:
				lineAlphaX = map(v, 0, 127, 0, 255);
				break;
			case 11:
				lineAlphaY = map(v, 0, 127, 0, 255);
				break;
			case 12:
				lineAlpha = map(v, 0, 127, 0, 255);
				break;
			case 13:
				//idea : pulsating fill
				Particle.fillX = map(v, 0, 127, 0, 255);
				break;
			case 14:
				//idea pulsating fill
				Particle.fillY = map(v, 0, 127, 0, 255);
				break;
			case 15:
				lineSize = map(v, 0, 127, 1, 5);
				break;
			default:
				break;
			}
		}else if(s == "beatstep"){
			v = v - 64;//normalize
			
			if(n == 7){//reset stuff
				if(v < -1)
					resetElements();
				else if(v > 1)
					resetGrid();
			}
			
			if(c == 0){
				switch(n){
				case 10:
					cometsBrightnessStroke += v*0.001f;
					break;
				case 11:
					cometsBrightness += v*4f;
					break;
				case 12:
					cometsLerpInc += v*0.001f;
					break;
				case 13:
					cometsVertexRange += v;
					break;
				case 14:
					cometsBrightnessVariation += v*0.1f;
					break;
				case 15:
					cometsBrightnessInc += v*0.01f;
					break;
				case 16:
					stepAlpha(0, v);
					break;
				case 17:
					selectComet(v, 1);
				case 18://--------------------------------SECOND ROW
					cometsLBrightnessStroke += v*4f;
					break;
				case 19:
					cometsLBrightness += v*4f;
					break;
				case 20:
					cometsLLerpInc += v*0.001f;
					break;
				case 21:
					Comet.swinc += v*0.001f;
					Comet.swinc = constrain(Comet.swinc, 0, 1);
					break;
				case 22:
					cometsLBrightnessVariation += v*0.1f;
					break;
				case 23:
					cometsLBrightnessInc += v*0.01f;
					break;
				case 24:
					Comet.swcoeff += v*0.05f;
					Comet.swcoeff = constrain(Comet.swcoeff, 0, 10);
					break;
				case 25:
					selectComet(v, 0);
					break;
				default:
					break;
				}
			}else if(c == 1){ // ------------------ LINKS
				switch(n){
				case 10:
					linksOStrokeWeight += v*0.5f;
					break;
				case 11:
					linksOBrightness += v*5f;
					break;
				case 12:
					linksOLerpInc += v*0.001f;
					break;
				case 13:
					linksOCurrentCoeff += v*0.3f;
					break;
				case 14:
					linksOCurrentInc += v*0.001f;
					break;
				case 15:
					linksOOriginCoeff += v*0.3f;
					break;
				case 16:
					linksOOriginInc += v*0.01f;
					break;
				case 17:
					linksOOriginPeriod += v*0.001f;
					linksOCurrentPeriod += v*0.001f;
					break;
				case 18://--------------------------------SECOND ROW
					linksDStrokeWeight += v*0.5f;
					break;
				case 19:
					linksDBrightness += v*5f;
					break;
				case 20:
					linksDLerpInc += v*0.001f;
					break;
				case 21:
					linksDCurrentCoeff += v*0.3f;
					break;
				case 22:
					linksDCurrentInc += v*0.001f;
					break;
				case 23:
					linksDOriginCoeff += v*0.3f;
					break;
				case 24:
					linksDOriginInc += v*0.01f;
					break;
				case 25:
					linksDOriginPeriod += v*0.001f;
					linksDCurrentPeriod += v*0.001f;
					break;
				default:
					break;
				}
			}
		}
		
		
		
		keepInRangeGrid();
	}
	
	public void resetGrid(){
		cosCoeffX = 0;
		cosCoeffY = 0;
		alphaX = 255;
		alphaY = 255;
		lineAlphaX = 0;
		lineAlphaY = 0;
		lineAlpha = 0;
		sizeX = 2;
		sizeY = 2;
	}
	
	public void resetElements(){
		cometsPos.clear();
		clearComets(0);
		repopulate(cometsPos);
		
		cometsLPos.clear();
		clearComets(1);
		repopulate(cometsLPos);
		
		linksOPos.clear();
		clearLinks(0);
		repopulate(linksOPos);
		
		linksDPos.clear();
		clearLinks(1);
		repopulate(linksDPos);
	}
	
	public void keepInRangeElements(){
		cometsScaleInc = constrain(cometsScaleInc, 0.0f, 10.0f);
		cometsBrightness = constrain(cometsBrightness, 0, 255);
		cometsBrightnessVariation= constrain(cometsBrightnessVariation, 0, 1);
		cometsBrightnessInc = constrain(cometsBrightnessInc, 0, 0.5f);
		cometsLerpInc = constrain(cometsLerpInc, 0.005f, 0.2f);
		cometsVertexRange = constrain(cometsVertexRange, xStep*0.25f*0.1f, xStep*0.25f*1.5f);
		
		cometsLScaleInc = constrain(cometsLScaleInc, 0.0f, 10.0f);
		cometsLBrightness = constrain(cometsLBrightness, 0, 255);
		cometsLBrightnessVariation= constrain(cometsLBrightnessVariation, 0, 1);
		cometsLBrightnessInc = constrain(cometsLBrightnessInc, 0, 0.5f);
		cometsLLerpInc = constrain(cometsLLerpInc, 0.0025f, 0.2f);
		cometsLVertexRange = constrain(cometsLVertexRange, xStep*0.65f*0.1f, xStep*0.65f*0.5f);
		
		linksOStrokeWeight = constrain(linksOStrokeWeight, 1, 50);
		linksOBrightness = constrain(linksOBrightness, 0, 255);
		linksOLerpInc = constrain(linksOLerpInc, 0.005f, 0.1f);
		
		linksOCurrentCoeff = constrain(linksOCurrentCoeff, 0, 100);
		linksOCurrentInc = constrain(linksOCurrentInc, 0, 1);
		linksOCurrentPeriod = constrain(linksOCurrentPeriod, 0, 1f);
		
		linksOOriginCoeff = constrain(linksOOriginCoeff, 0, 100);
		linksOOriginInc = constrain(linksOOriginInc, 0, 1f);
		linksOOriginPeriod = constrain(linksOOriginPeriod, 0, 1f);
		
		linksDStrokeWeight = constrain(linksDStrokeWeight, 1, 50);
		linksDBrightness = constrain(linksDBrightness, 0, 255);
		linksDLerpInc = constrain(linksDLerpInc, 0.005f, 0.1f);
		
		linksDCurrentCoeff = constrain(linksDCurrentCoeff, 0, 100);
		linksDCurrentInc = constrain(linksDCurrentInc, 0, 1);
		linksDCurrentPeriod = constrain(linksDCurrentPeriod, 0, 1f);
		
		linksDOriginCoeff = constrain(linksDOriginCoeff, 0, 100);
		linksDOriginInc = constrain(linksDOriginInc, 0, 1f);
		linksDOriginPeriod = constrain(linksDOriginPeriod, 0, 1f);
	}
	
	public void keepInRangeGrid(){
		alphaX = constrain(alphaX, 0, 255);
		cosIncX = constrain(cosIncX, 0.00025f, 0.2f);
		cosCoeffX = constrain(cosCoeffX, 0, 360);
		sizeX = constrain(sizeX, 1, 80);
		lineAlphaX = constrain(lineAlphaX, 0, 255);
		
		alphaY = constrain(alphaY, 0, 255);
		cosIncY = constrain(cosIncY, 0.00025f, 0.2f);
		cosCoeffY = constrain(cosCoeffY, 0, 360);
		sizeY = constrain(sizeY, 1, 80);
		lineAlphaY = constrain(lineAlphaY, 0, 255);
		
		lineAlpha = constrain(lineAlpha, 0, 255);
		lineSize = constrain(lineSize, 1, 5);
	}
	
	public void keyPressed(){
		
		if(key == ' '){
			resetGrid();
			resetElements();
			intro = false;
		}
		
		if(key == 'o'){
			clearLinks(0);
		}else if(key == 'l'){
			clearComets(1);
		}else if(key == 'c'){
			clearComets(0);
		}else if(key == 'd'){
			clearLinks(1);
		}else if(key == 'v'){
			removeComet(0);
		}else if(key == ';'){
			removeComet(1);
		}else if(key == 'f'){
			removeLink(1);
		}else if(key == 'p'){
			removeLink(0);
		}else if(key == 'q'){
			setup();
		}else if(key == 'r'){
			linksOPos.clear();
			repopulate(linksOPos);
		}else if(key == 't'){
			linksDPos.clear();
			repopulate(linksDPos);
		}else if (key == 'w'){
			intro = true;
		}else if(key == 'z'){
			increaseRadius(0, 0);
		}
	}
	
	public void increaseRadius(int num, int type){
		if(type == 0){
			if(num == 0 && comets.size() > 0){
				int index = (int)(random(comets.size()));
				comets.get(index).maxVertex += comets.get(index).inc;
				if(comets.get(index).maxVertex > 360)
					comets.get(index).maxVertex = 360;
			}else if(num == 1 && comets.size() > 0){
				for(int i = 0; i < comets.size(); i++){
					comets.get(i).maxVertex += comets.get(i).inc;
					if(comets.get(i).maxVertex > 360)
						comets.get(i).maxVertex = 360;
				}
			}
		}else{
			if(num == 0 && cometsL.size() > 0){
				int index = (int)(random(cometsL.size()));
//				comets.get(index).maxVertex += comets.get(index).inc;
//				if(comets.get(index).maxVertex > 360)
//					comets.get(index).maxVertex = 360;
				cometsL.get(index).max_arc = 0;
			}else if(num == 1 && cometsL.size() > 0){
				for(int i = 0; i < cometsL.size(); i++){
//					cometsL.get(i).maxVertex += cometsL.get(i).inc;
//					if(cometsL.get(i).maxVertex > 360)
//						cometsL.get(i).maxVertex = 360;
					cometsL.get(i).max_arc = 0;
				}
			}
		}
	}
	
	public void resetRadius(int type, int num){
		if(type == 0){
			for(int i = 0; i < comets.size(); i++){
				comets.get(i).maxVertex = 180;
			}
		}else{
			for(int i = 0; i < cometsL.size(); i++){
				cometsL.get(i).innerRad = 0;
			}
		}
	}
	
	public void mousePressed(){
		addComet(0);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { cameo.Cameo.class.getName() });
	}
}

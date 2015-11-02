package cameo;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import themidibus.MidiBus;


public class Cameo extends PApplet {

	//----MIDI
	MidiBus midi;
	int channel = 0;
	int pitch = 0;
	int velocity = 0;
	int note;
	int value = 0;
	
	//----COLORS
	int background_color;
	int foreground_color;
	int[] colors;
	
	//----GRID
	ArrayList<PVector> grid;
	int cols;
	int rows;
	static float xStep;
	float yStep;
	
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
	
	//----PARTICLES
	ArrayList<Particle> particles;
	
	static float alphaX;
	static float sizeX;
	static float lineAlphaX;
	
	static float alphaY;
	static float sizeY;
	static float lineAlphaY;
	
	static float lineAlpha;
	static float lineSize;
	
	//----COMETS
	static ArrayList<Comet> comets;
	static ArrayList<PVector> cometsPos;
	
	static ArrayList<Comet> cometsL;
	static ArrayList<PVector> cometsLPos;
	
	static float cometsScale;
	float cometsScaleInc;
	static float cometsBrightness;
	static float cometsLerpInc;
	static float cometsVertexRange;
	
	static float cometsLScale;
	float cometsLScaleInc;
	static float cometsLBrightness;
	static float cometsLLerpInc;
	static float cometsLVertexRange;
	
	float cometsMaxScale;
	
	//----INTRO
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
		midi = new MidiBus(this, 0, 1);
		
		println("--------------");
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
		cosIncX = 0.001f;
		cosIncY = 0.001f;
		cosCoeffX = 0.1f;
		cosCoeffY = 0.1f;
		
		alphaX = 255;
		lineAlphaX = 0;
		sizeX = 5;
		
		alphaY = 255;
		lineAlphaY = 0;
		sizeY = 5;
		
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
		cometsLerpInc = 0.005f;
		
		cometsLScale = 1;
		cometsLScaleInc = 0f;
		cometsLVertexRange = xStep*0.65f*0.2f;
		cometsLBrightness = 255;
		cometsLLerpInc = 0.005f;
		
		cometsMaxScale = 0;
		
		
		//---GRID
		cols = 8;
		rows = 5;
		
		xStep = width/cols;
		yStep = height/rows;
		
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
		size(1024, 768);
		fullScreen();
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
		
		cosValX += cosIncX;
		cosValY += cosIncY;
		
		cometsScale += cometsScaleInc;
		cometsLScale += cometsLScaleInc;
	}

	public void draw() {
//		scale(0.5f);
//		translate(750, 320);
//		scale(0.65f);
//		translate(440, 150);
		update();
		noCursor();
		background(background_color);
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
		
		//debug();
//		textAlign(CENTER);
//		textSize(64);
//		fill(255);
//		text("c     o     n     n     e     c     t", width*0.5f, height*0.5f);
		if(end){
			fill(0);
			rect(0, 0, width, height);
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
				int i = (int)random(cometsL.get(0).sides);;
				for(int index = 0; index < cometsL.size(); index++){
					cometsL.get(index).targetVariations[i] += random(-cometsLVertexRange, cometsLVertexRange*2);
					cometsL.get(index).lerpValVariations[i] = 0;
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
				c.targetRotation += 90;
				c.lerpValRotation = 0;
			}else{
				for(int i = 0; i < cometsL.size(); i++){
					cometsL.get(i).targetRotation += 90;
					cometsL.get(i).lerpValRotation = 0;
				}
			}
		}
		
	}
	
	public void resetLerp(int type){
		if(type == 0){
			for(int i = 0; i < comets.size(); i++){
				if(comets.get(i).canUpdate)
					comets.get(i).lerpValRad = 0;
			}
		}else{
			for(int i = 0; i < cometsL.size(); i++){
				if(cometsL.get(i).canUpdate)
					cometsL.get(i).lerpValRad = 0;
			}
		}
	}
	
	public void addLink(int dir){
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
		}
	}
	
	public void randomScale(float v){
		cometsMaxScale += v*0.01f;
		cometsMaxScale = constrain(cometsMaxScale, 0, 1);
		for(int i = 0; i < comets.size(); i++){
			comets.get(i).scaleVarCoeff = cometsMaxScale;
		}
		for(int i = 0; i < cometsL.size(); i++){
			cometsL.get(i).scaleVarCoeff = cometsMaxScale;
		}
	}
	
	public void debug(){
		textAlign(LEFT);
		textSize(8);
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
		text("cometsScaleInc: "+cometsScaleInc, 10, 210);
		text("cometsLScaleInc: "+cometsLScaleInc, 10, 120);
		text("cometsScale: "+cometsScale, 10, 130);
		text("cometsLScale: "+cometsLScale, 10, 140);
		text("comets: "+comets.size(), 10, 150);
		text("cometspos: "+cometsPos.size(), 10, 160);
	}
	
	public void noteOn(int c, int p, int v){
		channel = c;
		pitch = p;
		velocity = v;
		
		if(c == 0){
			switch(p){
			case 44:
				addComet(0);
				break;
			case 45:
				resetLerp(0);
				break;
			case 46:
				moveVertex(0, 1);
				break;
			case 47:
				rotateComet(0, 0);
				break;
			case 48:
				rotateComet(0, 1);
				break;
			case 36://----SECOND ROW
				addComet(1);
				break;
			case 37:
				resetLerp(1);
				break;
			case 38:
				moveVertex(1, 1);
				break;
			case 39:
				rotateComet(1, 0);
				break;
			case 40:
				rotateComet(1, 1);
				break;
			case 49:
				addLink(0); //----ORTHOGONAL
				break;
			case 50:
				resetLink(0, 0); //----RESET ONE
				break;
			case 51:
				resetLink(0, 1); //----RESET ALL
				break;
			case 41:
				addLink(1); //----DIAGONAL
				break;
			case 42:
				resetLink(1, 0); //----RESET ONE
				break;
			case 43:
				resetLink(1, 1); //----RESET ALL
				break;
			default:
				break;
			}
		}else if(c == 1){
			switch(p){
			case 44:
				if(!intro)
					selectOne("particle");
				else
					selectOne("particleIntro");
				break;
			case 45:
				if(!intro)
					selectOne("particleAdd");
				else
					selectOne("particleAddIntro");
				break;
			case 36:
				if(!intro)
					selectAll("particle");
				else
					selectAll("particleIntro");
				break;
			case 50:
				clearComets(0);
				break;
			case 51:
				clearLinks(0);
				break;
			case 42:
				clearComets(1);
				break;
			case 43:
				clearLinks(1);
				break;
			default:
				break;
			}
		}
	}
	
	public void controllerChange(int c, int n, int v){
		channel = c;
		note = n;
		value = v;
		
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
				cometsScaleInc += v*0.1f;
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
				linksOStrokeWeight += v*0.5f;
				break;
			case 15:
				linksOBrightness += v*5f;
				break;
			case 16:
				linksOLerpInc += v*0.001f;
				break;
			case 17:
				selectComet(v, 1);
			case 18://--------------------------------SECOND ROW
				randomScale(v);
				break;
			case 19:
				cometsLBrightness += v*4f;
				break;
			case 20:
				cometsLLerpInc += v*0.001f;
				break;
			case 21:
				cometsLVertexRange += v;
				break;
			case 22:
				linksDStrokeWeight += v*0.5f;
				break;
			case 23:
				linksDBrightness += v*5f;
				break;
			case 24:
				linksDLerpInc += v*0.001f;
				break;
			case 25:
				selectComet(v, 0);
				break;
			default:
				break;
			}
		}else if(c == 1){
			switch(n){
			case 10:
				alphaX += v*4f;
				break;
			case 11:
				cosIncX += v*0.01f;
				break;
			case 12:
				cosCoeffX += v*1f;
				break;
			case 13:
				sizeX += v;
				break;
			case 14:
				lineAlphaX += v*4f;
				break;
			case 15:
				lineAlpha += v*4f;
				break;
			case 16:
				
				break;
			case 17:
				
				break;
			case 18://--------------------------------SECOND ROW
				alphaY += v*4f;
				break;
			case 19:
				cosIncY += v*0.01f;
				break;
			case 20:
				cosCoeffY += v*1f;
				break;
			case 21:
				sizeY += v;
				break;
			case 22:
				lineAlphaY += v*4f;
				break;
			case 23:
				lineSize += v;
				break;
			case 24:
				
				break;
			default:
				break;
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
		cometsLerpInc = constrain(cometsLerpInc, 0.005f, 0.2f);
		cometsVertexRange = constrain(cometsVertexRange, xStep*0.25f*0.1f, xStep*0.25f*0.5f);
		
		cometsLScaleInc = constrain(cometsLScaleInc, 0.0f, 10.0f);
		cometsLBrightness = constrain(cometsLBrightness, 0, 255);
		cometsLLerpInc = constrain(cometsLLerpInc, 0.0025f, 0.2f);
		cometsLVertexRange = constrain(cometsLVertexRange, xStep*0.65f*0.1f, xStep*0.65f*0.5f);
		
		linksOStrokeWeight = constrain(linksOStrokeWeight, 5, 50);
		linksOBrightness = constrain(linksOBrightness, 0, 255);
		linksOLerpInc = constrain(linksOLerpInc, 0.005f, 0.1f);
		
		linksDStrokeWeight = constrain(linksDStrokeWeight, 5, 50);
		linksDBrightness = constrain(linksDBrightness, 0, 255);
		linksDLerpInc = constrain(linksDLerpInc, 0.005f, 0.1f);
	}
	
	public void keepInRangeGrid(){
		alphaX = constrain(alphaX, 0, 255);
		cosIncX = constrain(cosIncX, 0.00025f, 0.2f);
		cosCoeffX = constrain(cosCoeffX, 0, 360);
		sizeX = constrain(sizeX, 5, 20);
		lineAlphaX = constrain(lineAlphaX, 0, 255);
		
		alphaY = constrain(alphaY, 0, 255);
		cosIncY = constrain(cosIncY, 0.00025f, 0.2f);
		cosCoeffY = constrain(cosCoeffY, 0, 360);
		sizeY = constrain(sizeY, 5, 20);
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
		}else if(keyCode == RIGHT){
			selectComet(2, 0);//select one
		}else if(keyCode == LEFT){
			selectComet(-2, 0); //select all
		}else if(keyCode == UP){
			selectComet(2, 1);
		}else if(keyCode == DOWN){
			selectComet(-2, 1);
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
		}
	}
	
	public void mousePressed(){
		addComet(0);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { cameo.Cameo.class.getName() });
	}
}

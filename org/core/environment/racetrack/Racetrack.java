package org.core.environment.racetrack;

import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import org.core.movable_object.Vehicle.Direction;
import org.core.still_object.StillObject;
import org.core.still_object.environment.Stone;
import org.core.still_object.environment.Tree;
import org.core.still_object.racetrack.Bonus;
import org.core.still_object.racetrack.Malus;
import org.gui.Loader;
import org.gui.panels.GamePanel;

public class Racetrack {

	public enum PositionOnFile { 
		UNDEFINED (0X0), DIM_OUT(0x1), DIM_IN(0x2), COORDS_OUT(0x3), COORDS_IN(0x4),
		NUM_PARTS(0x5), RANGE(0x6), DIRECTION(0x7),
		NUM_STONE(0x8), COORDS_STONE1(0x9), NUM_STONE1(0x10), COORDS_STONE2(0x11), NUM_STONE2(0x12), COORDS_STONE3(0x13),
		NUM_TREE(0x14), COORDS_TREE1(0x15), NUM_TREE1(0x16), COORDS_TREE2(0x17), NUM_TREE3(0x18), COORDS_TREE3(0x19),
		NUM_BONUS(0x20), COORDS_BONUS(0x21), NUM_MALUS(0x22), COORDS_MALUS(0x23),
		PARAM_CAR_PLAYER(0x24), NUM_CARS_COMPUTER(0x25), PARAM_CARS_COMPUTER(0x26),
		START_LINE(0x27), NUM_INTELLIGENCE_POINTS(0x28), INTELLIGENCE_POINT(0x29);
		
		private int format;
		PositionOnFile (int format) {
			this.format = format;
		}

		public static PositionOnFile fromInteger(int format) throws IllegalArgumentException {
			for (PositionOnFile dir : values()) {
				if(dir.format == format)
					return dir;
			}
			throw new IllegalArgumentException();
		}

		public int toInteger() {
			return format;
		}

		public static PositionOnFile getDefault() {
			return UNDEFINED;
		}
	}

	private PositionOnFile positionOnFile;

	private List<Point2D.Double> coordsOut;
	private List<Point2D.Double> coordsIn;

	private GeneralPath pathOut;
	private GeneralPath pathIn;

	private List<int[]> 		ranges;
	private List<Direction[]> 	directions;
	private List<GeneralPath> 	racetrackParts;
	
	private List<StillObject> 	stillObjects;

	private List<Line2D.Double> checkpoints;
	private Line2D.Double startLine;
	
	private Point2D.Double 	startPointCarPlayer;
	private Point2D.Double 	startTranslationCarPlayer;
	private Double 			startOrientationCarPlayer;

	/**
	 * parameters for cars computer, key (startPoint) - value (orientation)
	 */
	private Map<Point2D.Double, Double> paramVehicles;

	public Racetrack(File racetrackFile) throws IOException {

		coordsOut = new ArrayList<Point2D.Double>();
		coordsIn = new ArrayList<Point2D.Double>();

		ranges = new ArrayList<int[]>();
		directions = new ArrayList<Direction[]>();
		racetrackParts = new ArrayList<GeneralPath>();
		checkpoints = new ArrayList<Line2D.Double>();
		stillObjects = new ArrayList<StillObject>();
		paramVehicles = new HashMap<Point2D.Double, Double>();
		positionOnFile = PositionOnFile.DIM_OUT;
		int dim = 0;
		
		StringTokenizer item;
		for(String line: FileUtils.readLines(racetrackFile, Charset.defaultCharset())) {

			item = new StringTokenizer(line, " ");

			switch (positionOnFile) {

			case DIM_OUT:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_OUT;
				break;

			case COORDS_OUT:
				if (dim > 0) {
					coordsOut.add(new Point2D.Double(Double.parseDouble(item.nextToken()) * GamePanel.SCALE, Double.parseDouble(item.nextToken()) * GamePanel.SCALE));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.DIM_IN;
				}
				break;

			case DIM_IN:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_IN;
				break;

			case COORDS_IN:
				if (dim > 0) {
					coordsIn.add(new Point2D.Double(Double.parseDouble(item.nextToken()) * GamePanel.SCALE, Double.parseDouble(item.nextToken()) * GamePanel.SCALE));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_PARTS;
				}
				break;

			case NUM_PARTS:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.RANGE;
				break;

			case RANGE:
				if (dim > 0) {
					ranges.add(new int[]{ Integer.parseInt(item.nextToken()), Integer.parseInt(item.nextToken()), Integer.parseInt(item.nextToken()), Integer.parseInt(item.nextToken()) });
					dim--;
					if (dim == 0) {
						dim = ranges.size();
						positionOnFile = PositionOnFile.DIRECTION;
					}
				}
				break;
				
			case DIRECTION:
				if (dim > 0) {
					directions.add(new Direction[]{ Direction.fromInteger(Integer.parseInt(item.nextToken())), Direction.fromInteger(Integer.parseInt(item.nextToken())), Direction.fromInteger(Integer.parseInt(item.nextToken())) });
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_STONE;
				}
				break;
				
			case NUM_STONE:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_STONE1;
				break;

			case COORDS_STONE1:
				if (dim > 0) {
					stillObjects.add(new Stone(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()), Loader.imgStone1));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_STONE1;
				}
				break;
				
			case NUM_STONE1:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_STONE2;
				break;
				
			case COORDS_STONE2:
				if (dim > 0) {
					stillObjects.add(new Stone(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()), Loader.imgStone2));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_STONE2;
				}
				break;
				
			case NUM_STONE2:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_STONE3;
				break;
				
			case COORDS_STONE3:
				if (dim > 0) {
					stillObjects.add(new Stone(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()), Loader.imgStone3));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_TREE;
				}
				break;
				
			case NUM_TREE:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_TREE1;
				break;
				
			case COORDS_TREE1:
				if (dim > 0) {
					stillObjects.add(new Tree(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()), Loader.imgTree4));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_TREE1;
				}
				break;
				
			case NUM_TREE1:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_TREE2;
				break;
				
			case COORDS_TREE2:
				if (dim > 0) {
					stillObjects.add(new Tree(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()), Loader.imgTree5));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_TREE3;
				}
				break;
				
			case NUM_TREE3:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_TREE3;
				break;
				
			case COORDS_TREE3:
				if (dim > 0) {
					stillObjects.add(new Tree(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()), Loader.imgTree6));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_BONUS;
				}
				break;

			case NUM_BONUS:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_BONUS;
				break;
				
			case COORDS_BONUS:
				if (dim > 0) {
					stillObjects.add(new Bonus(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken())));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.NUM_MALUS;
				}
				break;
				
			case NUM_MALUS:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.COORDS_MALUS;
				break;
				
			case COORDS_MALUS:
				if (dim > 0) {
					stillObjects.add(new Malus(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken())));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.PARAM_CAR_PLAYER;
				}
				break;

			case PARAM_CAR_PLAYER:
				startPointCarPlayer = new Point2D.Double(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()));
				startTranslationCarPlayer = new Point2D.Double(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken()));
				startOrientationCarPlayer = new Double(Double.parseDouble(item.nextToken()));
				positionOnFile = PositionOnFile.NUM_CARS_COMPUTER;
				break;
				
			case NUM_CARS_COMPUTER:
				dim = Integer.parseInt(line);
				positionOnFile = PositionOnFile.PARAM_CARS_COMPUTER;
				break;
				
			case PARAM_CARS_COMPUTER:
				if (dim > 0) {
					paramVehicles.put(new Point2D.Double(Double.parseDouble(item.nextToken()), Double.parseDouble(item.nextToken())), Double.parseDouble(item.nextToken()));
					dim--;
					if (dim == 0)
						positionOnFile = PositionOnFile.START_LINE;
				}
				break;
				
			case START_LINE:
				startLine = new Line2D.Double(Double.parseDouble(item.nextToken()) * GamePanel.SCALE, Double.parseDouble(item.nextToken()) * GamePanel.SCALE, Double.parseDouble(item.nextToken()) * GamePanel.SCALE, Double.parseDouble(item.nextToken()) * GamePanel.SCALE);
				break;
				
			case UNDEFINED:
			default:
				break;
			}
			

		}
		
		for (int i = 0; i < ranges.size(); i++)
			checkpoints.add(new Line2D.Double( coordsIn.get(ranges.get(i)[0]),  coordsOut.get(ranges.get(i)[1])));

		/***************** PATH OUT ****************************************************************************************************/

		pathOut = new GeneralPath();

		pathOut.moveTo(coordsOut.get(0).x, coordsOut.get(0).y);

		for (int i = 1; i < coordsOut.size(); i+=3) {
			pathOut.curveTo	(coordsOut.get(i).x,  coordsOut.get(i).y,  
					coordsOut.get(i+1).x,  coordsOut.get(i+1).y,  
					coordsOut.get((i+2) % coordsOut.size()).x,  coordsOut.get((i+2) % coordsOut.size()).y);  
		}

		pathOut.closePath();

		/***************** PATH IN ****************************************************************************************************/

		pathIn = new GeneralPath();

		pathIn.moveTo(coordsIn.get(0).x, coordsIn.get(0).y);

		for (int i = 1; i < coordsIn.size(); i+=3) {
			pathIn.curveTo	(coordsIn.get(i).x,  coordsIn.get(i).y,  
					coordsIn.get(i+1).x,  coordsIn.get(i+1).y,  
					coordsIn.get((i+2) % coordsIn.size()).x,  coordsIn.get((i+2) % coordsIn.size()).y);  
		}

		pathIn.closePath();

		/***************** PATH PARTS ****************************************************************************************************/

		for (int i = 0; i < ranges.size(); i++) {

			GeneralPath part = new GeneralPath();

			part.moveTo(coordsIn.get(ranges.get(i)[0]).x, coordsIn.get(ranges.get(i)[0]).y);

			part.lineTo(coordsOut.get(ranges.get(i)[1]).x, coordsOut.get(ranges.get(i)[1]).y);

			for (int j = (ranges.get(i)[1] + 1); j < ranges.get(i)[2]; j+=3) {

				part.curveTo(coordsOut.get(j).x,  coordsOut.get(j).y,  
						coordsOut.get(j+1).x,  coordsOut.get(j+1).y,  
						coordsOut.get((j+2) % coordsOut.size()).x,  coordsOut.get((j+2) % coordsOut.size()).y);  
			}

			part.lineTo(coordsIn.get(ranges.get(i)[3] % coordsIn.size()).x, coordsIn.get(ranges.get(i)[3] % coordsIn.size()).y);


			for (int j = (ranges.get(i)[3] - 1); j > ranges.get(i)[0]; j-=3) {

				part.curveTo(coordsIn.get(j).x,  coordsIn.get(j).y,  
						coordsIn.get(j-1).x,  coordsIn.get(j-1).y,  
						coordsIn.get(j-2).x,  coordsIn.get(j-2).y);
			}

			part.closePath();
			
			racetrackParts.add(part);
		}
		
	}
	
	public List<StillObject> getStillObjects() { return stillObjects; }
	public List<GeneralPath> getRacetrackParts() { return racetrackParts; }

	public GeneralPath getPathOut() { return pathOut; }
	public GeneralPath getPathIn() { return pathIn; }

	public List<Point2D.Double> getCoordsOut() { return coordsOut; }
	public List<Point2D.Double> getCoordsIn() { return coordsIn; }
	
	public List<Direction[]> getDirections() { return directions; }
	public List<Line2D.Double> getCheckpoints() { return checkpoints; }
	
	public Point2D.Double getStartPointCarPlayer() { return startPointCarPlayer; }
	public Point2D.Double getStartTranslationCarPlayer() { return startTranslationCarPlayer; }
	public Double getStartOrientationCarPlayer() { return startOrientationCarPlayer; }

	public Map<Point2D.Double, Double> getParamVehicles() { return paramVehicles; }

	public Line2D.Double getStartLine() { return startLine; }

}


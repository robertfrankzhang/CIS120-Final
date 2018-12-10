import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Color;
import java.awt.Font;

public abstract class Block {
	private int x; //Represents the final destination x point
	private int y; //Represents the final destination y point
	private double cx; //Represents the current position of the x point
	private double cy; //Represents the current position of the x point
	private int ox; //Represents the original destination x point
	private int oy; //Represents the original destination y point
	private final double FRAMES_TO_ANIMATE = 6;
	private int value;
	private boolean isNew = true;
	private boolean isToBeDestroyed = false;
	private double scalingFactor = 0;
	
	private static Map<Integer,Color> colorMap = new TreeMap<Integer,Color>();
	static {
		colorMap.put(2, new Color(238,228,218));
		colorMap.put(4, new Color(236,224,200));
		colorMap.put(8, new Color(240,178,121));
		colorMap.put(16, new Color(242,140,78));
		colorMap.put(32, new Color(236,130,90));
		colorMap.put(64, new Color(225,91,56));
		colorMap.put(128, new Color(246,219,104));
		colorMap.put(256, new Color(237,204,97));
		colorMap.put(512, new Color(229,190,53));
		colorMap.put(1024, new Color(237,197,63));
		colorMap.put(2048, new Color(237,194,46));
	}
	
	public Block(int x,int y, int value) {
		this.setX(x);
		this.setY(y);
		this.setValue(value);
		this.setCX(x);
		this.setCY(y);
		this.ox = x;
		this.oy = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public double getCX() {
		return cx;
	}

	public void setCX(double cx) {
		this.cx = cx;
	}

	public double getCY() {
		return cy;
	}

	public void setCY(double cy) {
		this.cy = cy;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public void setToBeDestroyed(boolean d) {
		this.isToBeDestroyed = d;
	}
	
	public boolean getToBeDestroyed() {
		return this.isToBeDestroyed;
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Block.colorMap.get(this.value));
		int width = (int)(scalingFactor*(GameBoard.COURT_WIDTH-50)/4);
		int height = (int)(scalingFactor*(GameBoard.COURT_HEIGHT-50)/4);
        g.fillRect((int)(this.cx*(double)(GameBoard.COURT_WIDTH-10)/4+10+(width*(1/scalingFactor)-width)/2), (int)(this.cy*(double)(GameBoard.COURT_HEIGHT-10)/4+10+(height*(1/scalingFactor)-height)/2), width, height);
        g.setColor(Color.BLACK);
        
        Font font  = new Font(Font.SANS_SERIF, Font.BOLD, 40);
        g.setFont(font);
        
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(String.valueOf(this.value), frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        
        if (scalingFactor == 1) {
        	g.drawString(String.valueOf(this.value), (int)(this.getCX()*(double)(GameBoard.COURT_WIDTH-10)/4+10+(GameBoard.COURT_WIDTH-50)/8-rWidth/2), (int)(this.getCY()*(double)(GameBoard.COURT_HEIGHT-10)/4+10+(GameBoard.COURT_HEIGHT-50)/8+rHeight/4));
        }
        
        if (Math.abs(cx-x) > 0.1 || Math.abs(cy-y) > 0.1) {
        	cx+=(double)(x-ox)/FRAMES_TO_ANIMATE;
        	cy+=(double)(y-oy)/FRAMES_TO_ANIMATE;
        	GameBoard.boardSingleton.isAnimating = true;
        }else {
        	ox = x;
        	cx = x;
        	oy = y;
        	cy = y;
        }
        
        if (Math.abs(scalingFactor-1) > 0.00001) {
        	scalingFactor += 0.1;
        }else {
        	scalingFactor = 1;
        }
        	
	}
	
	public void moveLeft(ArrayList<Block> blocks) {
		int minPosition = 0;
		for (Block block:blocks) {
			if (block.getX()<this.x && block.getY() == this.y && block.getX()>=minPosition && !block.isToBeDestroyed) {
				minPosition = block.getX()+1;
			}
		}
		this.x = minPosition;
		this.isNew = false;
		combineLeft(blocks);
	}
	
	public void combineLeft(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getX() == this.x-1 && block.getY() == this.y && block.getValue() == this.value && !block.isNew && !block.isToBeDestroyed) {
				this.x-=1;
				this.value*=2;
				block.setToBeDestroyed(true);
				this.isNew = true;
				GameBoard.boardSingleton.score+=this.value;
				break;
			}
		}
	}
	
	public void moveRight(ArrayList<Block> blocks) {
		int minPosition = 3;
		for (Block block:blocks) {
			if (block.getX()>this.x && block.getY() == this.y && block.getX()<=minPosition && !block.isToBeDestroyed) {
				minPosition = block.getX()-1;
			}
		}
		this.x = minPosition;
		this.isNew = false;
		combineRight(blocks);
		
	}
	
	public void combineRight(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getX() == this.x+1 && block.getY() == this.y && block.getValue() == this.value && !block.isNew && !block.isToBeDestroyed) {
				this.x+=1;
				this.value*=2;
				block.setToBeDestroyed(true);
				this.isNew = true;
				GameBoard.boardSingleton.score+=this.value;
				break;
			}
		}
	}
	
	public void moveUp(ArrayList<Block> blocks) {
		int minPosition = 0;
		for (Block block:blocks) {
			if (block.getY()<this.y && block.getX() == this.x && block.getY()>=minPosition && !block.isToBeDestroyed) {
				minPosition = block.getY()+1;
			}
		}
		this.y = minPosition;
		this.isNew = false;
		combineUp(blocks);
	}
	
	public void combineUp(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getY() == this.y-1 && block.getX() == this.x && block.getValue() == this.value && !block.isNew && !block.isToBeDestroyed) {
				this.y-=1;
				this.value*=2;
				block.setToBeDestroyed(true);
				this.isNew = true;
				GameBoard.boardSingleton.score+=this.value;
				break;
			}
		}
	}
	
	public void moveDown(ArrayList<Block> blocks) {
		int minPosition = 3;
		for (Block block:blocks) {
			if (block.getY()>this.y && block.getX() == this.x && block.getY()<=minPosition && !block.isToBeDestroyed) {
				minPosition = block.getY()-1;
			}
		}
		this.y = minPosition;
		this.isNew = false;
		combineDown(blocks);
	}
	
	public void combineDown(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getY() == this.y+1 && block.getX() == this.x && block.getValue() == this.value && !block.isNew && !block.isToBeDestroyed) {
				this.y+=1;
				this.value*=2;
				block.setToBeDestroyed(true);
				this.isNew = true;
				GameBoard.boardSingleton.score+=this.value;
				break;
			}
		}
	}
	
	
}

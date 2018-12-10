import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BombBlock extends Block{
	private boolean isToExplode = false;
	
	public BombBlock(int x, int y) {
		super(x, y, 5);
	}
	
	@Override
	public void moveLeft(ArrayList<Block> blocks) {
		super.moveLeft(blocks);
		combineLeft(blocks);
	}
	
	@Override
	public void combineLeft(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getX() == super.getX()-1 && block.getY() == super.getY() && block.getValue() == super.getValue() && !block.isNew() && !block.getToBeDestroyed()) {
				super.setX(super.getX()-1);
				block.setToBeDestroyed(true);
				isToExplode = true;
				special(blocks);
				break;
			}
		}
	}
	
	@Override
	public void moveRight(ArrayList<Block> blocks) {
		super.moveRight(blocks);
		combineRight(blocks);
	}
	
	@Override
	public void combineRight(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getX() == super.getX()+1 && block.getY() == super.getY() && block.getValue() == super.getValue() && !block.isNew() && !block.getToBeDestroyed()) {
				super.setX(super.getX()+1);
				block.setToBeDestroyed(true);
				isToExplode = true;
				special(blocks);
				break;
			}
		}
	}
	
	@Override
	public void moveUp(ArrayList<Block> blocks) {
		super.moveUp(blocks);
		combineUp(blocks);
	}
	
	@Override
	public void combineUp(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getY() == super.getY()-1 && block.getX() == super.getX() && block.getValue() == super.getValue() && !block.isNew() && !block.getToBeDestroyed()) {
				super.setY(super.getY()-1);
				block.setToBeDestroyed(true);
				isToExplode = true;
				special(blocks);
				break;
			}
		}
	}
	
	@Override
	public void moveDown(ArrayList<Block> blocks) {
		super.moveDown(blocks);
		combineDown(blocks);
	}
	
	@Override
	public void combineDown(ArrayList<Block> blocks) {
		for (Block block:blocks) {
			if (block.getY() == super.getY()+1 && block.getX() == super.getX() && block.getValue() == super.getValue() && !block.isNew() && !block.getToBeDestroyed()) {
				super.setY(super.getY()+1);
				block.setToBeDestroyed(true);
				isToExplode = true;
				special(blocks);
				break;
			}
		}
	}
	
	@Override
	public void special(ArrayList<Block> blocks) {
		if (isToExplode) {
			for (Block block:blocks) {
				if ((Math.abs(block.getY()-super.getY()) == 1 && Math.abs(block.getX()-super.getX())==0) ||
					(Math.abs(block.getY()-super.getY()) == 0 && Math.abs(block.getX()-super.getX())==1)) {
					block.setToBeDestroyed(true);
				}
			}
			super.setToBeDestroyed(true);

		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Block.getColorMap(super.getValue()));
		int width = (int)(super.getScalingFactor()*(GameBoard.COURT_WIDTH-50)/4);
		int height = (int)(super.getScalingFactor()*(GameBoard.COURT_HEIGHT-50)/4);
        g.fillRect((int)(this.getCX()*(double)(GameBoard.COURT_WIDTH-10)/4+10+(width*(1/super.getScalingFactor())-width)/2), (int)(this.getCY()*(double)(GameBoard.COURT_HEIGHT-10)/4+10+(height*(1/super.getScalingFactor())-height)/2), width, height);
        g.setColor(Color.WHITE);
        
        String s = "Bomb";
        
        Font font  = new Font(Font.SANS_SERIF, Font.BOLD, 25);
        g.setFont(font);
        
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        
        if (super.getScalingFactor() == 1) {
        	g.drawString(s, (int)(this.getCX()*(double)(GameBoard.COURT_WIDTH-10)/4+10+(GameBoard.COURT_WIDTH-50)/8-rWidth/2), (int)(this.getCY()*(double)(GameBoard.COURT_HEIGHT-10)/4+10+(GameBoard.COURT_HEIGHT-50)/8+rHeight/4));
        }
        
        if (Math.abs(super.getCX()-super.getX()) > 0.01 || Math.abs(super.getCY()-super.getY()) > 0.01) {
        	super.setCX(super.getCX()+(double)(super.getX()-super.getOX())/super.getFramesToAnimate());
        	super.setCY(super.getCY()+(double)(super.getY()-super.getOY())/super.getFramesToAnimate());
        	GameBoard.boardSingleton.isAnimating = true;
        }else {//If no blocks have moved, isAnimating never turns on, so next block doesn't appear
        	super.setOX(super.getX());
        	super.setCX(super.getX());
        	super.setOY(super.getY());
        	super.setCY(super.getY());
        }
        
        if (Math.abs(super.getScalingFactor()-1) > 0.00001) {
        	super.setScalingFactor(super.getScalingFactor() + 0.1);
        }else {
        	super.setScalingFactor(1);
        }
        	
	}
	
}

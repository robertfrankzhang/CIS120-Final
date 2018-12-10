import java.awt.Graphics;
import java.util.ArrayList;

public class RegularBlock extends Block{
	public RegularBlock(int x, int y, int value) {
		super(x, y, value);
	}
	
	@Override
	public void moveLeft(ArrayList<Block> blocks) {
		super.moveLeft(blocks);
		super.combineLeft(blocks);
	}
	
	@Override
	public void moveRight(ArrayList<Block> blocks) {
		super.moveRight(blocks);
		super.combineRight(blocks);
	}
	
	@Override
	public void moveUp(ArrayList<Block> blocks) {
		super.moveUp(blocks);
		super.combineUp(blocks);
	}
	
	@Override
	public void moveDown(ArrayList<Block> blocks) {
		super.moveDown(blocks);
		super.combineDown(blocks);
	}
}

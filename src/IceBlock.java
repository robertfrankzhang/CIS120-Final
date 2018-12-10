import java.util.ArrayList;

public class IceBlock extends Block{
	public IceBlock(int x, int y) {
		super(x, y, 7);
	}
	
	@Override
	public void moveLeft(ArrayList<Block> blocks) {
		super.moveLeft(blocks);
		combineLeft(blocks);
	}
	
	@Override
	public void combineLeft(ArrayList<Block> blocks) {
		
	}
	
	@Override
	public void moveRight(ArrayList<Block> blocks) {
		super.moveRight(blocks);
		combineRight(blocks);
	}
	
	@Override
	public void combineRight(ArrayList<Block> blocks) {
		
	}
	
	@Override
	public void moveUp(ArrayList<Block> blocks) {
		super.moveUp(blocks);
		combineUp(blocks);
	}
	
	@Override
	public void combineUp(ArrayList<Block> blocks) {
		
	}
	
	@Override
	public void moveDown(ArrayList<Block> blocks) {
		super.moveDown(blocks);
		combineDown(blocks);
	}
	
	@Override
	public void combineDown(ArrayList<Block> blocks) {
		
	}
}

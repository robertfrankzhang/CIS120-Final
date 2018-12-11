
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;


public class Tests {
	
	//Test Regular MoveLeft
	@Test
	public void regularMoveLeft() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new RegularBlock(1,1,2);
		Block block2 = new RegularBlock(3,1,4);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),0);
		assertEquals(blocks.get(1).getX(),1);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test Regular MoveRight
	@Test
	public void regularMoveRight() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new RegularBlock(1,1,2);
		Block block2 = new RegularBlock(2,1,4);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),2);
		assertEquals(blocks.get(1).getX(),3);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test Regular MoveUp
	@Test
	public void regularMoveUp() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new RegularBlock(1,1,2);
		Block block2 = new RegularBlock(1,2,4);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getY() == i) {
	    			block.moveUp(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),1);
		assertEquals(blocks.get(1).getX(),1);
		assertEquals(blocks.get(0).getY(),0);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test Regular MoveDown
	@Test
	public void regularMoveDown() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new RegularBlock(1,1,2);
		Block block2 = new RegularBlock(1,2,4);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getY() == i) {
	    			block.moveDown(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),1);
		assertEquals(blocks.get(1).getX(),1);
		assertEquals(blocks.get(0).getY(),2);
		assertEquals(blocks.get(1).getY(),3);
	}
	
	//Test Regular MoveLeftCombo
	@Test
	public void regularMoveLeftCombo() {
		GameBoard board = new GameBoard(new JLabel(),new JPanel());
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new RegularBlock(1,1,2);
		Block block2 = new RegularBlock(3,1,2);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		
		blocks = removeNullBlocks(blocks);
		assertEquals(blocks.size(),1);
		assertEquals(blocks.get(0).getX(),0);
		assertEquals(blocks.get(0).getY(),1);
		
	}
	
	//Test Regular MoveRightCombo
	@Test
	public void regularMoveRightCombo() {
		GameBoard board = new GameBoard(new JLabel(),new JPanel());
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new RegularBlock(1,1,2);
		Block block2 = new RegularBlock(2,1,2);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		
		blocks = removeNullBlocks(blocks);
		assertEquals(blocks.size(),1);
		assertEquals(blocks.get(0).getX(),3);
		assertEquals(blocks.get(0).getY(),1);
	}
		
	//Test WildCard MoveLeft
	@Test
	public void wildMoveLeft() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new WildCardBlock(1,1);
		Block block2 = new BombBlock(3,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),0);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),1);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test WildCard MoveRight
	@Test
	public void wildMoveRight() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new WildCardBlock(1,1);
		Block block2 = new BombBlock(2,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),2);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),3);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test WildCard MoveLeftCombo
	@Test
	public void wildMoveLeftCombo() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new WildCardBlock(1,1);
		Block block2 = new WildCardBlock(3,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		blocks = removeNullBlocks(blocks);
		
		assertEquals(blocks.size(),0);
	}
	
	//Test WildCard MoveRightCombo
	@Test
	public void wildMoveRightCombo() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new WildCardBlock(1,1);
		Block block2 = new WildCardBlock(2,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		blocks = removeNullBlocks(blocks);
		
		assertEquals(blocks.size(),0);
	}
	
	
	//Test IceBlock MoveLeft
	@Test
	public void IceMoveLeft() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new IceBlock(1,1);
		Block block2 = new BombBlock(3,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),0);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),1);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test IceBlock MoveRight
	@Test
	public void IceMoveRight() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new IceBlock(1,1);
		Block block2 = new BombBlock(2,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),2);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),3);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test IceBlock MoveLeftMerger
	@Test
	public void IceMoveLeftCombo() {
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new IceBlock(3,1);
		Block block2 = new IceBlock(1,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		blocks = removeNullBlocks(blocks);
		assertEquals(blocks.get(0).getX(),2);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),0);
		assertEquals(blocks.get(1).getY(),1);
		assertEquals(blocks.size(),2);
	}
	
	//Test IceBlock MoveRightMerger
	@Test
	public void IceMoveRightCombo() {
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new IceBlock(0,1);
		Block block2 = new IceBlock(2,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		
		blocks = removeNullBlocks(blocks);
		assertEquals(blocks.get(0).getX(),1);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),3);
		assertEquals(blocks.get(1).getY(),1);
		assertEquals(blocks.size(),2);
	}
	
	
	//Test BombBlock MoveLeft
	@Test
	public void BombMoveLeft() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new WildCardBlock(1,1);
		Block block2 = new BombBlock(3,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),0);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),1);
		assertEquals(blocks.get(1).getY(),1);
	}
	
	//Test BombBlock MoveRight
	@Test
	public void BombMoveRight() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new WildCardBlock(1,1);
		Block block2 = new BombBlock(2,1);
		blocks.add(block1);
		blocks.add(block2);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		
		assertEquals(blocks.get(0).getX(),2);
		assertEquals(blocks.get(0).getY(),1);
		assertEquals(blocks.get(1).getX(),3);
		assertEquals(blocks.get(1).getY(),1);
	}
		
	//Test BombBlock MoveLeftMerger
	@Test
	public void BombMoveLeftCombo() {
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new BombBlock(0,1);
		Block block2 = new BombBlock(2,1);
		Block block3 = new RegularBlock(0,0,2);
		Block block4 = new RegularBlock(0,2,2);
		blocks.add(block1);
		blocks.add(block2);
		blocks.add(block3);
		blocks.add(block4);
		
		for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
		blocks = removeNullBlocks(blocks);
		
		assertEquals(blocks.size(),3);
	}
		
	//Test BombBlock MoveRightMerger
	@Test
	public void BombMoveRightCombo() {
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block1 = new BombBlock(0,1);
		Block block2 = new BombBlock(2,1);
		Block block3 = new RegularBlock(3,0,2);
		Block block4 = new RegularBlock(3,2,2);
		blocks.add(block1);
		blocks.add(block2);
		blocks.add(block3);
		blocks.add(block4);
		
		for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
		blocks = removeNullBlocks(blocks);
		
		assertEquals(blocks.size(),3);
	}

	
	//Utility Classes:
	private ArrayList<Block> removeNullBlocks(ArrayList<Block> blocks) {
    	
		ArrayList<Block> newBlocks = new ArrayList<Block>();
    	for (Block block:blocks) {
    		if (!block.getToBeDestroyed()) {
    			newBlocks.add(block);
    		}
    	}
    	return newBlocks;
    	
    }

}

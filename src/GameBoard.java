import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {
	public static GameBoard boardSingleton;
	
	// Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 10;
    
    public boolean isAnimating = false;
    
    public int score = 0;
    
    private JLabel status; // Current status text, i.e. "Running..."
    private JPanel statusPanel;
    private JButton replay;
    private boolean firstIteration = true;
    
    private ArrayList<Block> blocks = new ArrayList<Block>();
    
    private ArrayList<Block> toBeDestroyedBlocks = new ArrayList<Block>();
    
    private boolean isGameOver = false;

    public GameBoard(JLabel status, JPanel status_panel) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	if (!isAnimating && !isGameOver && (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP)) {
	            	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	                    moveLeft();
	                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	                    moveRight();
	                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	                    moveDown();
	                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
	                    moveUp(); 
	                }
	            	postMovementSpecials();
	            	removeNullBlocks();
            	}
            }
        });

        this.status = status;
        this.statusPanel = status_panel;
        
        GameBoard.boardSingleton = this;
    }
    
    private void generateBlock() {
    	boolean isValid = true;
    	int x;
    	int y;
    	int value;
    	do {
	    	x = (int)(Math.random()*4);
	    	y = (int)(Math.random()*4);
	    	isValid = true;
	    	for (Block block:blocks) {
	    		if (block.getX() == x && block.getY() == y) {
	    			isValid = false;
	    		}
	    	}
    	}while(!isValid);
    	
    	double blockDecider = Math.random();
    	
    	if (blockDecider<0.1) value = 4;
    	else if (blockDecider<0.93) value = 2;
    	else if (blockDecider<0.96) value = 3;
    	else if (blockDecider<0.98) value = 5;
    	else value = 7;
    	
    	Block b;
    	if (value == 3) {
    		b = new WildCardBlock(x,y);
    	}else if (value == 5) {
    		b = new BombBlock(x,y);
    	}else if (value == 7) {
    		b = new IceBlock(x,y);
    	}
    	else {
    		b = new RegularBlock(x,y,value);
    	}
	    
	    blocks.add(b);
    	
    }
    
    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        
        status.setText("Score: 0");
        statusPanel.add(status);
        if (replay != null) {
        	System.out.println("removed");
        	statusPanel.remove(replay);
        }
        isGameOver = false;
        isAnimating = false;
        score = 0;
        firstIteration = true;
        blocks = new ArrayList<Block>();
        for (int i = 0; i<4; i++) {
        	generateBlock();
        }
        toBeDestroyedBlocks = new ArrayList<Block>();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
        statusPanel.revalidate();
        statusPanel.repaint();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
            // update the display
            repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(202,193,181));
        g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
        g.setColor(new Color(185,173,161));
        g.fillRect(0, 0, 10, COURT_HEIGHT);
        g.fillRect((COURT_WIDTH-10)/4, 0, 10, COURT_HEIGHT);
        g.fillRect((COURT_WIDTH-10)/2, 0, 10, COURT_HEIGHT);
        g.fillRect((COURT_WIDTH-10)*3/4, 0, 10, COURT_HEIGHT);
        g.fillRect(COURT_WIDTH-10, 0, 10, COURT_HEIGHT);
        
        g.fillRect(0, 0, COURT_WIDTH,10);
        g.fillRect(0,(COURT_HEIGHT-10)/4, COURT_WIDTH,10);
        g.fillRect(0,(COURT_HEIGHT-10)/2, COURT_WIDTH,10);
        g.fillRect(0,(COURT_HEIGHT-10)*3/4, COURT_WIDTH,10);
        g.fillRect(0,COURT_HEIGHT-10, COURT_WIDTH,10);
        
        boolean doneAnimating = true;
        for (Block block:toBeDestroyedBlocks) {
        	block.draw(g);
        	if (block.getCX() != block.getX() || block.getCY() != block.getY()) {
        		doneAnimating = false;
	        }
        }
        
        for (Block block:blocks) {
        	block.draw(g);
        	if (block.getCX() != block.getX() || block.getCY() != block.getY()) {
        		doneAnimating = false;
	        }
        }
        
        if (doneAnimating && isAnimating || blocks.size() == 0) {
        	updateScore();
        	isAnimating = false;
        	toBeDestroyedBlocks.clear();
        	generateBlock();
        	
        	boolean isWin = checkWin();
        	boolean isLose = checkLose();
        	if (isWin) {
        		status.setText("You win with a score of "+String.valueOf(score)+"!");
        	}else if (isLose) {
        		status.setText("You lose with a score of "+String.valueOf(score));
        	}
        	isGameOver = isWin || isLose;
        }
        
        if (isGameOver) {
        	g.setColor(new Color(0,0,0,200));
            g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
            if (firstIteration) {
	            statusPanel.remove(status);
	            replay = new JButton("Replay");
	            replay.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    reset();
	                }
	            });
	            System.out.println("added");
	            statusPanel.add(replay);
	            firstIteration = false;
            }
        }
    }
    
    private void moveLeft() {
    	for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveLeft(blocks);
	    		}
	    	}
    	}
    }
    
    private void moveRight() {
    	for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getX() == i) {
	    			block.moveRight(blocks);
	    		}
	    	}
    	}
    }
    
    private void moveUp() {
    	for (int i = 0; i<4; i++) {
	    	for (Block block:blocks) {
	    		if (block.getY() == i) {
	    			block.moveUp(blocks);
	    		}
	    	}
    	}
    }
    
    private void moveDown() {
    	for (int i = 3; i>=0; i--) {
	    	for (Block block:blocks) {
	    		if (block.getY() == i) {
	    			block.moveDown(blocks);
	    		}
	    	}
    	}
    }
    
    private void postMovementSpecials() {
    	for (Block block:blocks) {
    		block.special(blocks);
    	}
    }
    
    private void removeNullBlocks() {
    	
		ArrayList<Block> newBlocks = new ArrayList<Block>();
    	for (Block block:blocks) {
    		if (block.getToBeDestroyed()) {
    			Block destroyedBlock;
    			if (block.getValue() == 3) {
    				destroyedBlock = new WildCardBlock(block.getX(),block.getY());
    			}else if (block.getValue() == 5) {
    				destroyedBlock = new BombBlock(block.getX(),block.getY());
    			}else if (block.getValue() == 7) {
    				destroyedBlock = new IceBlock(block.getX(),block.getY());
    			}
    			else {
    				destroyedBlock = new RegularBlock(block.getX(),block.getY(),block.getValue());
    			}
    			destroyedBlock.setCX(block.getCX());
				destroyedBlock.setCY(block.getCY());
				destroyedBlock.setOX(block.getOX());
				destroyedBlock.setOY(block.getOY());
    			if (!toBeDestroyedBlocks.contains(destroyedBlock))
    			toBeDestroyedBlocks.add(destroyedBlock);
    		}else {
    			
    			newBlocks.add(block);
    		}
    	}
    	blocks = newBlocks;
    	
    }
    
    private boolean checkWin() {
    	for (Block block:blocks) {
    		if (block.getValue() >= 2048) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean checkLose() {
    	boolean hasLost = true;
    	for (Block block:blocks) {
    		int counter = 0;
    		int counter2 = 4;
    		if (block.getX() == 0 || block.getX() == 3) {
				counter2 -= 1;
			}
			if (block.getY() == 0 || block.getY() == 3) {
				counter2 -= 1;
			}
    		for (Block surrounding:blocks) {
    			if ((Math.abs(surrounding.getX()-block.getX()) == 0 && Math.abs(surrounding.getY()-block.getY()) == 1)||
    				(Math.abs(surrounding.getX()-block.getX()) == 1 && Math.abs(surrounding.getY()-block.getY()) == 0)) {
    				if (block.getValue() != 3 && block.getValue() != 5 && block.getValue() != 7) {
    					if (surrounding.getValue() != block.getValue() && surrounding.getValue() != 3) {
    						counter+=1;
    					}
    				}else {
    					if (surrounding.getValue() != block.getValue()) {
    						counter+=1;
    					}
    				}
    			}
    		}
    		if (counter != counter2) {
    			hasLost = false;
    			break;
    		}
    	}
    	return hasLost;
    }
    
    public void updateScore() {
    	status.setText("Score: "+String.valueOf(score));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}

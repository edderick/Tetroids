public class TetroidGrid {

	//The grid that contains all the docked blocks
	private int[][] grid;
	
	//The dimensions of the grid
	private int height, width;

	//The offsets of the grid on the screen
	private int xOffset, yOffset;
	
	//The window that the grid will be drawn to
	private DemoWindow window;
	
	//The score and lost flag for the game
	private int score;
	private boolean lost;
	
	//Variables used to control the flow of the game
	private float tDelay;
	private int clock;
	
	
	//the constructor of the grid object
	public TetroidGrid(DemoWindow window){
		
		//Sets the instance variable to the argument
		this.window = window;
		
		//Resets score to zero
		score = 0;
		
		//Sets grids dimensions
		width = 10;
		height = 20;
		xOffset = 1;
		yOffset = 0;
		
		//Resets the variables that control the flow
		tDelay = 15;
		clock = 0;
		lost = false;
		
		//Initialises the array
		grid = new int[width][height];
		
		//Fills the array with 0s
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				grid[i][j] = 0;		
			}
		}
		draw();	
	}
	
	
	//From top to bottom checks for completed lines
	public void checkLines(){
				
		//Flag for full lines
		Boolean lineFull;
		int cleared = 0;
		
		for(int j = 1; j < height; j++){
			//Assumes line is full
			lineFull = true;
			for(int i= 0; i < width; i++){
				//Sets line to false if it isn't
				if(grid[i][j] == 0) lineFull = false;
			}
			if(lineFull){
				cascade(j);
				cleared++;
				tDelay -= 0.1;
			}
			
		}

		//Increments the score depending on the number of lines cleared
		switch(cleared){
			case 1: score+= 10; break;
			case 2: score+= 20; break;
			case 3: score+= 40; break;
			case 4: score+= 80; break;
		}
		
	}
	
	
	//Removes given line by moving all the lines above the line down one
	public void cascade(int line){
		for(int j = line; j > 0; j--){
			for(int i= 0; i < width; i++){
				grid[i][j] = grid[i][j-1]; 
			}
		}
	}
	
	
	//Draws out all of the stationary blocks
	public void draw(){
		//Loops through all of the grid squares,
	    //Colouring them in the appropriate colour
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				window.setGridColour(i + xOffset,j + yOffset, grid[i][j]);
			}
		}	
	}
	
	
	//Checks thats the given square is full
	public boolean isFull(int x, int y){
		
		//This works as though there are bricks at the siders and bottom of the play area
		if((x < 0) || (x >= width) || (y >= height)) return true; 
		//If it is off the top of the screen it must be empty
		if(y < 0) return false;
		if(grid[x][y]!=0) return true;
		return false;
	}
	
	//The set and get functions	
	public int getWidth(){
		return width;
	}
	
	
	public int getHeight(){
		return height;
	}
	
	
	public DemoWindow getWindow(){
		return window;
	}
	
	
	public int getXOffset(){
		return xOffset;
	}
	
	
	public int getYOffset(){
		return yOffset;
	}
	
	
	public void setGrid(int x , int y, int number){
		grid[x][y]= number;
	}
	
	
	public int getTDelay(){
		return (int) Math.floor(tDelay);
	}
	
	
	public boolean getLost(){
		return lost;
	}
	
	
	public void setLost(boolean state){
		lost = state;
	}
	
	
	public int getClock(){
		return clock;
	}

	
	public void incrementClock(){
		clock++;
	}
	
	public int getScore(){
		return score;
	}
}

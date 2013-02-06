/*For tommorow, maybe take out:

boolean collides = false;
					for(int i = 0; i < 5; i++){
						for(int j= 0; j < 5; j++){
							if((shape[i][j] == 1) && ((tGrid.isFull(x + i - 2 + 1, y + j - 2) ))){
								collides = true;
							}
						}
					}
					if(!collides) x++;
				
That appears several times, and replace it with a doesCollide() method..?

*/

import java.util.Random;

public class Tetroid {
	
	//The coordinates of the tetroid
	private int x, y;
	
	//The kind of tetroid
	int type;
	
	//Array that represents the shape
		int[][] shape;
	
	//The number of squares above (or left) of the centre.
	int height, width;
	
	//The window and grid the tetroid will be drawn to
	DemoWindow window;
	TetroidGrid tGrid;
	
	//Flag for whether the shape is still in play
	boolean docked; 
	
	//The colour of the shape
	private int colour;
	
	//Matrix for rotation
	private int[][] matrix = new int[2][2];
	
	
	//The constructor, chooses a random type
	public Tetroid(TetroidGrid tGrid){
		
		//Sets the instance variables to reference the arguments
		this.tGrid = tGrid;
		window = this.tGrid.getWindow();
		
		//Fills the matrix with the correct values
		initialiseMatrix();		

		//Creates the array for the tetroid and sets the dimension
		//5 was used as it is an odd number and this allows a centre square
		shape = new int[5][5];
		width = 2;
		height = 2;		
		
		//Sets the type to a random number
		Random r = new Random();
		type = r.nextInt(7);	
		setShape(type);
		
		//Sets the colour based on the type of shape
		colour = calculateColour();
		
		//Puts the shape above the screen, in the centre
		x = (tGrid.getWidth() / 2) - 1;
		y = -1; 
	
			
		docked = false;
		
		draw();
	}
	
	
	//Sets the kind of shape, fills the array
	public void setShape(int type){
		//Fill array empty
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				shape[i][j] = 0;
			}
		}
		
		//Fills array in shape, correct colour
		switch(type){
			//I
			case 0:{
				shape[1][2] = 1;
				shape[2][2] = 1;
				shape[3][2] = 1;
				shape[4][2] = 1;
				break;
			}
			//T
			case 1:{
				shape[2][2] = 1;
				shape[2][1] = 1;
				shape[1][2] = 1;
				shape[3][2] = 1;
				break; 
			}
			//J
			case 2:{
				shape[1][1] = 1;
				shape[1][2] = 1;
				shape[2][2] = 1;
				shape[3][2] = 1;
				break;
			}
			//L
			case 3:{
				shape[3][1] = 1;
				shape[1][2] = 1;
				shape[2][2] = 1;
				shape[3][2] = 1;
				break;
			}
			//S
			case 4:{
				shape[2][2] = 1;
				shape[2][1] = 1;
				shape[3][2] = 1;
				shape[3][3] = 1;
				break;
			}
			//Z
			case 5:{
				shape[2][2] = 1;
				shape[3][1] = 1;
				shape[3][2] = 1;
				shape[2][3] = 1;				
				break;
			}
			//O
			case 6:{
				shape[3][1] = 1;
				shape[2][1] = 1;
				shape[2][2] = 1;
				shape[3][2] = 1;
				break;
			}
		}
	}
	
	
	//Called when the tetroid has fallen as far as it can (the squares directly below are full)
	public void dock(){
		if (!docked){
			docked = true;
			//Moves all the cells coloured by the tetroid onto the grid
			for(int i = 0; i < 5; i++){
				for(int j= 0; j < 5; j++){
					if (shape[i][j] == 1){
						//Checks that the player has lost
						if(y + j + tGrid.getYOffset() - height < 0){
							System.out.println("You have lost");
							tGrid.setLost(true);
						}
						else{
							//Minus 2 centres it from the shapes array
							tGrid.setGrid(x + i - width, y + j - height, colour); 
						}
					}
				}
			}
			tGrid.checkLines();
		}
	}
	
	
	//Simulates gravity by moving the Tetroid down one space
	public void gravity(){
		
		boolean collides = false;
		
		for(int i = 0; i < 5; i++){
			for(int j= 0; j < 5; j++){
				//Checks if the cell bellow is full
				if((shape[i][j] == 1) && ((tGrid.isFull(x + i - width, y + j - height + 1) ))){
					collides = true;
				} 
			}
		}
		
		if(!collides){
			//If it doesn't collide move it down
			y++;
		}
		else {
			//If it does collide, dock it
			dock();
		}
	}
	
	
	//Moves the block in the given direction
	public void move(int buttonPressed){
		switch(buttonPressed){
				
				//When the right arrow is pressed, check it can move, then move it
				case 39:{
					boolean collides = false;
					//loops through all the grid squares ensuring there are no collisions
					for(int i = 0; i < 5; i++){
						for(int j= 0; j < 5; j++){
							if((shape[i][j] == 1) && ((tGrid.isFull(x + i - width + 1, y + j - height) ))){
								collides = true;
							}
						}
					}
					if(!collides) x++;
				
					break; 
				}
				//When the down arrow is pressed, check it can move, then move it
				case 40:{
					boolean collides = false;
					//loops through all the grid squares ensuring there are no collisions
					for(int i = 0; i < 5; i++){
						for(int j= 0; j < 5; j++){
							if((shape[i][j] == 1) && ((tGrid.isFull(x + i - width, y + j - height + 1) ))){
								collides = true;
							}
						}
					}
					if(!collides){y++;}
					else{dock();}
				
					break; 
				}
				//When the left arrow is pressed, check it can move, then move it
				case 37:{
					boolean collides = false;
					//loops through all the grid squares ensuring there are no collisions
					for(int i = 0; i < 5; i++){
						for(int j= 0; j < 5; j++){
							if((shape[i][j] == 1) && ((tGrid.isFull(x + i - width - 1, y + j - height) ))){
								collides = true;
							}
						}
					}
					if(!collides) x--;
					break; 
				}
				//When the up arrow is pressed, check it can move, then move it
				case 38: rotate();
		}
	}
	
	
	//Sets up the rotation matrix 
	private void initialiseMatrix(){
		//Because we only need to rotate by 90 degrees, the matrix is very simple
		matrix[0][0]= 0; //cos 90
		matrix[1][0]= 1; //sin 90
		matrix[0][1]= -1; //-sin 90
		matrix[1][1]= 0; //cos 90
	}
	
	
	//Applies a rotation matrix to the shape
	public void rotate(){

	    if(type != 6){ ///Type 6 doesn't need rotating as it is symmetrical
		
	    //arrays to store the new values of x and y
	    int[] newX = new int[4];
		int[] newY = new int[4];
		int squareNo = 0;
		
		//Loops through all the cells rotating them if they are full
		for(int i = 0; i < 5; i++){
			for(int j= 0; j < 5; j++){
				if (shape[i][j] == 1){
					int x = i-2;
					int y = j-2;
					newX[squareNo] = (matrix[0][0] * x) + (matrix[0][1] * y) + 2;
					newY[squareNo] = (matrix[1][0] * x) + (matrix[1][1] * y ) + 2;
					
					squareNo++;
					
				}
			}
		}
		
		//Makes sure the rotation is possible
		boolean collides = false;
		for(int i= 0; i < 4; i++){
			if(tGrid.isFull(x + newX[i] - 2, y + newY[i] - 2) ){
				collides = true;
			}	
			
		}
		
		//Sets it all to zero
		if(!collides){
		for(int i = 0; i < 5; i++){
			for(int j= 0; j < 5; j++){
					shape[i][j] = 0;
			}
		}
		
		//Fills in the cells that contain the shape
		for(int i= 0; i < 4; i++	){
			shape[newX[i]][newY[i]] = 1;
		}
		}
	    }
	}
	
	
	public void draw(){
		//Draws the tetroid in the correct place on the grid
		for(int i = 0; i < 5; i++){
			for(int j= 0; j < 5; j++){
				if (shape[i][j] == 1){
					//Allows shape to be drawn above the screen
					if(y + j + tGrid.getYOffset() - 2 >= 0){
					window.setGridColour(x + i + tGrid.getXOffset() - 2, y + j + tGrid.getYOffset() - 2, colour); //Minus 2 centres it from the shapes array
				
					}
				}
			}	
		}
	}
	
	//Allows access to docked
	public boolean getDocked(){
		
		return docked;
		
	}
	
	//Works out what colour the shape should be
	private int calculateColour(){
		
		return (type + 2);
		
	}
	
}

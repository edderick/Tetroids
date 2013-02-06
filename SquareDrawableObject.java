
public class SquareDrawableObject extends DrawableObject{

	private int x, y, colour;
	//The height and width of the square. 
	int height = 1; //Number of cells above (and below) the centre
	int width = 1;  //Number of cells left (and right) of the centre

	
	//Returns the width of the Square
	public int getWidth(){
		return width;
	}
	
	
	//Returns the height of the Square
	public int getHeight(){
		return width;
	}
	
	
	//Attempts to move the square, returns false if the square is moved off screen
	public boolean move(int direction, int gridWidth, int gridHeight){
		//Flag set true upon failure
		boolean fail= false;
		
		//Choose appropriate action
		switch(direction){
			case 0: if (y - height > 0){y--;}else{fail = true;} break; //Up
			case 1: if (x + width < gridWidth -1){x++;}else{fail = true;} break; //Right
			case 2: if (y + height < gridHeight -1){y++;}else{fail = true;} break; //Down
			case 3: if (x - width > 0){x--;}else{fail = true;} break; //Left
		}
		if(fail){
			//Prints error to the error stream
			System.err.println("Tried to move offscreen");
			return false;
		}
		else{
			return true;	
		}
	}
	
	
	//The constructor for SquareDrawableObject
	public SquareDrawableObject(int x, int y, int colour){
		
		//Yummy bit of scope 
		this.x = x;
		this.y = y;
		this.colour= colour;
		
	}
	
	
	//Allows outside methods to change x and y
	public void moveTo(int x, int y){
		
		//Yummy bit of scope 
		this.x = x;
		this.y = y;
		
	}
	
	
	//Draws the square on the screen
	public void draw(GraphicsController arg0){
		//Colours all the cells that are part of the square
		for(int i = (x-width); i <= (x+width); i++){
			for(int j = (y-height); j <= (y+height); j++){
				arg0.setGridColour(i,j,colour);
			}
		}
	}

	
}

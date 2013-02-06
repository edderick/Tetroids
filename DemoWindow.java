import java.util.Random;

public class DemoWindow extends WindowController {

	private int lastButtonPressed;
	private SquareDrawableObject square;

	//Random number generator called by many methods
	private Random r = new Random(); 

	//The co-ordinates of the "snail" for button B's action
	private int snailX;
	private int snailY;

	//Objects for the game
	private TetroidGrid tGrid;
	private Tetroid currentTetroid;


	//Called when application starts
	public static void main(String[] args){
		//Create new window and calls the constructor
		DemoWindow myDemoWindow = new DemoWindow();
	}


	//Default Constructor
	public DemoWindow(){
		//Calls the parents constructor, passing the title and the number of buttons
		super("Edward Seabrook", 5 , 12 , 20 , 25, true);

		//Re-label the buttons
		this.setButtonText(0, "A");
		this.setButtonText(1, "B");
		this.setButtonText(2, "C");
		this.setButtonText(3, "D");
		this.setButtonText(4, "Tetroids");

		//Starts the application running
		this.start();
	}


	//Called when ANY button is pressed
	@Override
		protected void buttonPressed(int arg0) {

			String buttonText = "";

			//Detects which button was pressed, 
			//and calls the corresponding method 
			switch(arg0){
				case 0:{ 
					       buttonText = "A"; 
					       buttonAAction();
					       break; 
				       }		
				case 1:{ 
					       buttonText = "B"; 
					       buttonBAction();
					       break;
				       }
				case 2:{
					       buttonText = "C"; 
					       buttonCAction();
					       break;
				       }
				case 3:{ 
					       buttonText = "D";
					       buttonDAction();
					       break;
				       }
				case 4:{ 
					       buttonText = "Tetroids";
					       buttonTetroidsAction();
					       break;
				       }

			}

			System.out.println(buttonText);
			lastButtonPressed = arg0;
		}


	//Executed when Button A is pressed
	public void buttonAAction(){
		//Sets the background to a random colour, and prints the name of the colour
		int randomNumber = r.nextInt(10);
		int num = setBackgroundColour(randomNumber);
		printColorName(num);
	}


	//Executed when Button B is pressed
	private void buttonBAction() {
		//Checks if initialisation is needed (not last button pressed)
		if(lastButtonPressed != 1){
			setBackgroundColour(0);
			//Sets the snails location to the centre of the grid
			snailX = getGridWidth()/2;
			snailY = getGridHeight()/2;
		}
		else{
			//Draw the snail
			setGridColour(snailX,snailY,7);
			int randomNum = r.nextInt(4);

			switch(randomNum){
				case 0: if (snailY>0){snailY--;}else{snailY++;} break; //Up
				case 1: if (snailX<getGridWidth() -1){snailX++;}else{snailX--;} break; //Right
				case 2: if (snailY<getGridHeight() -1){snailY++;}else{snailY--;} break; //Down
				case 3: if (snailX>0){snailX--;}else{snailX++;} break; //Left
			}
		}
		//Draw the snail's trail
		setGridColour(snailX,snailY,1);
	}


	//Executed when Button C is pressed
	private void buttonCAction(){

		setBackgroundColour(0);

		//Checks if initialisation is needed (not last button pressed)
		if(lastButtonPressed != 2){
			//Choose a random colour, that is not black
			int randomNum = r.nextInt(8) + 1;

			//Creates the SquareDrawabaleObject in the centre
			square = new SquareDrawableObject(getGridWidth()/2, getGridHeight()/2, randomNum);
			draw(square);
		}
		else{
			//Move it one space in a random direction
			//Stays inside the grid
			int x = r.nextInt(getGridWidth() - (2 * square.getWidth()) ) + square.getWidth();
			int y = r.nextInt(getGridHeight()- (2 * square.getHeight()) ) + square.getHeight();
			square.moveTo(x, y);
			draw(square);
		}
	}


	//Executed when Button D is pressed
	private void buttonDAction(){

		setBackgroundColour(0);
		//Choose a random colour, that is not black
		int randomNum = r.nextInt(8) + 1;

		//Creates the SquareDrawabaleObject in the centre	
		square = new SquareDrawableObject(getGridWidth()/2, getGridHeight()/2, randomNum);
		draw(square);
	}


	//Executed when the Tetroids Button is pressed
	private void buttonTetroidsAction(){
		//Starts up the game
		setBackgroundColour(5);

		tGrid = new TetroidGrid(this);
		currentTetroid = new Tetroid(tGrid);				
	}

	//Takes in a number and prints the name of its colour to the console
	public void printColorName(int num){
		//Create an empty string
		String colour = "";

		//Finds corresponding colour name
		switch(num){
			case GraphicsController.YELLOW: colour = "Yellow"; break;
			case GraphicsController.BLUE: colour = "Blue"; break;
			case GraphicsController.GREEN: colour = "Green"; break;
			case GraphicsController.BLACK: colour = "Black"; break;
			case GraphicsController.ORANGE: colour = "Orange"; break;
			case GraphicsController.PINK: colour = "Pink"; break;
			case GraphicsController.PURPLE: colour = "Purple"; break;
			case GraphicsController.RED: colour = "Red"; break;
			case GraphicsController.WHITE: colour = "White"; break;
		}

		System.out.println(colour);

	}


	//Changes a number to the equivalent number colour
	public int numberToColourNumber(int num){

		int colour = 0;

		//Finds the correct colour number of the number
		switch(num){
			case GraphicsController.BLACK: colour = 0; break;
			case GraphicsController.WHITE: colour = 1; break;
			case GraphicsController.RED:   colour = 2; break;
			case GraphicsController.BLUE:  colour = 3; break;
			case GraphicsController.GREEN: colour = 4; break;
			case GraphicsController.ORANGE:colour = 5; break;
			case GraphicsController.PINK:  colour = 6; break;
			case GraphicsController.YELLOW:colour = 7; break;
			case GraphicsController.PURPLE:colour = 8; break;
		}

		return colour;
	}


	//Changes the colour of each cell to the specified colour
	public int setBackgroundColour(int num){

		int colour = numberToColourNumber(num);

		//Loops through all of the grid squares,
		//Colouring them in the appropriate colour
		for(int i = 0; i < getGridWidth(); i++){
			for(int j = 0; j < getGridHeight(); j++){
				setGridColour(i,j,colour);
			}
		}

		return colour;
	}


	//Called on key down
	@Override
		protected void keyPressed(Key arg0) {

			currentTetroid.move(arg0.getKeyChar());

		}


	//Called on key up
	@Override
		protected void keyReleased(Key arg0) {
			// TODO Auto-generated method stub

		}


	//Called every 0.1 seconds
	@Override
		protected void update() {
			//Checks if D was the last button to be pressed
			if (lastButtonPressed == 3){
				setBackgroundColour(0);

				//Tries to move the square in the given direction, if it can't it tries again with a new random number
				while(!square.move(r.nextInt(4), getGridWidth(), getGridHeight())){
					square.move(r.nextInt(4), getGridWidth(), getGridHeight());
				}

				draw(square);
			}

			//Checks if the Tetroids button was the last to be pressed
			if (lastButtonPressed == 4 && tGrid.getLost() == false){

				//When the right amount of time has passed, call the gravity function
				if(tGrid.getClock() % tGrid.getTDelay() == 0) currentTetroid.gravity();

				tGrid.draw();

				//If the current tetroid has been docked, make a new one
				if(currentTetroid.getDocked()){
					currentTetroid = new Tetroid(tGrid);
				}

				currentTetroid.draw();
				tGrid.incrementClock();

				//Sets the buttons text to the score.
				this.setButtonText(4, "Score: " + tGrid.getScore());

			}
		}
}

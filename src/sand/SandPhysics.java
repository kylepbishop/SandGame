package sand;

public class SandPhysics {
	private static final int SIZE = 10;
	private static int numOfMoving = 0;
	private static int[][] movingParticles = new int[SIZE][SIZE];
	
	public static void init(){
		for(int yCoord = 0; yCoord < SIZE; yCoord++){
			for(int xCoord = 0; xCoord < SIZE; xCoord++){
				movingParticles[xCoord][yCoord] = 0;
			}
		}
	}
	
	public static void print(){
		System.out.println("Visual");
		for(int yCoord = 0; yCoord < SIZE; yCoord++){
			for(int xCoord = 0; xCoord < SIZE; xCoord++){
				System.out.print(movingParticles[xCoord][yCoord]);
			}
			System.out.println();
		}
	}
	
	public static void create(int xCoord, int yCoord, int color){
		movingParticles[xCoord][yCoord] = color;
		numOfMoving++;
	}
	
	public static void physicsUpdate(){
		for(int yCoord = SIZE-1; yCoord >= 0; yCoord--){
			for(int xCoord = 0; xCoord < SIZE-1; xCoord++){
				if(movingParticles[xCoord][yCoord] > 0){// if a coordinate has color
					System.out.println(xCoord + " " + yCoord + " has color");
					if(xCoord < SIZE && xCoord > 0 && yCoord < SIZE-1){ // if x+ or - 1 or y +1 is okay do this stuff
						if(movingParticles[xCoord][yCoord+1] == 0){ //check if it can fall down, down left or down right
							System.out.println("Falling down");
							movingParticles[xCoord][yCoord+1] = movingParticles[xCoord][yCoord];
							movingParticles[xCoord][yCoord] = 0;
						} else if (movingParticles[xCoord-1][yCoord+1] == 0){
							System.out.println("Falling left");
							movingParticles[xCoord-1][yCoord+1] = movingParticles[xCoord][yCoord];
							movingParticles[xCoord][yCoord] = 0;
						} else if (movingParticles[xCoord+1][yCoord+1] == 0){
							System.out.println("Falling right");
							movingParticles[xCoord+1][yCoord+1] = movingParticles[xCoord][yCoord];
							movingParticles[xCoord][yCoord] = 0;
						}
					} else {
						numOfMoving--;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		init();
		print();
		create(1, 1, 1);
		create(2, 1, 2);
		create(1, 2, 3);
		print();
		while(numOfMoving > 0){
			physicsUpdate();
			print();
		}
	}

}

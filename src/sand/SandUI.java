package sand;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.List;
import javax.swing.*;

public class SandUI extends JPanel implements ActionListener {
	static int size = 300;
	static int delay = 300;
	static BufferedImage bi = new BufferedImage(size, size,
			BufferedImage.TYPE_INT_RGB);
	static Timer timer;
	private static int numOfMoving = 0;
	private static int[][] movingParticles = new int[size][size];
	JLabel jl;
	int frameNumber = -1;
	boolean frozen = false;

	public SandUI() {
		
		bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		ImageIcon icon = new ImageIcon(bi);
		jl = new JLabel(icon);
		add(jl);

		for (int y = 0; y < size; y += 5) {
			for (int x = 0; x < size; x++) {
				Color color = Color.RED;
				int colorValue = color.getRGB();
				bi.setRGB(x, y, colorValue);
				bi.setRGB(x, y + 1, colorValue);
				bi.setRGB(x, y + 2, colorValue);
				bi.setRGB(x, y + 3, colorValue);
				bi.setRGB(x, y + 4, colorValue);
			}
		}
		
		timer = new Timer(delay, this);
	}
	
	public synchronized static void startAnimation(){
		timer.start();
	}
	
	public synchronized void stopAnimation(){
		timer.stop();
	}
	
	public static void physicsUpdate(){
		for(int row = size-1; row >= 0; row--){
			for(int col = 0; col < size-1; col++){
				if(movingParticles[col][row] > 0){// if a coordinate has color
					//System.out.println(col + " " + row + " has color");
					if(col < size && col > 0 && row < size-1){ // if x+ or - 1 or y +1 is okay do this stuff
						if(movingParticles[col][row+1] == 0){ //check if it can fall down, down left or down right
							//System.out.println("Falling down");
							movingParticles[col][row+1] = movingParticles[col][row];
							bi.setRGB(col, row+1, Color.BLUE.getRGB());
							bi.setRGB(col, row, Color.RED.getRGB());
							movingParticles[col][row] = 0;
						} else if (movingParticles[col-1][row+1] == 0){
							//System.out.println("Falling left");
							movingParticles[col-1][row+1] = movingParticles[col][row];
							movingParticles[col][row] = 0;
							bi.setRGB(col-1, row+1, Color.BLUE.getRGB());
							bi.setRGB(col, row, Color.RED.getRGB());
						} else if (movingParticles[col+1][row+1] == 0){
							//System.out.println("Falling right");
							movingParticles[col+1][row+1] = movingParticles[col][row];
							movingParticles[col][row] = 0;
							bi.setRGB(col+1, row+1, Color.BLUE.getRGB());
							bi.setRGB(col, row, Color.RED.getRGB());
						}
					} else {
						numOfMoving--;
					}
				}
			}
		}
	}
	
	public static void init(){
		for(int row = 0; row < size; row++){
			for(int col = 0; col < size; col++){
				movingParticles[col][row] = 0;
			}
		}
	}
	
	public static void create(int col, int row, int color){
		movingParticles[col][row] = color;
		numOfMoving++;
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Sand UI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new SandUI());
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
	}

	private static void updateGUI() {
		for (int y = 0; y < size; y += 5) {
			for (int x = 0; x < size; x++) {
				Color color = (y % 2 == 0) ? Color.BLUE : Color.BLACK;
				int colorValue = color.getRGB();
				bi.setRGB(x, y, colorValue);
				bi.setRGB(x, y + 1, colorValue);
				bi.setRGB(x, y + 2, colorValue);
				bi.setRGB(x, y + 3, colorValue);
				bi.setRGB(x, y + 4, colorValue);
			}
		}
	}

	public static void main(String[] args) {
		boolean keepGoing = true;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
				startAnimation();
				create(5, 5, 1);
				create(5, 6, 1);
				create(5, 4, 1);
				create(6, 5, 1);
			}
		});
	}
	
	public JLabel getJl() {
		return jl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frameNumber++;
		physicsUpdate();
	}
}

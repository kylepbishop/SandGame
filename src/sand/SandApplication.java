package sand;
/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/* 
 * A template for animation applications.
 */
public class SandApplication extends JFrame implements ActionListener {

	int frameNumber = -1;
	Timer timer;
	boolean frozen = false;
	JLabel label;
	JLabel jl;
	int size = 300;
	int activeColor = Color.YELLOW.getRGB();
	int nothing = Color.GRAY.getRGB();
	int lava = Color.RED.getRGB();
	int plant = Color.GREEN.getRGB();
	int sand = Color.YELLOW.getRGB();
	int water = Color.BLUE.getRGB();
	int obsidian = Color.BLACK.getRGB();
	int steam = Color.WHITE.getRGB();
	int oil = Color.MAGENTA.getRGB();
	int cyan = Color.CYAN.getRGB();
	int orange = Color.ORANGE.getRGB();
	int pink = Color.PINK.getRGB();

	BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
	private int numOfMoving = 0;
	private int[][] movingParticles = new int[size][size];

	SandApplication(int fps, String windowTitle) {
		super(windowTitle);
		int delay = (fps > 0) ? (1000 / fps) : 100;

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				movingParticles[col][row] = nothing;
			}
		}
		// Set up a timer that calls this object's action handler.
		timer = new javax.swing.Timer(delay, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);

		addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				stopAnimation();
			}

			public void windowDeiconified(WindowEvent e) {
				startAnimation();
			}

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		label = new JLabel("Frame     ", JLabel.CENTER);
		label.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (frozen) {
					frozen = false;
					startAnimation();
				} else {
					frozen = true;
					stopAnimation();
				}
			}
		});
		bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		ImageIcon icon = new ImageIcon(bi);
		jl = new JLabel(icon);
		jl.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				create(e.getX(), e.getY(), activeColor);
			}
		});
		getContentPane().setLayout(new MigLayout("", "[438px]", "[300px][14px]"));
		getContentPane().add(jl);
		jl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// if (frozen) {
				// frozen = false;
				// startAnimation();
				// } else {
				// frozen = true;
				// stopAnimation();
				// }
				create(e.getX(), e.getY(), activeColor);
			}
		});
		for (int y = 0; y < size; y += 5) {
			for (int x = 0; x < size; x++) {
				bi.setRGB(x, y, nothing);
				bi.setRGB(x, y + 1, nothing);
				bi.setRGB(x, y + 2, nothing);
				bi.setRGB(x, y + 3, nothing);
				bi.setRGB(x, y + 4, nothing);
			}
		}

		getContentPane().add(jl);
		getContentPane().add(label, "cell 0 1,growx,aligny top");

		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[]", "[][][][][][][][][][][][][][][][]"));

		JButton btnYellow = new JButton("Sand");
		btnYellow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = sand;
			}
		});
		panel.add(btnYellow, "flowy,cell 0 0,growx");

		JButton btnBlue = new JButton("Water");
		btnBlue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = water;
			}
		});

		JButton btnRed = new JButton("Lava");
		btnRed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = lava;
			}
		});
		panel.add(btnRed, "cell 0 1,growx");
		panel.add(btnBlue, "cell 0 2,growx");

		JButton btnGreen = new JButton("Plant");
		btnGreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = plant;
			}
		});
		panel.add(btnGreen, "cell 0 3,growx");

		JButton btnSteam = new JButton("Steam");
		btnSteam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = steam;
			}
		});
		panel.add(btnSteam, "cell 0 4,growx");

		JButton btnObsidian = new JButton("Obsidian");
		btnObsidian.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = obsidian;
			}
		});
		panel.add(btnObsidian, "cell 0 5,growx");

		JButton btnMagenta = new JButton("Oil");
		btnMagenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = oil;
			}
		});
		panel.add(btnMagenta, "cell 0 6,growx");

		JButton btnPink = new JButton("Pink");
		btnPink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = pink;
			}
		});
		panel.add(btnPink, "flowy,cell 0 7,growx");

		JButton btnCyan = new JButton("Cyan");
		btnCyan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = cyan;
			}
		});
		panel.add(btnCyan, "cell 0 8,growx");

		JButton btnOrange = new JButton("Orange");
		btnOrange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = orange;
			}
		});
		panel.add(btnOrange, "cell 0 9,growx");

		JButton btnNothing = new JButton("Nothing");
		btnNothing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				activeColor = nothing;
			}
		});
		panel.add(btnNothing, "cell 0 10,growx");
	}

	public void create(int col, int row, int color) {
		if (col > 0 && col < size && row > 0 && row < size) {
			movingParticles[col][row] = color;
			// numOfMoving++;
		}
	}

	// Can be invoked by any thread (since timer is thread-safe).
	public void startAnimation() {
		if (frozen) {
			// Do nothing. The user has requested that we
			// stop changing the image.
		} else {
			// Start animating!
			if (!timer.isRunning()) {
				timer.start();
			}
		}
	}

	// Can be invoked by any thread (since timer is thread-safe).
	public void stopAnimation() {
		// Stop the animating thread.
		if (timer.isRunning()) {
			timer.stop();
		}
	}

	public void actionPerformed(ActionEvent e) {
		// Advance the animation frame.
		frameNumber++;
		label.setText("Frame " + frameNumber);
		physicsUpdate();
		// altPhysicsUpdate();
		jl.repaint();
	}

	public int densityOf(int color) {
		int result = 0;
		if (color == sand) {
			result = 100;
		} else if (color == plant) {
			result = 100;
		} else if (color == water) {
			result = 50;
		} else if (color == lava) {
			result = 75;
		} else if (color == nothing) {
			result = 0;
		} else if (color == obsidian) {
			result = 100;
		} else if (color == steam) {
			result = 25;
		} else if (color == cyan) {
			result = 100;
		} else if (color == oil) {
			result = 25;
		} else if (color == orange) {
			result = 100;
		} else if (color == pink) {
			result = 100;
		}
		return result;
	}

	public boolean isFlamable(int color) {
		boolean result = false;
		if (color == sand) {
			result = false;
		} else if (color == plant) {
			result = true;
		} else if (color == water) {
			result = false;
		} else if (color == lava) {
			result = false;
		} else if (color == nothing) {
			result = false;
		} else if (color == obsidian) {
			result = false;
		} else if (color == steam) {
			result = false;
		} else if (color == cyan) {
			result = false;
		} else if (color == oil) {
			result = true;
		} else if (color == orange) {
			result = false;
		} else if (color == pink) {
			result = false;
		}
		return result;
	}

	public void physicsUpdate() {
		for (int row = 0; row < size - 1; row++) {
			for (int col = 0; col < size - 1; col++) {
				if (col < size && col > 0 && row < size - 1 && row > 0) {
					if (movingParticles[col][row] == steam) {
						if (movingParticles[col + 1][row] == steam && movingParticles[col - 1][row] == steam
								&& movingParticles[col][row + 1] == steam && movingParticles[col][row - 1] == steam
								&& movingParticles[col-1][row-1] == steam && movingParticles[col-1][row+1] == steam
								&& movingParticles[col+1][row-1] == steam && movingParticles[col+1][row+1] == steam) {
							movingParticles[col][row] = water;
						} else if (movingParticles[col-1][row-1] == water && movingParticles[col+1][row+1] == steam){
							movingParticles[col+1][row+1] = water;
						} else if (movingParticles[col+1][row-1] == water && movingParticles[col-1][row+1] == steam){
							movingParticles[col-1][row-1] = water;
						}
						if (densityOf(movingParticles[col][row - 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row - 1];
							movingParticles[col][row - 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row - 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row - 1];
							movingParticles[col - 1][row - 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row - 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row - 1];
							movingParticles[col + 1][row - 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else {
							if (col < size - 2 && col > 2) {
								if (densityOf(movingParticles[col + 2][row - 1]) < densityOf(
										movingParticles[col][row])) {
									int temp = movingParticles[col + 2][row - 1];
									movingParticles[col + 2][row - 1] = movingParticles[col][row];
									movingParticles[col][row] = temp;
								} else if (densityOf(movingParticles[col - 2][row - 1]) < densityOf(
										movingParticles[col][row])) {
									int temp = movingParticles[col - 2][row - 1];
									movingParticles[col - 2][row - 1] = movingParticles[col][row];
									movingParticles[col][row] = temp;
								}
							}
						}
						// else {
						// if (row % 2 == 0) {
						// if (movingParticles[col + 1][row] == nothing) {
						// // System.out.println("Falling right");
						// movingParticles[col + 1][row] =
						// movingParticles[col][row];
						// movingParticles[col][row] = nothing;
						// } else if (movingParticles[col - 1][row] == nothing)
						// {
						// // System.out.println("Falling right");
						// movingParticles[col - 1][row] =
						// movingParticles[col][row];
						// movingParticles[col][row] = nothing;
						// }
						// } else if (row % 2 != 0) {
						// if (movingParticles[col - 1][row] == nothing) {
						// // System.out.println("Falling right");
						// movingParticles[col - 1][row] =
						// movingParticles[col][row];
						// movingParticles[col][row] = nothing;
						//
						// } else if (movingParticles[col + 1][row] == nothing)
						// {
						// // System.out.println("Falling right");
						// movingParticles[col + 1][row] =
						// movingParticles[col][row];
						// movingParticles[col][row] = nothing;
						// }
						// }
						// }
					}
				}
			}
		}
		for (int row = size - 1; row >= 0; row--) {
			for (int col = 0; col < size - 1; col++) {
				bi.setRGB(col, row, movingParticles[col][row]);
				// if a coordinate has a color
				if (col < size && col > 0 && row < size - 1) {
					if (movingParticles[col][row] == sand) {
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						}
					} else if (movingParticles[col][row] == plant) {
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						}
						// else if (densityOf(movingParticles[col - 1][row + 1])
						// < densityOf(
						// movingParticles[col][row])) {
						// int temp = movingParticles[col - 1][row + 1];
						// movingParticles[col - 1][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// } else if (densityOf(movingParticles[col + 1][row +
						// 1]) < densityOf(
						// movingParticles[col][row])) {
						// int temp = movingParticles[col + 1][row + 1];
						// movingParticles[col + 1][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// }
					} else if (movingParticles[col][row] == water) {
						if (movingParticles[col][row + 1] == lava) {
							movingParticles[col][row + 1] = obsidian;
							movingParticles[col][row] = steam;
						} else if (movingParticles[col - 1][row] == lava) {
							movingParticles[col - 1][row] = obsidian;
							movingParticles[col][row] = steam;
						} else if (movingParticles[col + 1][row] == lava) {
							movingParticles[col + 1][row] = obsidian;
							movingParticles[col][row] = steam;
						}
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else {
							if (row % 2 == 0) {
								if (movingParticles[col + 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col + 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								} else if (movingParticles[col - 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col - 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								}
							} else if (row % 2 != 0) {
								if (movingParticles[col - 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col - 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;

								} else if (movingParticles[col + 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col + 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								}
							}
						}
					} else if (movingParticles[col][row] == lava) {
						if (isFlamable(movingParticles[col][row + 1])) {
							movingParticles[col][row + 1] = lava;
							movingParticles[col - 1][row + 1] = lava;
							movingParticles[col][row] = nothing;
						} else if (isFlamable(movingParticles[col - 1][row])) {
							movingParticles[col - 1][row] = lava;
						} else if (isFlamable(movingParticles[col + 1][row])) {
							movingParticles[col + 1][row] = lava;
						} else if (movingParticles[col][row + 1] == water) {
							movingParticles[col][row + 1] = steam;
							movingParticles[col][row] = obsidian;
						} else if (movingParticles[col - 1][row] == water) {
							movingParticles[col - 1][row] = steam;
							movingParticles[col][row] = obsidian;
						} else if (movingParticles[col + 1][row] == water) {
							movingParticles[col + 1][row] = steam;
							movingParticles[col][row] = obsidian;
						}
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else {
							if (row % 2 == 0) {
								if (movingParticles[col + 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col + 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								} else if (movingParticles[col - 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col - 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								}
							} else if (row % 2 != 0) {
								if (movingParticles[col - 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col - 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;

								} else if (movingParticles[col + 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col + 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								}
							}
						}
					} else if (movingParticles[col][row] == obsidian) {
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						}
					} else if (movingParticles[col][row] == steam) {
						// if (densityOf(movingParticles[col][row + 1]) <
						// densityOf(movingParticles[col][row])) {
						// int temp = movingParticles[col][row + 1];
						// movingParticles[col][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// } else if (densityOf(movingParticles[col - 1][row +
						// 1]) < densityOf(
						// movingParticles[col][row])) {
						// int temp = movingParticles[col - 1][row + 1];
						// movingParticles[col - 1][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// } else if (densityOf(movingParticles[col + 1][row +
						// 1]) < densityOf(
						// movingParticles[col][row])) {
						// int temp = movingParticles[col + 1][row + 1];
						// movingParticles[col + 1][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// }
					} else if (movingParticles[col][row] == cyan) {
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						}
					} else if (movingParticles[col][row] == oil) {
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else {
							if (row % 2 == 0) {
								if (movingParticles[col + 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col + 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								} else if (movingParticles[col - 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col - 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								}
							} else if (row % 2 != 0) {
								if (movingParticles[col - 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col - 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;

								} else if (movingParticles[col + 1][row] == nothing) {
									// System.out.println("Falling right");
									movingParticles[col + 1][row] = movingParticles[col][row];
									movingParticles[col][row] = nothing;
								}
							}
						}
					} else if (movingParticles[col][row] == orange) {
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						}
					} else if (movingParticles[col][row] == pink) {
						// if (densityOf(movingParticles[col][row + 1]) <
						// densityOf(movingParticles[col][row])) {
						// int temp = movingParticles[col][row + 1];
						// movingParticles[col][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// } else if (densityOf(movingParticles[col - 1][row +
						// 1]) < densityOf(
						// movingParticles[col][row])) {
						// int temp = movingParticles[col - 1][row + 1];
						// movingParticles[col - 1][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// } else if (densityOf(movingParticles[col + 1][row +
						// 1]) < densityOf(
						// movingParticles[col][row])) {
						// int temp = movingParticles[col + 1][row + 1];
						// movingParticles[col + 1][row + 1] =
						// movingParticles[col][row];
						// movingParticles[col][row] = temp;
						// }
					}
				}
			}
		}
	}

	public void altPhysicsUpdate() {
		for (int row = size - 1; row >= 0; row--) {
			for (int col = 0; col < size - 1; col++) {
				bi.setRGB(col, row, movingParticles[col][row]);
				// if a coordinate has a color
				if (col < size && col > 0 && row < size - 1) {
					if (movingParticles[col][row] != nothing && movingParticles[col][row] != steam) {
						// System.out.println(col + " " + row + " has color");
						// if x+ or -1 or y +1 is okay do this stuff
						// check if it can fall down, down left or down right
						if (densityOf(movingParticles[col][row + 1]) < densityOf(movingParticles[col][row])) {
							int temp = movingParticles[col][row + 1];
							movingParticles[col][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col - 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col - 1][row + 1];
							movingParticles[col - 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else if (densityOf(movingParticles[col + 1][row + 1]) < densityOf(
								movingParticles[col][row])) {
							int temp = movingParticles[col + 1][row + 1];
							movingParticles[col + 1][row + 1] = movingParticles[col][row];
							movingParticles[col][row] = temp;
						} else {
							if (movingParticles[col][row] == water || movingParticles[col][row] == lava) {
								if (row % 2 == 0) {
									if (movingParticles[col + 1][row] == nothing) {
										// System.out.println("Falling right");
										movingParticles[col + 1][row] = movingParticles[col][row];
										movingParticles[col][row] = nothing;
									} else if (movingParticles[col - 1][row] == nothing) {
										// System.out.println("Falling right");
										movingParticles[col - 1][row] = movingParticles[col][row];
										movingParticles[col][row] = nothing;
									}
								} else if (row % 2 != 0) {
									if (movingParticles[col - 1][row] == nothing) {
										// System.out.println("Falling right");
										movingParticles[col - 1][row] = movingParticles[col][row];
										movingParticles[col][row] = nothing;

									} else if (movingParticles[col + 1][row] == nothing) {
										// System.out.println("Falling right");
										movingParticles[col + 1][row] = movingParticles[col][row];
										movingParticles[col][row] = nothing;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static void main(String args[]) {
		SandApplication animator = null;
		int fps = 600;

		// Get frames per second from the command line argument.
		if (args.length > 0) {
			try {
				fps = Integer.parseInt(args[0]);
			} catch (Exception e) {
			}
		}
		animator = new SandApplication(fps, "Sand Game");
		animator.pack();
		animator.setVisible(true);

		// It's OK to start the animation here because
		// startAnimation can be invoked by any thread.
		animator.startAnimation();
	}
}
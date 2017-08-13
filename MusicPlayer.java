package songs;

/*
 * Music Player
 *
 * This instructor-provided file implements the graphical user interface (GUI)
 * for the Music Player program and allows you to test the behavior
 * of your Song class.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


public class MusicPlayer implements ActionListener, StdAudio.AudioEventListener {

    // instance variables
    private Song song;
    private boolean playing; // whether a song is currently playing
    private JFrame frame;
    private JFileChooser fileChooser;
    private JTextField tempoText;
    private JSlider currentTimeSlider;
    
    private JButton play;
    private JButton stop;
    private JButton pause;
    private JButton load;
    private JButton reverse;
    private JButton tempo;
    
    private JButton octaveUp;
   // private JLabel octaveLabel;
    private JButton octaveDown;
    
    private JSpinner spinner;
    
    //these are the two labels that indicate time
    // to the right of the slider
    private JLabel currentTimeLabel;
    private JLabel totalTimeLabel;
    
    //this the label that says 'welcome to the music player'
    private JLabel statusLabel; 
    
    private double tempoValue;
    private JLabel logoLabel;
   
    /*
     * Creates the music player GUI window and graphical components.
     */
    public MusicPlayer() {
        song = null;
        createComponents();
        doLayout();
        StdAudio.addAudioEventListener(this);
        frame.setVisible(true);
        
        
    }

    /*
     * Called when the user interacts with graphical components, such as
     * clicking on a button.
     */
    public void actionPerformed(ActionEvent event) {
        String cmd = event.getActionCommand();
        if (cmd.equals("Play")) {
           this.playSong();
        } else if (cmd.equals("Pause")) {
            StdAudio.setPaused(!StdAudio.isPaused());
            pause.setText("UnPause");
            play.setEnabled(false);
            stop.setEnabled(false);
        } else if (cmd.equals("UnPause")) {
            StdAudio.setPaused(!StdAudio.isPaused());
            pause.setText("Pause");
            play.setEnabled(true);
            stop.setEnabled(true);
        } else if (cmd == "Stop") {
            StdAudio.setMute(true);
            StdAudio.setPaused(false);
        } else if (cmd == "Load") {
            try {
                loadFile();
            } catch (IOException ioe) {
            	System.out.println("not able to load from the file");
            }
        } else if (cmd == "Reverse") {
        	song.reverse();
        } else if (cmd == "Up") {
        	song.octaveUp();
        } else if (cmd == "Down") {
        	song.octaveDown();
        } else if (cmd == "Change Tempo") {
        	tempoValue = (Double) spinner.getValue();
        	song.changeTempo(tempoValue);
        }
    }

    /*
     * Called when audio events occur in the StdAudio library. We use this to
     * set the displayed current time in the slider.
     */
    public void onAudioEvent(StdAudio.AudioEvent event) {
        // update current time
        if (event.getType() == StdAudio.AudioEvent.Type.PLAY
                || event.getType() == StdAudio.AudioEvent.Type.STOP) {
            setCurrentTime(getCurrentTime() + event.getDuration());
        }
    }

    /*
     * Sets up the graphical components in the window and event listeners.
     */
    private void createComponents() {
        //TODO - create all your components here.
        // note that you should have already defined your components as instance variables.
    	//Following buttons and assigned listeners
    	play = new JButton("Play");
    	//passes the object in, in this case the music player because it is created as an implementation of an action Listener
    	play.addActionListener(this);
    	play.setEnabled(false);
    	pause = new JButton("Pause");
    	pause.addActionListener(this);
    	stop = new JButton("Stop");
    	stop.addActionListener(this);
    	load = new JButton("Load");
    	load.addActionListener(this);
    	reverse = new JButton("Reverse");
    	reverse.addActionListener(this);
    	tempo = new JButton("Change Tempo");
    	tempo.addActionListener(this);
    	
    	octaveUp = new JButton("Up");
    	octaveUp.addActionListener(this);
    	octaveDown = new JButton("Down");
    	octaveDown.addActionListener(this);
    	//octaveLabel = new JLabel("Octave");
    	
    	totalTimeLabel = new JLabel();
    	
    	currentTimeSlider = new JSlider();
    	currentTimeSlider.setMajorTickSpacing(30);
    	currentTimeSlider.setMinorTickSpacing(5);
    	currentTimeSlider.setSnapToTicks(true);
    	currentTimeSlider.setSize(300, 50);
    	currentTimeSlider.setPaintTicks(true);
    	
    	
    	currentTimeLabel = new JLabel();
    	
    	tempoText = new JTextField();
    	
        fileChooser = new JFileChooser();
        statusLabel = new JLabel("Welcome to the Music Player");
       
        
        SpinnerModel spinnerLimits = new SpinnerNumberModel(1.0,0.25,5,0.25);
        spinner = new JSpinner(spinnerLimits);
        
        logoLabel= new JLabel("PKKM");
       
        
        
        doEnabling();
    }

    /*
     * Sets whether every button, slider, spinner, etc. should be currently
     * enabled, based on the current state of whether a song has been loaded and
     * whether or not it is currently playing. This is done to prevent the user
     * from doing actions at inappropriate times such as clicking play while the
     * song is already playing, etc.
     */
    private void doEnabling() {
       //TODO - figure out which buttons need to enabled
    	if(!playing){
  
    		load.setEnabled(true);
    		stop.setEnabled(false);
    		pause.setEnabled(false);
    		currentTimeSlider.setEnabled(false);
    		tempoText.setEnabled(false);
    		reverse.setEnabled(false);
    		octaveUp.setEnabled(false);
    		octaveDown.setEnabled(false);
    		tempo.setEnabled(false);
    		
    	}
    	else{
    		load.setEnabled(false);
    		play.setEnabled(false);;
    		stop.setEnabled(true);
    		pause.setEnabled(true);
    		currentTimeSlider.setEnabled(true);
    		tempoText.setEnabled(true);
    		reverse.setEnabled(true);
    		octaveUp.setEnabled(true);
    		octaveDown.setEnabled(true);
    		tempo.setEnabled(true);
    	}
    	
    	
    }

    /*
     * Performs layout of the components within the graphical window. 
     * Also make the window a certain size and put it in the center of the screen.
     */
    private void doLayout() {
        //TODO - figure out how to layout the components
    	frame = new JFrame("Music Player");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLayout(new GridLayout(5,1));
    	JPanel panel1 = new JPanel(); 
		panel1.setLayout(new GridLayout(1,4));
    	
    	panel1.add(play);
    	panel1.add(pause);
    	panel1.add(stop);
    	panel1.setBorder(new EmptyBorder(5, 155, 5, 155));
    	
    	
    	
    	JPanel panel2 = new JPanel(); 
    	panel2.setLayout(new FlowLayout());
    	panel2.add(currentTimeSlider);
    	panel2.add(currentTimeLabel);
    	panel2.add(totalTimeLabel);
    	panel2.setBorder(new EmptyBorder(10, 10, 10, 30));
    	
    	JPanel panel3 = new JPanel(); 
    	panel3.setLayout(new FlowLayout());
    	//panel3.add(octaveLabel);
    	panel3.add(load);
    	panel3.add(octaveUp);
    	panel3.add(octaveDown);
    	panel3.add(reverse);
    	panel3.setBorder(new EmptyBorder(10, 10, 10, 25));
    	
    	JPanel panel4 = new JPanel();
    	panel3.setLayout(new FlowLayout());
    	panel4.add(spinner);
    	panel4.add(tempo);
    	panel4.setBorder(new EmptyBorder(10, 10, 10, 10));
    	
    	JPanel panel5 = new JPanel();
    	panel5.setLayout(new BorderLayout());
    	logoLabel.setFont(new Font("Serif", Font.BOLD, 25));
    	panel5.add(logoLabel, BorderLayout.WEST);
    	statusLabel.setHorizontalAlignment(JLabel.CENTER);
    	statusLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    	panel5.add(statusLabel, BorderLayout.SOUTH);
    	panel5.setBorder(new EmptyBorder(0, 10, 0, 10));
    	
    	
    	
    	frame.add(panel5);
    	frame.add(panel2);
    	frame.add(panel1);
    	frame.add(panel3);
    	frame.add(panel4);
    	frame.setMinimumSize(new Dimension(600,320));
  
    	
   
    	
    	
    }

    /*
     * Returns the estimated current time within the overall song, in seconds.
     */
    private double getCurrentTime() {
        String timeStr = currentTimeLabel.getText();
        timeStr = timeStr.replace(" /", "");
        try {
            return Double.parseDouble(timeStr);
        } catch (NumberFormatException nfe) {
            return 0.0;
        }
    }

    /*
     * Pops up a file-choosing window for the user to select a song file to be
     * loaded. If the user chooses a file, a Song object is created and used
     * to represent that song.
     */
    private void loadFile() throws IOException {
    	
        if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selected = fileChooser.getSelectedFile();
        if (selected == null) {
            return;
        }
        statusLabel.setText("Current song: " + selected.getName());
        String filename = selected.getAbsolutePath();
        System.out.println("Loading song from " + selected.getName() + " ...");
        
        song = new Song(filename);
        
        tempoText.setText("1.0");
        setCurrentTime(0.0);
        updateTotalTime();
        System.out.println("Loading complete.");
        System.out.println("Song: " + song);
        updateTotalTime();
        play.setEnabled(true);
        doEnabling();
    }

    /*
     * Initiates the playing of the current song in a separate thread (so
     * that it does not lock up the GUI). 
     * You do not need to change this method.
     * It will not compile until you make your Song class.
     */
    private void playSong() {
        if (song != null) {
            setCurrentTime(0.0);
            Thread playThread = new Thread(new Runnable() {
                public void run() {
                    StdAudio.setMute(false);
                    playing = true;
                    doEnabling();
                    String title = song.getTitle();
                    String artist = song.getArtist();
                    double duration = song.getTotalDuration();
                    System.out.println("Playing \"" + title + "\", by "
                            + artist + " (" + duration + " sec)");
                    song.play();
                    System.out.println("Playing complete.");
                    playing = false;
                    doEnabling();
                    
                }
            });
            playThread.start();
        }
    }

    /*
     * Sets the current time display slider/label to show the given time in
     * seconds. Bounded to the song's total duration as reported by the song.
     */
    private void setCurrentTime(double time) {
        double total = song.getTotalDuration();
        time = Math.max(0, Math.min(total, time));
        currentTimeLabel.setText(String.format("%08.2f /", time));
        currentTimeSlider.setValue((int) (100 * time / total));
    }

    /*
     * Updates the total time label on the screen to the current total duration.
     */
    private void updateTotalTime() {
    	double total = song.getTotalDuration();
    	double roundTotal = Math.round(total * 1000.0) / 1000.0;
    	totalTimeLabel.setText(String.valueOf(roundTotal)+ " seconds");
    }
}

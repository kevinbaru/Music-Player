package songs;

import java.io.BufferedReader;

//TODO: write this class

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;

/**
 * @author kevinmuriuki
 *Song class has title,artist and an array  collection of notes fields
 */
public class Song {
	String title;
	String artist;
	Note[] collection;




	/**
	 * reads notes from the filename
	 * @param filename-contains the song file
	 */
	public Song(String filename){
		//Note[] collection = new Note[numOfNotes];
		File file = new File(filename);
		//ArrayList<Book> collection = new ArrayList<Book>();
		int i=0;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			while (true) {		
				String line = reader.readLine();
				if (line == null||line.equals("")){ //break if the line is empty or null
					
					break;
					}
				line = line.trim();
				if(i==0){
					this.title=line;// stores the title
					
				}
				else if(i==1){
					this.artist=line;//stores the name of the artist
					
					
				}
				else if(i==2){
					int numOfNotes=Integer.parseInt(line);//number of notes in the song
					
					collection = new Note[numOfNotes];
					

				}
				
				//if (line.equals("")) continue; // ignore possible blank lines
				else{
					
					//	creates the notes from the files		
					String[] noteInfo = line.split(" ");//breaks at every line space
					if(noteInfo.length>3){
						double duration= Double.parseDouble(noteInfo[0]);
						Pitch pitch=Pitch.valueOf(noteInfo[1]);
						int octave=Integer.parseInt(noteInfo[2]);				
						Accidental acc = Accidental.valueOf(noteInfo[3]);
						boolean repeat=Boolean.parseBoolean(noteInfo[4]);

						if(noteInfo[1]=="R"){
							collection[i-3]=new Note(duration,repeat);//if note is at rest
						}
						collection[i-3]=(new Note(duration,pitch,octave,acc,repeat));
					}
					else{
						double duration= Double.parseDouble(noteInfo[0]);
						boolean repeat=Boolean.parseBoolean(noteInfo[2]);
						collection[i-3]=new Note(duration,repeat);
					}



				}
				i++;
			
				
			}
			reader.close();	
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
}
	/**
	 * @return the title of the song
	 */
	public String getTitle(){
		return title;
		
	}
	
	/**
	 * @returns the artist
	 */
	public String getArtist(){
		return artist;
	}
	/**
	 * @returns the total duration of the song in seconds
	 * It's the sum of the durations of the song notes
	 */
	public double getTotalDuration(){
		double totalDuration=0;//initializes the total duration
		int firstTrue=0;//initializes the first time there is repeat
		int secondTrue;//variable for the second time repeat happened
		boolean time= true;//switched between first and secondTrue

		for(int i=0;i<collection.length;i++){//for the total length of the notes
			totalDuration+=collection[i].getDuration();//sums the duration
			if(collection[i].isRepeat()){//if repeat occurs
				if(time){//if first time
					firstTrue=i;//store the index
					time=false;
					
				}
				else{
				if(!time){//if second repeat
					secondTrue=i;
					for(int j=firstTrue;j<=secondTrue;j++){//sum the duration of the repeated section
						totalDuration+=collection[j].getDuration();
					}
					time=true;
				}
				}
			}
			
		}
		return totalDuration;
		
	}
	/**
	 * play  the song so that it can be heard on the computer's speakers
	 * call play on each note in the song
	 * A repeated section is played twice
	 */
	public void play(){
		int firstTrue=0;//initializes the first time there is repeat
		int secondTrue;//variable for the second time repeat happened
		boolean time= true;//switched between first and secondTrue

		for(int i=0;i<collection.length;i++){//for the total length of the notes
			collection[i].play();//plays each note
			if(collection[i].isRepeat()){//if repeat occurs
				if(time){//if first time
					firstTrue=i;//store the index
					time=false;
					
				}
				else{
				if(!time){//if second repeat
					secondTrue=i;
					for(int j=firstTrue;j<=secondTrue;j++){//replays the repeated section
					  collection[j].play();
					}
					time=true;
				}
				}
			}
			
		}
		
	}
	/**
	 * increases the pitch one level above the current state
	 * States at rest are not affected
	 * In special case the octave is at min (1)
	 * returns true in all cases other than the special case
	 */
	public boolean octaveDown(){
		for(int i=0;i<collection.length;i++){
			if(collection[i].getOctave()==1){
				return false;}//returns false if special case
				else{
					if(collection[i].getPitch().equals(Pitch.R)){//does nothing if it's at rest
						
					}
					else{
						int octve=collection[i].getOctave();
						collection[i].setOctave(octve-1);//Reduces the octave			
						
					}
				
				
			}
					
		}
		return true;
	}
	/**
	 * increases the pitch one level above the current state
	 * States at rest are not affected
	 * In special case the octave is at max (10)
	 * returns true in all cases other than the special case
	 */
	public boolean octaveUp(){
		for(int i=0;i<collection.length;i++){
			if(collection[i].getOctave()==10){
				return false;}//returns false if special case
				else{
					if(collection[i].getPitch().equals(Pitch.R)){//does nothing if it's at rest
						
					}
					else{
						int octve=collection[i].getOctave();
						collection[i].setOctave(octve+1);//Increments the octave by 1			
						
					}
				
				
			}
					
		}
		return true;
	}
	/**
	 * changes the the duration of the note by the ration given
	 * @param ratio-used to multiply the duration of the note
	 */
	/**
	 * @param ratio
	 */
	public void changeTempo(double ratio){
		for(int i=0;i<collection.length;i++){
			double period=collection[i].getDuration();
			collection[i].setDuration(period*ratio);//multiplies the duration by the ratio
		}		
	}
	/**
	 * reverses the order of notes in the song
	 */
	public void reverse(){
		for(int i=0;i<collection.length/2;i++){
			Note p=collection[i];
			Note k=collection[collection.length-1-i];
			collection[collection.length-1-i]=p;//reverses the location of the value
			collection[i]=k;
		}
	}
	/* 
	 * @see java.lang.Object#toString()
	 * returns the string to the collection of notes in the song
	 */
	public String toString(){
		
		String endLine = System.getProperty("line.separator");
	
		String songInfo="";//Initializes the string
		
		for(int i=0;i<collection.length;i++){
			
			 songInfo+= collection[i].toString()+endLine;//adds the note
			 
		}
		return songInfo;
	}
}

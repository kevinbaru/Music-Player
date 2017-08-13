package songs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SongTest {
	Song newSong;
	Song newSong2;
	Song newSong3;
	
	
	@Before
	public void setUp() throws Exception {
		newSong= new Song("books.txt");
		newSong2= new Song("books2.txt");
		newSong3 = new Song("books3.txt");
		
	}

	@Test
	public void testSong() {
		assertEquals("TTLS", newSong.getTitle());
		assertEquals("Jane Taylor",newSong.getArtist());
		assertEquals("test", newSong2.getTitle());
		assertEquals("Phil",newSong2.getArtist());
		assertNotEquals("TS", newSong.getTitle());
		assertNotEquals("J Taylor",newSong.getArtist());
		assertNotEquals("t", newSong2.getTitle());
		assertNotEquals("P",newSong2.getArtist());
	}

	@Test
	public void testGetTitle() {
		assertEquals("TTLS", newSong.getTitle());
		assertEquals("test", newSong2.getTitle());
	}

	@Test
	public void testGetArtist() {
		assertEquals("Jane Taylor",newSong.getArtist());
		assertEquals("Phil",newSong2.getArtist());
	}

	@Test
	public void testGetTotalDuration() {
		
		assertEquals(9.0, newSong.getTotalDuration(),0.1);
		assertEquals(9, newSong2.getTotalDuration(),0.1);
		assertNotEquals(8.0, newSong.getTotalDuration(),0.1);
		assertNotEquals(7, newSong2.getTotalDuration(),0.1);
	}


	@Test
	public void testOctaveDown() {
		int a = newSong.collection[0].getOctave();
		int b = newSong.collection[1].getOctave();
		int c = newSong.collection[2].getOctave();
		newSong.octaveDown();
		//check to make sure that nothing happens with a rest note
		assertEquals(a, newSong.collection[0].getOctave());
		//check to make sure octave is actually decreased
		assertEquals(b-1, newSong.collection[1].getOctave());
		//makes sure doesn't go below 1
		assertNotEquals(c-1, newSong.collection[2].getOctave());
		assertNotEquals(a-1, newSong.collection[0].getOctave());
		assertNotEquals(b, newSong.collection[1].getOctave());
	}

	@Test
	public void testOctaveUp() {
		int a = newSong.collection[0].getOctave();
		int b = newSong.collection[1].getOctave();
		int c = newSong.collection[2].getOctave();
		newSong.octaveUp();
		assertEquals(a, newSong.collection[0].getOctave());
		assertEquals(b+1, newSong.collection[1].getOctave());
		assertEquals(c+1, newSong.collection[2].getOctave());
		assertNotEquals(a+1, newSong.collection[0].getOctave());
		assertNotEquals(b, newSong.collection[1].getOctave());
	}

	@Test
	public void testChangeTempo() {
		double a= newSong.getTotalDuration();
		double b = newSong2.getTotalDuration();
		newSong.changeTempo(.5);
		newSong2.changeTempo(0.5);
		assertEquals(a*.5, newSong.getTotalDuration(),0.001);
		assertEquals(b*.5, newSong2.getTotalDuration(),0.001);
		newSong.changeTempo(0.25);
		newSong2.changeTempo(0.25);
		assertEquals((a*0.5)*.25, newSong.getTotalDuration(),0.001);
		assertEquals((b*0.5)*.25, newSong2.getTotalDuration(),0.001);
		newSong.changeTempo(1.0);
		newSong2.changeTempo(1.0);
		assertEquals(a*0.25*.5, newSong.getTotalDuration(),0.001);
		assertEquals(b*0.25*.5, newSong2.getTotalDuration(),0.001);
		newSong.changeTempo(1.5);
		newSong2.changeTempo(1.5);
		assertEquals(a*0.25*.5*1.5, newSong.getTotalDuration(),0.001);
		assertEquals(b*0.25*.5*1.5, newSong2.getTotalDuration(),0.001);
		newSong.changeTempo(2);
		newSong2.changeTempo(2);
		assertEquals(a*0.25*.5*1.5*2, newSong.getTotalDuration(),0.001);
		assertEquals(b*0.25*.5*1.5*2, newSong2.getTotalDuration(),0.001);
	}

	@Test
	public void testReverse() {
		String a = newSong3.collection[0].toString();
		String b = newSong3.collection[1].toString();
		String c = newSong3.collection[2].toString();
		newSong3.reverse();
		assertEquals(c, newSong3.collection[0].toString());
		assertEquals(b, newSong3.collection[1].toString());
		assertEquals(a, newSong3.collection[2].toString());
		String d = newSong.collection[0].toString();
		String e = newSong.collection[1].toString();
		String f = newSong.collection[2].toString();
		String g = newSong.collection[3].toString();
		String h = newSong.collection[4].toString();
		String i = newSong.collection[5].toString();
		String j = newSong.collection[6].toString();
		String k = newSong.collection[7].toString();
		newSong.reverse();
		assertEquals(d, newSong.collection[7].toString());
		assertEquals(e, newSong.collection[6].toString());
		assertEquals(f, newSong.collection[5].toString());
		assertEquals(g, newSong.collection[4].toString());
		assertEquals(h, newSong.collection[3].toString());
		assertEquals(i, newSong.collection[2].toString());
		assertEquals(j, newSong.collection[1].toString());
		assertEquals(k, newSong.collection[0].toString());
	}

}

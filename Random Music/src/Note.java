
public class Note {
	private double length;
	private double frequency;
	private byte[] sin;
	public Note (double length, double frequency) {
		this.length = length;
		this.frequency = frequency;
		sin = new byte[(int) (16384 * ((60 / crap.tempo) * getLength()))];
		 for (int i = 0; i < sin.length; i++) {
             double period = (double) 16384 / frequency;
             double angle = 2.0 * Math.PI * i / period;
             sin[i] = (byte)(Math.sin(angle) * 127f);
         }
	}
	double getLength() {
		return length;
	}
	
	double getFrequency() {
		return frequency;
	}
	 public byte[] data() {
	        return this.sin;
	    }
}

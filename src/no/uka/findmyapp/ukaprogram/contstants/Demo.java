package no.uka.findmyapp.ukaprogram.contstants;

public enum Demo {
	TEST(12), TEST2(1);
	
	private int i;
	private Demo(int i) {
		this.i = i;
	}
	
	public int getIntValue() {
		return i;
	}
}

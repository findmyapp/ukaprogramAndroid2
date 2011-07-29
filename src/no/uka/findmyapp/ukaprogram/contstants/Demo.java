/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.contstants;

// TODO: Auto-generated Javadoc
/**
 * The Enum Demo.
 */
public enum Demo {
	
	/** The TEST. */
	TEST(12), 
 
 /** The TES t2. */
 TEST2(1);
	
	/** The i. */
	private int i;
	
	/**
	 * Instantiates a new demo.
	 *
	 * @param i the i
	 */
	private Demo(int i) {
		this.i = i;
	}
	
	/**
	 * Gets the int value.
	 *
	 * @return the int value
	 */
	public int getIntValue() {
		return i;
	}
}

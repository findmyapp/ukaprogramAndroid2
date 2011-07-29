/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.utils;

import android.database.Cursor;

// TODO: Auto-generated Javadoc
/**
 * The Class CursorTools.
 */
public class CursorTools
{
	
	/**
	 * Gets the string from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the string from table column
	 */
	public static String getStringFromTableColumn(Cursor cursor, String tableColumnName) {
		return cursor.getString(cursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the long from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the long from table column
	 */
	public static long getLongFromTableColumn(Cursor cursor, String tableColumnName) {
		return cursor.getLong(cursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the int from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the int from table column
	 */
	public static int getIntFromTableColumn(Cursor cursor, String tableColumnName) {
		return cursor.getInt(cursor.getColumnIndex(tableColumnName));
	}
	
	/**
	 * Gets the boolean from table column.
	 *
	 * @param cursor the cursor
	 * @param tableColumnName the table column name
	 * @return the boolean from table column
	 */
	public static boolean getBooleanFromTableColumn(Cursor cursor, String tableColumnName) {
		int bool = cursor.getInt(cursor.getColumnIndex(tableColumnName));
		return (bool==1) ? true : false; 
	}
}

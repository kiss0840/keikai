/* Importer.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/5/1 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.keikai.api.model.Book;

/**
 * Importer that loads an Excel file and returns a {@link Book}.
 * @author dennis
 * @since 3.0.0
 */
public interface Importer {
		
	/**
	 * Import book from a input stream
	 * @param is the input stream
	 * @param bookName the book name for imported book
	 * @return the book instance
	 * @throws IOException
	 */
	public Book imports(InputStream is, String bookName) throws IOException;
	
	/**
	 * Import book from a file
	 * @param file the file
	 * @param bookName the book name for imported book
	 * @return the book instance
	 * @throws IOException
	 */
	public Book imports(File file, String bookName) throws IOException;
	
	/**
	 * Import book from a URL
	 * @param url the url
	 * @param bookName the book name for imported book
	 * @return the book instance
	 * @throws IOException
	 */
	public Book imports(URL url, String bookName) throws IOException;
	
	/**
	 * Import book from a input stream
	 * @param is the input stream
	 * @param bookName the book name for imported book
	 * @param postImport Object to do post processing after book is imported.
	 * @return the book instance
	 * @throws IOException
	 * @see PostImport
	 * @since 3.9.1
	 */
	public Book imports(InputStream is, String bookName, PostImport postImport) throws IOException;
	
	/**
	 * Import book from a file
	 * @param file the file
	 * @param bookName the book name for imported book
	 * @param postImport Object to do post processing after book is imported.
	 * @return the book instance
	 * @throws IOException
	 * @see PostImport
	 * @since 3.9.1
	 */
	public Book imports(File file, String bookName, PostImport postImport) throws IOException;
	
	/**
	 * Import book from a URL
	 * @param url the url
	 * @param bookName the book name for imported book
	 * @param postImport Object to do post processing after book is imported.
	 * @return the book instance
	 * @throws IOException
	 * @see PostImport
	 * @since 3.9.1
	 */
	public Book imports(URL url, String bookName, PostImport postImport) throws IOException;
}

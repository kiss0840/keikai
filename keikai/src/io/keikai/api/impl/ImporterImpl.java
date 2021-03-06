/* ImporterImpl.java

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
package io.keikai.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

import io.keikai.api.Importer;
import io.keikai.api.PostImport;
import io.keikai.model.SBook;
import io.keikai.model.impl.AbstractBookAdv;
import io.keikai.range.SImporter;
import io.keikai.api.model.Book;
import io.keikai.api.model.impl.BookImpl;
import io.keikai.api.model.impl.SimpleRef;

/**
 * 
 * @author dennis
 * @since 3.0.0
 */
public class ImporterImpl implements Importer, Serializable {
	private static final long serialVersionUID = 4040976617940828919L;
	private SImporter _importer;
	public ImporterImpl(SImporter importer) {
		this._importer = importer;
	}

	public Book imports(InputStream is, String bookName)throws IOException{
		return this.imports(is, bookName, null);
	}
	//ZSS-1283
	public Book imports(InputStream is, String bookName, PostImport postImport)throws IOException{
		if(is == null){
			throw new IllegalArgumentException("null inputstream");
		}
		if(bookName == null){
			throw new IllegalArgumentException("null book name");
		}
		BookImpl book = new BookImpl(new SimpleRef<SBook>(_importer.imports(is, bookName)));
		if (postImport != null) {
			try {
				((AbstractBookAdv)book.getNative()).setPostProcessing(true);
				postImport.process(book);
			} finally {
				((AbstractBookAdv)book.getNative()).setPostProcessing(false);
			}
		}
		return book;
	}

	public SImporter getNative() {
		return _importer;
	}


	@Override
	public Book imports(File file, String bookName) throws IOException {
		return this.imports(file, bookName, null);
	}
	//ZSS-1283
	public Book imports(File file, String bookName, PostImport postImport) throws IOException {
		if(file == null){
			throw new IllegalArgumentException("null file");
		}
		if(bookName == null){
			throw new IllegalArgumentException("null book name");
		}
		FileInputStream is = null;
		try{
			is = new FileInputStream(file);
			return imports(is,bookName, postImport);
		}finally{
			if(is!=null){
				try{
					is.close();
				}catch(Exception x){}//eat
			}
		}
	}

	@Override
	public Book imports(URL url, String bookName) throws IOException {
		return this.imports(url, bookName, null);
	}
	//ZSS-1283
	public Book imports(URL url, String bookName, PostImport postImport) throws IOException {
		if(url == null){
			throw new IllegalArgumentException("null url");
		}
		if(bookName == null){
			throw new IllegalArgumentException("null book name");
		}
		InputStream is = null;
		try{
			is = url.openStream();
			return imports(is,bookName, postImport);
		}finally{
			if(is!=null){
				try{
					is.close();
				}catch(Exception x){}//eat
			}
		}
	}
}

package org.bishopireton.files;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Scanner;

/**
 * Provides a string reader/writer for a file using arrays. The class "isa" java File.
 * Functionality provided includes counting lines in a file to determine
 * how big to make the array when reading the file. Provides an open method 
 * for both reading and writing, a close method, and then one method to read the 
 * file into an array of Strings and another to write an array of Strings to the file.
 * 
 * @author Mrs. Kelly
 */
public class MyFiles extends File {

	/**
	 * allows reading of the file
	 */
	private Scanner scanner;
	/**
	 * allows writing to the file
	 */
	private PrintWriter writer;
	
	/**
	 * version information
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * standard constructor
	 * @param pathname name of file
	 */
	public MyFiles(String pathname) {
		super(pathname);
	}

	/**
	 * standard constructor
	 * @param parent folder name for where the file resides
	 * @param child file name
	 */
	public MyFiles(String parent, String child) {
		super(parent, child);
	}
	
	/**
	 * attempts to create a scanner from the file
	 * @return if scanner was created
	 */
	public boolean openForReading() {
		try {
			scanner = new Scanner(this);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("File not found: " + this.getName());
			return false;
		}
	
  return true;
}
	/**
	 * attempts to create a printwriter from the file
	 * @return if the printwriter was created
	 */
	public boolean openForWriting() {
		try {
			writer = new PrintWriter(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found: " + this.getName());
			return false;
		}
	
  return true;
}

	/**
	 * closes either the scanner or printwriter
	 * NB: it is imperative that you close printwriters or the 
	 * last line may not be written to the file.
	 */
	public void close() {
		if (scanner != null) scanner.close();
		if (writer != null) writer.close();
	}
	
	/**
	 * counts the number of lines in the file
	 * @return number of lines in the file
	 */
	public int numLines() {
		close();
		if (! openForReading()) return -1;
		int lines = 0;
		while (scanner.hasNext()) {
			scanner.nextLine();
			lines++;
		};
		close();
		return lines;
	}
	
	/**
	 * counts the number of lines in the file, instantiates an array to
	 * read the file into and returns it.
	 * @return array of Strings with the file contents.
	 */
	public String[] readToArray() {
		int count = numLines();
		if (! openForReading()) return null;
		if (count < 0) return null;
		String[] lines = new String[count];
		for (int i = 0; i < count; i++) {
			lines[i] = scanner.nextLine();
		}
		return lines;
	}

	/**
	 * writes the data in the parameter contents to the file
	 * @param contents array of strings to write to the file
	 * @return if the file was able to be written
	 */
	public boolean writeToFile(String[] contents) {
		if (writer == null) openForWriting();
		if (writer == null) return false;
		for (String s : contents) {
			writer.println(s);
		}
		return true;
	}
}
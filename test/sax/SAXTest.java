/*
 * @(#)SAXTest.java 2010-3-16 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package sax;

import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * TODO describe this class please.
 */
public class SAXTest {

	/**
	 * @param args
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, SAXException {

		args = new String[] { "kb/super.ldl" };
		XMLReader xr = XMLReaderFactory.createXMLReader();
		MySAXApp handler = new MySAXApp();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);

		// Parse each file provided on the
		// command line.
		for (int i = 0; i < args.length; i++) {
			FileReader r = new FileReader(args[i]);
			xr.parse(new InputSource(r));
		}

	}

}

class MySAXApp extends DefaultHandler {

	public void startDocument() {
		System.out.println("Start document");
	}

	public void endDocument() {
		System.out.println("End document");
	}

	public void startElement(String uri, String name, String qName, Attributes atts) {
		if ("".equals(uri))
			System.out.println("Start element: " + qName);
		else
			System.out.println("Start element: {" + uri + "}" + name);
	}

	public void endElement(String uri, String name, String qName) {
		if ("".equals(uri))
			System.out.println("End element: " + qName);
		else
			System.out.println("End element:   {" + uri + "}" + name);
	}

	public void characters(char ch[], int start, int length) {
		System.out.print("Characters:    \"");
		for (int i = start; i < start + length; i++) {
			switch (ch[i]) {
			case '\\':
				System.out.print("\\\\");
				break;
			case '"':
				System.out.print("\\\"");
				break;
			case '\n':
				System.out.print("\\n");
				break;
			case '\r':
				System.out.print("\\r");
				break;
			case '\t':
				System.out.print("\\t");
				break;
			default:
				System.out.print(ch[i]);
				break;
			}
		}
		System.out.print("\"\n");
	}
}
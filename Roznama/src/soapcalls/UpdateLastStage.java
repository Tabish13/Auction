package soapcalls;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;

public class UpdateLastStage {

	/*
	 * public ArrayList<String[]> makeGetStages() throws Exception {
	 * 
	 * // Create SOAP Connection SOAPConnectionFactory soapConnectionFactory =
	 * SOAPConnectionFactory.newInstance(); SOAPConnection soapConnection =
	 * soapConnectionFactory.createConnection();
	 * 
	 * // Send SOAP Message to SOAP Server String url =
	 * "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx"; String
	 * case_id = "1234"; SOAPMessage soapResponse =
	 * soapConnection.call(createSOAPRequest(case_id), url); // print SOAP
	 * Response System.out.print("Response SOAP Message:"); //
	 * soapResponse.writeTo(System.out); ArrayList<String[]> data =
	 * getSOAPResponse(soapResponse); soapConnection.close(); return data;
	 * 
	 * for (int i = 0; i < data.size(); i++) { String t[]=data.get(i);
	 * System.out.println(t[0]+"\n\n"+t[1]); }
	 * 
	 * 
	 * }
	 */

	public static void main(String args[]) throws Exception {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Send SOAP Message to SOAP Server
		String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
		String case_id = "1234";
		String imageUrl = "https://api.telegram.org/file/bot183230543:AAHr9nQwmXVsGZzS8zk6gd-bd0pBC_kl5N4/document/file_22.jpg";
		SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(case_id,imageUrl), url);
		soapResponse.writeTo(System.out);
		System.out.println("\n"+printSOAPResponse(soapResponse));
		

		soapConnection.close();
	}
	
	public String getdetails(String case_id,String imageUrl) throws Exception
	{
		// Create SOAP Connection
				SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
				SOAPConnection soapConnection = soapConnectionFactory.createConnection();

				// Send SOAP Message to SOAP Server
				String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
				//String case_id = "1234";
				//String imageUrl = "https://api.telegram.org/file/bot183230543:AAHr9nQwmXVsGZzS8zk6gd-bd0pBC_kl5N4/document/file_22.jpg";
				SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(case_id,imageUrl), url);
				soapResponse.writeTo(System.out);
				//System.out.println("\n"+printSOAPResponse(soapResponse));
				String response = printSOAPResponse(soapResponse);
				soapConnection.close();
		return response;
	}

	private static SOAPMessage createSOAPRequest(String case_id,String imageUrl) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "http://tempuri.org/";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("tem", serverURI);

		/*
		 * <soapenv:Envelope
		 * xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:tem="http://tempuri.org/"> <soapenv:Header/> <soapenv:Body>
		 * <tem:UpdateLastStage> <!--Optional:-->
		 * <tem:caseNumber>123</tem:caseNumber> <!--Optional:-->
		 * <tem:eventDescription>Testing</tem:eventDescription> <!--Optional:-->
		 * <tem:binaryFile>cid:430418748526</tem:binaryFile> <!--Optional:-->
		 * <tem:fileName>Testinnn</tem:fileName> </tem:UpdateLastStage>
		 * </soapenv:Body> </soapenv:Envelope>
		 */

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("UpdateLastStage", "tem");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("caseNumber", "tem");
		soapBodyElem1.addTextNode("1234");
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("eventDescription", "tem");
		soapBodyElem2.addTextNode("Testing");
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("binaryFile", "tem");
		//String imageUrl = "https://api.telegram.org/file/bot183230543:AAHr9nQwmXVsGZzS8zk6gd-bd0pBC_kl5N4/document/file_22.jpg";
		URL url = new URL(imageUrl);
		BufferedInputStream bis = new BufferedInputStream(url.openConnection().getInputStream());
		String bin = new String(Base64.encodeBase64(getBytesFromInputStream(bis)));
		soapBodyElem3.addTextNode(bin);

		/*
		 * //working with local host File file = new
		 * File("C:/Users/Intern7/Desktop/images/PVR2.jpg");
		 * System.out.println(getBytesFromFile(file)); System.out.println(
		 * "----------------------------------------------------------------");
		 * String bin1= new String(Base64.encodeBase64(getBytesFromFile(file)));
		 * if(bin.equals(bin1)) {
		 * System.out.println("\n\n\n\n\n\n\n\n\n tabish \n\n\n\n\n\n\n\n"); }
		 * //System.out.println("\n\n\n\n\n"+bin1);
		 */
		SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("fileName", "tem");
		soapBodyElem4.addTextNode("Testinnn");

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "UpdateLastStage");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	// Method used to print the SOAP Response
	public static String printSOAPResponse(SOAPMessage soapResponse) throws Exception {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);
			byte[] test = out.toByteArray();
			//System.out.println("asadadada" + Arrays.toString(test));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(test));
			document.getDocumentElement().normalize();

			// Access nodes

			String msg = document.getElementsByTagName("UpdateLastStageResult").item(0).getTextContent();
			String response = msg;

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF];

			for (int len; (len = is.read(buffer)) != -1;)
				os.write(buffer, 0, len);

			os.flush();

			return os.toByteArray();
		}
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

}

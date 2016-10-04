
package soapcalls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

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

import org.w3c.dom.Document;

public class ValidateCaseNumber {

	
	public Boolean validateCaseNumber(String case_id) throws Exception {
		
		// Create SOAP Connection
				SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
				SOAPConnection soapConnection = soapConnectionFactory.createConnection();

				// Send SOAP Message to SOAP Server
				String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
				//String case_id = "21212";
				SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(case_id), url);
				// print SOAP Response
				System.out.print("Response SOAP Message:");
				soapResponse.writeTo(System.out);
				String[] data = getSOAPResponse(soapResponse);
				soapConnection.close();
				if(data[0]!=null)
				{
					for (int i = 0; i < data.length; i++) {
						System.out.println("\n" + data[i]);
					}
					return true;
				}
				else
				{
					System.out.println("\ngetting null");
					return false;
				}
		
	}
	/*public static void main(String args[]) throws Exception {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Send SOAP Message to SOAP Server
		String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
		String case_id = "1234";
		SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(case_id), url);
		// print SOAP Response
		System.out.print("Response SOAP Message:");
		soapResponse.writeTo(System.out);
		String[] data = getSOAPResponse(soapResponse);
		System.out.println(data.length);
		if(data[0]!=null)
		{
			for (int i = 0; i < data.length; i++) {
				System.out.println("\n" + data[i]);
			}
		}
		else
		{
			System.out.println("\ngeting null");
		}
		soapConnection.close();
	}*/

	private static SOAPMessage createSOAPRequest(String case_id) throws Exception {
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
		 * <tem:ValidateCaseNumber> <!--Optional:-->
		 * <tem:caseNumber>1234</tem:caseNumber> </tem:ValidateCaseNumber>
		 * </soapenv:Body> </soapenv:Envelope>
		 */

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("ValidateCaseNumber", "tem");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("caseNumber", "tem");
		soapBodyElem1.addTextNode(case_id);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "ValidateCaseNumber");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	// Method used to print the SOAP Response
	public static String[] getSOAPResponse(SOAPMessage soapResponse) throws Exception {

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
			//document.get
			int length = document.getElementsByTagName("ValidateCaseNumber").getLength();
			String finalresult[] = new String[3];
			if(length>0)
			{
				
				finalresult[0] = document.getElementsByTagName("LitigationID").item(0).getTextContent();
				finalresult[1] = document.getElementsByTagName("casenumber").item(0).getTextContent();
				finalresult[2] = document.getElementsByTagName("Title").item(0).getTextContent();
			}
			

			return finalresult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

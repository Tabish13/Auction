package soapcalls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import org.w3c.dom.Document;

public class GetLitigations {

	public static void main(String[] args) throws SOAPException, Exception {

		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Send SOAP Message to SOAP Server
		String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
		// String case_id = "1234";
		SOAPMessage soapResponse = soapConnection.call(createSOAPRequest("1234567890"), url);
		//print SOAP Response
		System.out.print("Response SOAP Message:");
		 soapResponse.writeTo(System.out);
		ArrayList<String[]> data = getSOAPResponse(soapResponse);
		soapConnection.close();

		for (int i = 0; i < data.size(); i++) { String t[]=data.get(i);
		 System.out.println("\n\nLitigationID: "+t[0]+"\nCaseNumber: "+t[1]+"\nHearingdate: "+t[2]+"\nCaseTitle: "+t[3]); }
	}

	public ArrayList<String[]> makeGetLitigations(String mobile_no) throws Exception {

		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Send SOAP Message to SOAP Server
		String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
		// String case_id = "1234";
		SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(mobile_no), url);
		// print SOAP Response
		System.out.print("Response SOAP Message:");
		// soapResponse.writeTo(System.out);
		ArrayList<String[]> data = getSOAPResponse(soapResponse);
		soapConnection.close();
		return data;
		/*for (int i = 0; i < data.size(); i++) { String t[]=data.get(i);
		 System.out.println("LitigationID"+t[0]+"\n\nCaseNumber"+t[1]+"Hearingdate"+t[2]+"CaseTitle"+t[3]); }*/
		
		 
		 

	}

	private static SOAPMessage createSOAPRequest(String mobile_no) throws Exception {
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
		 * <tem:GetLitigations> <tem:mobileNumber>1234567890</tem:mobileNumber>
		 * </tem:GetLitigations> </soapenv:Body> </soapenv:Envelope>
		 * 
		 */

		// SOAP Body
				SOAPBody soapBody = envelope.getBody();
				SOAPElement soapBodyElem = soapBody.addChildElement("GetLitigations", "tem");
				SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("mobileNumber", "tem");
				soapBodyElem1.addTextNode(mobile_no);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "GetLitigations");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	// Method used to print the SOAP Response
	public static ArrayList<String[]> getSOAPResponse(SOAPMessage soapResponse) throws Exception {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapResponse.writeTo(out);
			byte[] test = out.toByteArray();
			// System.out.println("asadadada" + Arrays.toString(test));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(test));
			document.getDocumentElement().normalize();

			// Access nodes
			ArrayList<String[]> finalresult = new ArrayList<>();

			int litigations_length = document.getElementsByTagName("Litigations").getLength();
			//System.out.println("\ntabish datat length"+litigations_length);
			if (litigations_length > 0) {
				for (int i = 0; i < litigations_length; i++) {
					String dataarr[] = new String[4];
					dataarr[0] = document.getElementsByTagName("LitigationID").item(i).getTextContent();
					dataarr[1] = document.getElementsByTagName("CaseNumber").item(i).getTextContent();
					dataarr[2] = document.getElementsByTagName("Hearingdate").item(i).getTextContent();
					dataarr[3] = document.getElementsByTagName("CaseTitle").item(i).getTextContent();
					finalresult.add(dataarr);
				}
			}
			return finalresult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

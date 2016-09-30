package soapcalls;

import java.io.File;
import java.io.FileOutputStream;
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
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class GetStages {

	public static void main(String args[]) throws Exception {
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Send SOAP Message to SOAP Server
		String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
		String case_id ="1234";
		SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(case_id), url);

		// print SOAP Response
		System.out.print("Response SOAP Message:");
		//soapResponse.writeTo(System.out);
		ArrayList<String[]> data = getSOAPResponse(soapResponse);
		for (int i = 0; i < data.size(); i++) {
			String t[]=data.get(i);
			System.out.println(t[0]+"\n\n"+t[1]);
		}
		soapConnection.close();
	}

	private static SOAPMessage createSOAPRequest(String case_id) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "http://tempuri.org/";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("tem", serverURI);

		/*
		 * Constructed SOAP Request Message: <soapenv:Envelope
		 * xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:tem="http://tempuri.org/"> <soapenv:Header/> <soapenv:Body>
		 * <tem:GetStages> <!--Optional:-->
		 * <tem:caseNumber>1234</tem:caseNumber> </tem:GetStages>
		 * </soapenv:Body> </soapenv:Envelope>
		 */

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("GetStages", "tem");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("caseNumber", "tem");
		soapBodyElem1.addTextNode(case_id);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "GetStages");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	// Method used to print the SOAP Response
	public static ArrayList<String []> getSOAPResponse(SOAPMessage soapResponse) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.print("\nResponse SOAP Message:");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);

		try {
			// Write response into file
			FileOutputStream out = null;
			out = new FileOutputStream("C:/Users/Intern7/Desktop/SOAP Response/output.xml");
			soapResponse.writeTo(out);

			// logger.info(myclass, );

			// Read response from file
			File inputFile = new File("C:/Users/Intern7/Desktop/SOAP Response/output.xml");

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputFile);
			document.getDocumentElement().normalize();

			// Access nodes
			ArrayList<String []> finalresult = new ArrayList<>();
			
			int stageslength = document.getElementsByTagName("Stage").getLength();
			for (int i = 0; i < stageslength; i++) {
				String dataarr[]= new String[2];   
				dataarr[0] = document.getElementsByTagName("StageMasterOID").item(i).getTextContent();
				dataarr[1]= document.getElementsByTagName("Stage").item(i).getTextContent();
				finalresult.add(dataarr);
			}
			
			
			return finalresult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

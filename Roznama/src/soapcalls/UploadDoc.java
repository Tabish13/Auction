package soapcalls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
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

import org.w3c.dom.Document;

public class UploadDoc {


	public static void main(String args[]) throws Exception {
	// Create SOAP Connection
	SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	// Send SOAP Message to SOAP Server
	String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
	String case_id ="1234";
	SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(case_id), url); 
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	soapResponse.writeTo(out);
	byte[] test = out.toByteArray();
	System.out.println("asadadada"+Arrays.toString(test));
	 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(new ByteArrayInputStream(test));
	    document.getDocumentElement().normalize();
	    String msg = document.getElementsByTagName("InsertNextStageResult").item(0).getTextContent();
	
	System.out.println("output: "+msg);
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
		 * <soapenv:Envelope
		 * xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:tem="http://tempuri.org/"> <soapenv:Header/> <soapenv:Body>
		 * <tem:InsertNextStage> <!--Optional:-->
		 * <tem:caseNumber>1234</tem:caseNumber>
		 * <tem:stageOID>555</tem:stageOID> <!--Optional:-->
		 * <tem:stageDescription>Testing...</tem:stageDescription>
		 * <tem:hearingDate>30/09/2016</tem:hearingDate> <!--Optional:-->
		 * <tem:isNonHearingCase></tem:isNonHearingCase> </tem:InsertNextStage>
		 * </soapenv:Body> </soapenv:Envelope>
		 *
		 */

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("InsertNextStage", "tem");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("caseNumber", "tem");
		soapBodyElem1.addTextNode("1234");
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("stageOID", "tem");
		soapBodyElem2.addTextNode("555");
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("stageDescription", "tem");
		soapBodyElem3.addTextNode("Testing...");
		SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("hearingDate", "tem");
		Date date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format1.format(date);
		//System.out.println("date given"+formatted);
		soapBodyElem4.addTextNode(formatted);
		SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("isNonHearingCase", "tem");

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "InsertNextStage");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}
	}



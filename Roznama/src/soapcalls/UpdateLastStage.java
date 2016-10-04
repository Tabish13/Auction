package soapcalls;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;

import Bot.MyLogger;

public class UpdateLastStage {
	private final static String myclass = UpdateLastStage.class.getName();
	MyLogger logger = MyLogger.getInstance();

	/*public static void main(String args[]) throws Exception {
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
	}*/
	
	public String getdetails(String case_id,String imageUrl) 
	{
		try
		{
		// Create SOAP Connection
				SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
				SOAPConnection soapConnection = soapConnectionFactory.createConnection();

				// Send SOAP Message to SOAP Server
				String url = "http://115.113.146.235/RoznamaWebservice/RoznamaService.asmx";
				//String case_id = "1234";
				//String imageUrl = "https://api.telegram.org/file/bot183230543:AAHr9nQwmXVsGZzS8zk6gd-bd0pBC_kl5N4/document/file_22.jpg";
				SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(case_id,imageUrl), url);
				logger.info(myclass, "got soap response");
				//soapResponse.writeTo(System.out);
				//System.out.println("\n"+printSOAPResponse(soapResponse));
				String response = printSOAPResponse(soapResponse);
				soapConnection.close();
		return response;
		}catch (Exception e) {
			
			logger.error(myclass, "method getdetails error", e);
			return null;
		}
	}

	private  SOAPMessage createSOAPRequest(String case_id,String imageUrl) throws Exception {
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
		logger.info(myclass, "image to input stream");
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
		logger.info(myclass, "soap request created");
		/*System.out.print("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println();*/

		return soapMessage;
	}

	// Method used to print the SOAP Response
	public String printSOAPResponse(SOAPMessage soapResponse) throws Exception {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			//soapResponse.writeTo(out);
			
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
			logger.error(myclass, "method('printSOAPResponse()')", e);
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

}

package example.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlExample {
	
	public XmlExample() {
		
	}
	
	public XmlExample(String filePath) throws Exception {
		this.filePath = filePath; 
		this.document = parseXmlFile(filePath);
	}
	
	private String filePath;
	
	private Document document;
	
	public static void main(String[] args) {
		
		String filePath = "C:/\\zipTest/\\test - 복사본/\\Contents/\\section0.xml";
		String text ="문서 제목 [HY헤드라인M 22pt]-test";
		String id = "documentTitle";
	      try {
	    	  
	    	  XmlExample xmlExample = new XmlExample(filePath);
	    	  
	    	  System.out.println(xmlExample.getXmlContentTextById(id));
	          
	          xmlExample.setXmlContentById(id,  text);
	          
	          System.out.println(xmlExample.getXmlContentTextById(id));
	      } catch (Exception e) {
	          e.printStackTrace();
	      } 

	}
	
	public void setXmlContentById(String filePath, String id, String text) throws Exception {
		try {
			this.document = parseXmlFile(filePath);
			setXmlContentById(id, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void setXmlContentById(String id, String text) throws Exception {
		try {
			if(setNodeTextContentById(this.document, id,  text)) {
				writeXmlFile(this.document, this.filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String getXmlContentTextById(String filePath, String id) throws Exception {
	  	  String text =null;
			try {
				this.document = parseXmlFile(filePath);
				text = getXmlContentTextById(id);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return text;
	}

	public String getXmlContentTextById(String id) throws Exception {
	  	  String text =null;
			try {
				text = getNodeTextContentById(this.document, id);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return text;
	}
	
	public void writeXmlFile(Document document, String filePath) throws Exception {
        DOMSource source = new DOMSource(document);
        FileWriter writer = null;
		try {
			writer = new FileWriter(new File(filePath));
			StreamResult result = new StreamResult(writer);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			throw e;
		} catch (TransformerException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(writer!=null) {
				writer.close();
			}
		}
	}
	
	public boolean setNodeTextContentById(Document document, String id, String text) {
		Node node =getNodeById(document, id);
		if(node==null) {
			return false;
		}else {
			node.setTextContent(text);
			return true;
		}
	}
	
	public String getNodeTextContentById(Document document, String id) {
		Node node =getNodeById(document, id);
		if(node==null) {
			return "";
		}else {
			return node.getTextContent();
		}
	}
	
	public Node getNodeById(Document document, String id) {
		if(document==null||id==null||id.isEmpty()) {
			return null;
		}
		Element documentElement = document.getDocumentElement();
		return getNodeById(documentElement.getChildNodes(), id);
	}

	public Node getNodeById(NodeList childNodes, String id) {
		if(childNodes!=null&&childNodes.getLength()>0) {
			for(int i=0, length =childNodes.getLength(); i<length; i++) {
				Node child = childNodes.item(i);
				if(isTheNode(child ,id)) {
					return child;
				}else {
					Node childNode = getNodeById(child.getChildNodes(), id);
					if(childNode!=null) {
						return childNode;
					}
				}
			}
		}
		return null;
	}
	
	public boolean isTheNode(Node child , String id) {
		if(child!=null&&id!=null&&!id.isEmpty()) {
			NamedNodeMap nodeAttributeKeys = child.getAttributes();
			if(nodeAttributeKeys!=null) {
				Node idNode =nodeAttributeKeys.getNamedItem("id");
				if(idNode!=null) {
					return idNode.getNodeValue().equals(id);
				}
			}
		}
		return false;
	}
	
	public Document parseXmlFile(String filePath) throws Exception {
		File file = new File(filePath);
		if(!file.exists()) {
			return null;
		}
	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    Document document = null;
	    try {
	    	documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        document = documentBuilder.parse(file);
	        document.getDocumentElement().normalize();
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	    	e.printStackTrace();
	    	throw e;
	    } 
	    return document;
	}
}

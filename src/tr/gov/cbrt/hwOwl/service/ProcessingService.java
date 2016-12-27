package tr.gov.cbrt.hwOwl.service;

public interface ProcessingService {
	public static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	public static final String JAXP_SCHEMA_LOCATION = "http://java.sun.com/xml/jaxp/properties/schemaSource";
	public static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	public static final String JAXB_MODEL_PACKAGE = "tr.gov.cbrt.hwOwl.model";
	public static final String OWL_FILE_PATH = ".\\owldata\\";
	public static final String RDF_FILE_PATH = ".\\rdfdata\\";
	public static final String TXT_FILE_PATH = ".\\txtdata\\";
	public static final String QUERY_FILE_PATH = ".\\query\\";
	public static final String IRI = "http://www.semanticweb.org/tp11501203/owl/";
	public static final String[] OWL_FILES = { "cbrt0.owl","cbrt1.owl", "cbrt2.owl", "cbrt3.owl", "cbrt4.owl", "cbrt5.owl" };
	public static final String[] QUERY_FILES = { "q1.rq", "q2.rq", "q3.rq" };

	public void validateOWLFile(String owlFile);

	public void uniteRDFFiles(String[] rdfFiles);

	public void queryOntology(String owlFile, String queryFile) throws Exception;

}

package tr.gov.cbrt.hwOwl.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import tr.gov.cbrt.hwOwl.service.ProcessingService;

public class ProcessingServiceImpl implements ProcessingService {

	@Override
	public void validateOWLFile(String owlFile) {
		File inputfile = new File(OWL_FILE_PATH + owlFile);
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
		OWLOntology yourOntology;
		try {
			yourOntology = manager.loadOntologyFromOntologyDocument(inputfile);
			Optional<org.semanticweb.owlapi.model.IRI> ontologyIRI = yourOntology.getOntologyID().getOntologyIRI();
			OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
			OWLReasoner reasoner = reasonerFactory.createReasoner(yourOntology);
			if (reasoner.isConsistent()) {
				System.out.println("Tutarlı");
			} else {
				System.out.println("Tutarlı değil");
			}
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void uniteRDFFiles(String[] rdfFiles) {

		StringBuilder buffer = new StringBuilder();
		String newLine = System.lineSeparator();
		List<Individual> indList = new ArrayList<>();
		for (int i = 1; i < rdfFiles.length; i++) {
			OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
			InputStream ontologyIn = FileManager.get().open(OWL_FILE_PATH + rdfFiles[i]);
			model.read(ontologyIn, "RDF/XML");
			ExtendedIterator<? extends OntResource> individuals = model.listIndividuals();

			while (individuals.hasNext()) {
				OntResource individual = individuals.next();
				if (individual.isIndividual()) {
					indList.add((Individual) individual);
				}
			}
		}
		try {
			OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
			for (Individual ind : indList) {
				m.createIndividual(ind);
			}
			FileWriter out = new FileWriter(TXT_FILE_PATH + "outOwl.owl");
			m.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void queryOntology(String owlFile, String queryFile) throws Exception {
		Model model = ModelFactory.createDefaultModel();
		model.read(new FileInputStream(OWL_FILE_PATH+owlFile), null);
		String queryString = readFile(QUERY_FILE_PATH + queryFile, Charset.defaultCharset());
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.out(outputStream, results, query);
		OutputStream out = new FileOutputStream(TXT_FILE_PATH + queryFile + "_result.txt");
		outputStream.writeTo(out);
		outputStream.close();
		qe.close();
	}

	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}

package tr.gov.cbrt.hwOwl.service.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.impl.StmtIteratorImpl;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.Filter;

import tr.gov.cbrt.hwOwl.service.InferenceService;
import tr.gov.cbrt.hwOwl.service.ProcessingService;

public class InferenceServiceImpl implements InferenceService {

	@Override
	public void writeInferenceModel2File(String owlFile) {

        String NS = "http://www.semanticweb.org/tp11501203/owl/";
        final OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM );
        OntClass a = model.createClass( NS+"A" );
        OntClass b = model.createClass( NS+"B" );
        a.addSubClass( b );
        model.createIndividual( NS+"i", b );

        // Create the inference model.
        Reasoner r = (Reasoner) ReasonerRegistry.getOWLReasoner();
        
        InfModel pModel = ModelFactory.createInfModel((org.apache.jena.reasoner.Reasoner) r, model);

        // An iterator over the statements of pModel that *aren't* in the base model.
        ExtendedIterator<Statement> stmts = pModel.listStatements().filterDrop( new Filter<Statement>() {
            //@Override
            public boolean accept(Statement o) {
                return model.contains( o );
            }
        });

        // For convenient printing, we create a model for the deductions.
        // (If stmts were a StmtIterator, we could add it directly.  It's not,
        // so we end up creating a new StmtIteratorImpl, which is kludgey, but
        // it's more efficient than converting the thing to a list.)
        Model deductions = ModelFactory.createDefaultModel().add( new StmtIteratorImpl( stmts ));
        File file2out = new File(ProcessingService.TXT_FILE_PATH + owlFile + "Inference.txt");

        Writer out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2out)));
			 deductions.write( out, "TTL" );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       

	}
	

}

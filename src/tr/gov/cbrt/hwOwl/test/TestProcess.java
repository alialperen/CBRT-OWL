package tr.gov.cbrt.hwOwl.test;

import org.junit.Test;

import tr.gov.cbrt.hwOwl.service.ProcessingService;
import tr.gov.cbrt.hwOwl.service.Impl.ProcessingServiceImpl;

public class TestProcess {

	@Test
	public final void testValidateOWLFile() {
		ProcessingService service = new ProcessingServiceImpl();
		service.validateOWLFile(ProcessingService.OWL_FILES[0]);
	}

	@Test
	public final void testUniteRDFFiles() {
		ProcessingService service = new ProcessingServiceImpl();
		service.uniteRDFFiles(ProcessingService.OWL_FILES);
	}

	@Test
	public final void testQueryOntology() throws Exception {
		ProcessingService service = new ProcessingServiceImpl();
		service.queryOntology( ProcessingService.OWL_FILES[1],ProcessingService.QUERY_FILES[2]);
	}

}

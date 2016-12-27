package tr.gov.cbrt.hwOwl.test;

import org.junit.Test;

import tr.gov.cbrt.hwOwl.service.HierarchyService;
import tr.gov.cbrt.hwOwl.service.ProcessingService;
import tr.gov.cbrt.hwOwl.service.Impl.HierarchyServiceImpl;

public class TestHierarchy {

	@Test
	public final void testReadOntologyAndWrite2File() {
		HierarchyService service = new HierarchyServiceImpl();
		service.readOntologyHierarchyAndWrite2File(ProcessingService.OWL_FILES[0]);
	}

}

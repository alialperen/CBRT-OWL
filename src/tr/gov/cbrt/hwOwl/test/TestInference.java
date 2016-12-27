package tr.gov.cbrt.hwOwl.test;

import org.junit.Test;

import tr.gov.cbrt.hwOwl.service.InferenceService;
import tr.gov.cbrt.hwOwl.service.ProcessingService;
import tr.gov.cbrt.hwOwl.service.Impl.InferenceServiceImpl;

public class TestInference {


	@Test
	public final void testWriteInferenceModel2File() {
		InferenceService service = new InferenceServiceImpl();
		service.writeInferenceModel2File(ProcessingService.OWL_FILES[0]);
	}

}

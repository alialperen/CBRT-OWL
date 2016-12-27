package tr.gov.cbrt.hwOwl.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

import tr.gov.cbrt.hwOwl.service.HierarchyService;
import tr.gov.cbrt.hwOwl.service.ProcessingService;

public class HierarchyServiceImpl implements HierarchyService {

	@Override
	public void readOntologyHierarchyAndWrite2File(String owlFile) {
		final Model model = ModelFactory.createDefaultModel();
		File initialFile = new File(ProcessingService.OWL_FILE_PATH + owlFile);
		InputStream in;
		try {
			in = new FileInputStream(initialFile);
			model.read(in, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		final Queue<List<Resource>> queue = new LinkedList<>();
		final List<Resource> thingPath = new ArrayList<>();
		thingPath.add(OWL.Thing);
		queue.offer(thingPath);
		StringBuilder buffer = new StringBuilder();
		String newLine = System.lineSeparator();
		buffer.append(owlFile + newLine);
		// Get the paths, and display them
		final List<List<Resource>> paths = BFS(model, queue, 4);
		for (List<Resource> path : paths) {
			buffer.append(path + newLine);
		}
		try {
			Files.write(Paths.get(ProcessingService.TXT_FILE_PATH + owlFile + "Hierarchy.txt"),
					buffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<List<Resource>> BFS(final Model model, final Queue<List<Resource>> queue, final int depth) {
		final List<List<Resource>> results = new ArrayList<>();
		while (!queue.isEmpty()) {
			final List<Resource> path = queue.poll();
			results.add(path);
			if (path.size() < depth) {
				final Resource last = path.get(path.size() - 1);
				final StmtIterator stmt = model.listStatements(null, RDFS.subClassOf, last);
				while (stmt.hasNext()) {
					final List<Resource> extPath = new ArrayList<>(path);
					extPath.add(stmt.next().getSubject().asResource());
					queue.offer(extPath);
				}
			}
		}
		return results;
	}

}

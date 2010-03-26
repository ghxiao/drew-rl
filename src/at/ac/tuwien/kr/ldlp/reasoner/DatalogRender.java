package at.ac.tuwien.kr.ldlp.reasoner;

import java.io.Writer;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxRenderer;

public class DatalogRender extends ManchesterOWLSyntaxRenderer {

	protected DatalogRender(OWLOntologyManager owlOntologyManager) {
		super(owlOntologyManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
		// TODO Auto-generated method stub

	}

}

package at.ac.tuwien.kr.owlapi.rdfxml.parser;


import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

public class ObjectPropertyExpressionTranslatorSelector {

	private OWLRDFConsumer consumer;
	
	private LDLObjectPropertyIntersectionOfTranslator objectPropertyIntersectionOfTranslator;
	
	private LDLObjectPropertyUnionOfTranslator objectPropertyUnionOfTranslator;
	
	private LDLObjectPropertyTransitiveClosureOfTranslator objectPropertyTransitiveClosureOfTranslator;

	public ObjectPropertyExpressionTranslatorSelector(OWLRDFConsumer consumer) {
		this.consumer = consumer;
		objectPropertyIntersectionOfTranslator = new LDLObjectPropertyIntersectionOfTranslator(consumer);
		objectPropertyUnionOfTranslator = new LDLObjectPropertyUnionOfTranslator(consumer);
		objectPropertyTransitiveClosureOfTranslator = new LDLObjectPropertyTransitiveClosureOfTranslator(consumer);
	}

	public ObjectPropertyExpressionTranslator getObjectPropertyExpressionTranslator(IRI mainNode) {
		
		if(consumer.hasPredicate(mainNode, LDL_OBJECT_PROPERTY_INTERSECTION_OF.getIRI())){
			return objectPropertyIntersectionOfTranslator;
		}
		
		if(consumer.hasPredicate(mainNode, LDL_OBJECT_PROPERTY_UNION_OF.getIRI())){
			return objectPropertyUnionOfTranslator;
		}
		
		if(consumer.hasPredicate(mainNode, LDL_OBJECT_PROPERTY_TRANSITIVE_CLOSURE_OF.getIRI())){
			return objectPropertyTransitiveClosureOfTranslator;
		}
		
		
		throw new UnsupportedOperationException();
	}
}

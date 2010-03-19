package at.ac.tuwien.kr.owlapi.rdfxml.parser;


import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

public class ObjectPropertyExpressionTranslatorSelector {

	private OWLRDFConsumer consumer;
	
	private LDLObjectPropertyIntersectionOfTranslator objectPropertyIntersectionOfTranslator;
	
	private LDLObjectPropertyUnionOfTranslator objectPropertyUnionOfTranslator;
	
	private LDLObjectPropertyTransitiveClosureOfTranslator objectPropertyTransitiveClosureOfTranslator;
	
	private LDLObjectPropertyChainOfTranslator objectPropertyChainOfTranslator;

	private OWLObjectPropertyInverseOfTranslator ObjectPropertyInverseOfTranslator;
	
	public ObjectPropertyExpressionTranslatorSelector(OWLRDFConsumer consumer) {
		this.consumer = consumer;
		objectPropertyIntersectionOfTranslator = new LDLObjectPropertyIntersectionOfTranslator(consumer);
		objectPropertyUnionOfTranslator = new LDLObjectPropertyUnionOfTranslator(consumer);
		objectPropertyTransitiveClosureOfTranslator = new LDLObjectPropertyTransitiveClosureOfTranslator(consumer);
		objectPropertyChainOfTranslator = new LDLObjectPropertyChainOfTranslator(consumer);
		ObjectPropertyInverseOfTranslator = new OWLObjectPropertyInverseOfTranslator(consumer);
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
		
		if(consumer.hasPredicate(mainNode, LDL_OBJECT_PROPERTY_CHAIN_OF.getIRI())){
			return objectPropertyChainOfTranslator;
		}
		
		if(consumer.hasPredicate(mainNode, OWL_INVERSE_OF.getIRI())){
			return ObjectPropertyInverseOfTranslator;
		}
		
		throw new UnsupportedOperationException();
	}
}

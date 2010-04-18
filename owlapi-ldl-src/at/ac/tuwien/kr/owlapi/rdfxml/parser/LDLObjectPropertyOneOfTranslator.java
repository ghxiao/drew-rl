/*
 * @(#)LDLObjectPropertyChainOfTranslator.java 2010-3-18 
 *
 * Author: Guohui Xiao
 * Technical University of Vienna
 * KBS Group
 */
package at.ac.tuwien.kr.owlapi.rdfxml.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.kr.ldlp.reasoner.LDLPAxiomCompiler;
import at.ac.tuwien.kr.owlapi.model.ldl.LDLIndividualPair;

/**
 * TODO describe this class please.
 */
public class LDLObjectPropertyOneOfTranslator extends AbstractObjectPropertyExpressionTranslator {

	final static Logger logger = LoggerFactory.getLogger(LDLObjectPropertyOneOfTranslator.class);

	final OWLRDFConsumer consumer = getConsumer();

	public LDLObjectPropertyOneOfTranslator(OWLRDFConsumer consumer) {
		super(consumer);
	}

	// @Override
	// protected OWLObjectPropertyExpression
	// createObjectPropertyExpression(Set<OWLObjectPropertyExpression> operands)
	// {
	//
	// Set<LDLIndividualPair> pairs = new HashSet<LDLIndividualPair>();
	//
	// for (OWLObjectPropertyExpression op : operands) {
	// if(op instanceof LDLIndividualPair){
	// LDLIndividualPair pair = (LDLIndividualPair)op;
	// pairs.add(pair);
	// }else{
	// throw new IllegalArgumentException("Expected that "+ op +
	// " should be OWLIndividualPair");
	// }
	// }
	//
	// return getDataFactory().getLDLObjectPropertyOneOf(pairs);
	// }
	//
	// @Override
	// protected IRI getPredicateIRI() {
	// return OWLRDFVocabulary.LDL_OBJECT_PROPERTY_ONE_OF.getIRI();
	// }

	@Override
	public OWLObjectPropertyExpression translate(IRI mainNode) {
		logger.debug("Start translate LDLObjectPropertyOneOf {}", mainNode);

		IRI oneOfObject = getResourceObject(mainNode, OWLRDFVocabulary.LDL_OBJECT_PROPERTY_ONE_OF.getIRI(), true);
		Set<LDLIndividualPair> pairs = translateToIndividualPairSet(oneOfObject);
		// for (OWLIndividual ind : individuals) {
		// if (!ind.isAnonymous()) {
		// getConsumer().addIndividual(((OWLNamedIndividual) ind).getIRI());
		// }
		// }
		if (pairs.isEmpty()) {
			logger.info("Empty set in ldl:objectPropertyOneOf property expression - converting to owl:Nothing");
			return getDataFactory().getOWLBottomObjectProperty();
		}
		return getDataFactory().getLDLObjectPropertyOneOf(pairs);
	}

	private Set<LDLIndividualPair> translateToIndividualPairSet(IRI mainNode) {
		logger.debug("translateToIndividualPairSet {}", mainNode);

		List<LDLIndividualPair> list = new ArrayList<LDLIndividualPair>();

		translateList(mainNode, list);

		Set<LDLIndividualPair> pairs = new HashSet<LDLIndividualPair>(list);
		return pairs;
	}

	private void translateList(IRI mainNode, List<LDLIndividualPair> list) {
		if (!consumer.isList(mainNode, true)) {
			// I originally threw an exception here, but some ontologies
			// seem to have missing type triples for lists where it's obvious
			// that the node is a list

			logger.debug("Untyped list found: " + mainNode);

		}

		IRI firstResource = consumer.getFirstResource(mainNode, true);
		LDLIndividualPairTranslator translator = new LDLIndividualPairTranslator(consumer);

		if (firstResource != null) {
			list.add((LDLIndividualPair) translator.translate(firstResource));
		} else {
			OWLLiteral literal = consumer.getFirstLiteral(mainNode);
			if (literal != null) {
				list.add((LDLIndividualPair) translator.translate((IRI) literal));
			} else {
				// Empty list?

				logger.debug("Possible malformed list: rdf:first triple missing");

			}
		}
		IRI rest = consumer.getRest(mainNode, true);
		if (rest != null) {
			translateList(rest, list);
		}

	}

}

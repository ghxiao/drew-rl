;*Query13*
;(type Person ?X)
;(hasAlumnus http://www.University0.edu ?X)
;/- Property hasAlumnus is defined in the benchmark ontology as the
;inverse of property degreeFrom, which has three subproperties:
;undergraduateDegreeFrom, mastersDegreeFrom, and doctoralDegreeFrom. The
;benchmark data state a person as an alumnus of a university using one of
;these three subproperties instead of hasAlumnus. Therefore, this query
;assumes subPropertyOf relationships between degreeFrom and its
;subproperties, and also requires inference about inverseOf.
;/



(retrieve (?x)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Person|)
		
		(|http://www.University0.edu| ?x 
		    |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#hasAlumnus|)	
	)
)

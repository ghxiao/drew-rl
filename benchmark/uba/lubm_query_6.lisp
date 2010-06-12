;*Query6*
;(type Student ?X)
;/- This query queries about only one class. But it assumes both the
;explicit subClassOf relationship between UndergraduateStudent and
;Student and the implicit one between GraduateStudent and Student. In
;addition, it has large input and low selectivity./



(retrieve (?x)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Student|)
		
		
	)
)

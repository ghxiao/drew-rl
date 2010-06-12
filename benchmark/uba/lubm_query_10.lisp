;*Query10*
;(type Student ?X)
;(takesCourse ?X http://www.Department0.University0.edu/GraduateCourse0)
;/- This query differs from Query 6, 7, 8 and 9 in that it only requires
;the (implicit) subClassOf relationship between GraduateStudent and
;Student, i.e., subClassOf relationship between UndergraduateStudent and
;Student does not add to the results./




(retrieve (?x )
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Student|)
		
		(?x |http://www.Department0.University0.edu/GraduateCourse0|
		 |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#takesCourse|)	
		
	
	)
)

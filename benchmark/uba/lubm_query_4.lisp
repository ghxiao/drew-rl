;*Query4*
;(type Professor ?X)
;(worksFor ?X http://www.Department0.University0.edu)
;(name ?X ?Y1)
;(emailAddress ?X ?Y2)
;(telephone ?X ?Y3)
;/- This query has small input and high selectivity. It assumes
;subClassOf relationship between Professor and its subclasses. Class
;Professor has a wide hierarchy. Another feature is that it queries about
;multiple properties of a single class./



(retrieve (?x ;?y1 
;?y2 
;?y3
)
	(and
	;	(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Professor|)
		
	;	(?x |http://www.Department0.University0.edu| 
	;	|http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#worksFor|)
		
	;	(?x ?y1 |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#name|)
		
	;	(?x ?y2 |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#emailAddress|)
		
		(?x ?y3 |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#telephone|)
	)
)

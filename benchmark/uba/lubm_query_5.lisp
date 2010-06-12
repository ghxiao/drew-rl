;*Query5*
;(type Person ?X)
;(memberOf ?X http://www.Department0.University0.edu)
;/- This query assumes subClassOf relationship between Person and its
;subclasses and subPropertyOf relationship between memberOf and its
;subproperties. Moreover, class Person features a deep and wide hierarchy./



(retrieve (?x)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Person|)
		
		(?x |http://www.Department0.University0.edu| 
		|http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#memberOf|)

	)
)

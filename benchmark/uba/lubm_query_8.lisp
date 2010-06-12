;*Query8*
;(type Student ?X)
;(type Department ?Y)
;(memberOf ?X ?Y)
;(subOrganizationOf ?Y http://www.University0.edu)
;(emailAddress ?X ?Z)
;/- This query is further more complex than Query 7 by including one more
;property./



(retrieve (?x ?y ?z)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Student|)
		
		(?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Department|)
		
		(?x ?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#memberOf|)
		
		(?y |http://www.University0.edu| 
		   |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#subOrganizationOf|)
			
		(?x ?z |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#emailAddress|)	
	)
)

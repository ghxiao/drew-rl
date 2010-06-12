;*Query12*
;(type Chair ?X)
;(type Department ?Y)
;(worksFor ?X ?Y)
;(subOrganizationOf ?Y http://www.University0.edu)
;/- The benchmark data do not produce any instances of class Chair.
;Instead, each Department individual is linked to the chair professor of
;that department by property headOf. Hence this query requires
;realization, i.e., inference that that professor is an instance of class
;Chair because he or she is the head of a department. Input of this query
;is small as well./



(retrieve (?x ?y)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Chair|)
		
		(?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Department|)
		
		(?x ?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#worksFor|)	
		
		(?y |http://www.University0.edu| 
		    |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#subOrganizationOf|)	
	)
)

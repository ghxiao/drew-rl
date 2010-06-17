;ans(x, y, z) :- Military-Person(x), hasRole(y, x), related(x, z)
;http://vicodi.org/ontology#

(ENABLE-DATA-SUBSTRATE-MIRRORING)

(retrieve (?x ?y ?z)
	(and
		(?x  |http://vicodi.org/ontology#Military-Person|)
		
		(?y ?x |http://vicodi.org/ontology#hasRole|)
		
		(?x ?z |http://vicodi.org/ontology#related|)
			
	)
)

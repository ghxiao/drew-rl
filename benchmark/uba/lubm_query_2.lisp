;(type GraduateStudent ?X)
;(type University ?Y)
;(type Department ?Z)
;(memberOf ?X ?Z)
;(subOrganizationOf ?Z ?Y)
;(undergraduateDegreeFrom ?X ?Y)

(retrieve (?x ?y ?z)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#GraduateStudent|)
		(?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#University|)
		(?z |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Department|)
		(?x ?z |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#memberOf|)
		(?y ?z |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#subOrganizationOf|)
		(?z ?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#undergraduateDegreeFrom|)
	)
)

;*Query9*
;(type Student ?X)
;(type Faculty ?Y)
;(type Course ?Z)
;(advisor ?X ?Y)
;(takesCourse ?X ?Z)
;(teacherOf ?Y ?Z)
;/- Besides the aforementioned features of class Student and the wide
;hierarchy of class Faculty, like Query 2, this query is characterized by
;the most classes and properties in the query set and there is a
;triangular pattern of relationships./



(retrieve (?x ?y ?z)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Student|)
		
		(?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Faculty|)
		
		(?z |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Course|)
		
		(?x ?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#advisor|)
		
		(?x ?z |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#takesCourse|)	
		
		(?y ?z |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#teacherOf|)	
	)
)

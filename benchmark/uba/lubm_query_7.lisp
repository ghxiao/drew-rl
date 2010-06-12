;*Query7*
;(type Student ?X)
;(type Course ?Y)
;(teacherOf http://www.Department0.University0.edu/AssociateProfessor0 ?Y)
;(takesCourse ?X ?Y)
;/- This query is similar to Query 6 in terms of class Student but it
;increases in the number of classes and properties and its selectivity is
;high./


(retrieve (?x ?y)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Student|)
		
		(?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Course|)
		
		(|http://www.Department0.University0.edu/AssociateProfessor0| ?y 
			|http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#teacherOf|)
			
		(?x ?y |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#takesCourse|)	
	)
)

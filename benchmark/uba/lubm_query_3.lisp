;(type Publication ?X)
;(publicationAuthor ?X
;http://www.Department0.University0.edu/AssistantProfessor0)
;/- This query is similar to Query 1 but class Publication has a wide
;hierarchy./;

;DL Query: [Publication and publicationAuthor value AssistantProfessor0]

;X
;http://www.Department0.University0.edu/AssistantProfessor0/Publication0
;http://www.Department0.University0.edu/AssistantProfessor0/Publication1
;http://www.Department0.University0.edu/AssistantProfessor0/Publication2
;http://www.Department0.University0.edu/AssistantProfessor0/Publication3
;http://www.Department0.University0.edu/AssistantProfessor0/Publication4
;http://www.Department0.University0.edu/AssistantProfessor0/Publication5


(retrieve (?x)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#Publication|)
		
		(?x |http://www.Department0.University0.edu/AssistantProfessor0| 
		|http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#publicationAuthor|)
		
	)
)

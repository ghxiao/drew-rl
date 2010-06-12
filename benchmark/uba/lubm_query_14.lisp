;*Query14*
;(type UndergraduateStudent ?X)
;/- This query is the simplest in the test set. This query represents
;those with large input and low selectivity and does not assume any
;hierarchy information or inference.
;/




(retrieve (?x)
	(and
		(?x |http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#UndergraduateStudent|)

	)
)

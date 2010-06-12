#!/usr/bin/python

import time
import os


def main():
    ontology = "University0_0.owl"
    q = "lubm_query_14.lisp"
    cmd = "RacerPro -f %s -q %s"%(ontology, q)

    t1 = time.time()
    os.system(cmd)
    t2 = time.time()
    print ("t=%f")%(t2-t1)


if __name__ == '__main__':
    main()

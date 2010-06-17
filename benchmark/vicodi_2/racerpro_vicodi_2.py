#!/usr/bin/python

import time
import os


def main():
    ontology = "vicodi_2.owl"
    for i in range(1,3):
        cmd = "RacerPro -f %s -q vicodi_query_%d.lisp"%(ontology, i)
        print "Qurey ID : %d" % (i)
        t1 = time.time()
        os.system(cmd)
        t2 = time.time()
        print ("t=%f")%(t2-t1)


if __name__ == '__main__':
    main()

#!/usr/bin/python

import time
import os


def main():
    ontology = "University0_0.owl"
    dlp = "lubm_query_4.dlp"
    cmd = "dlvhex --ontology=%s %s"%(ontology, dlp)

    t1 = time.time()
    os.system(cmd)
    t2 = time.time()
    print ("t=%f")%(t2-t1)


    
    
if __name__ == '__main__':
    main()

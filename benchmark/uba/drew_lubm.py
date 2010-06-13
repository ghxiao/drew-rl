#!/usr/bin/python

import time
import os


def main():
	cmd = "/home/xiao/usr/local/jdk1.6.0_13/bin/java -Dfile.encoding=UTF-8 -classpath /host/OntoRule/implementations/DReW/bin:/usr/lib/eclipse/plugins/org.junit_3.8.2.v20090203-1005/junit.jar:/host/OntoRule/implementations/DReW/lib/datalog.jar:/host/OntoRule/implementations/DReW/lib/log4j-1.2.15.jar:/host/OntoRule/implementations/DReW/lib/slf4j-api-1.5.11.jar:/host/OntoRule/implementations/DReW/lib/slf4j-log4j12-1.5.11.jar:/host/OntoRule/implementations/DReW/lib/google-collect-1.0.jar:/usr/lib/eclipse/plugins/org.junit4_4.5.0.v20090824/junit.jar:/usr/lib/eclipse/plugins/org.hamcrest.core_1.1.0.jar at.ac.tuwien.kr.ldlp.eval.LUBM_Query "
	os.chdir('/host/OntoRule/implementations/DReW/')
	for i in range(1,15):
	    t1 = time.time()
	    os.system("%s %d"%(cmd,i))
	    t2 = time.time()
	    print ("t=%f")%(t2-t1)


    
    
if __name__ == '__main__':
    main()




#!/bin/sh
mvn -DaltDeploymentRepository=github-ghxiao-snapshots::default::file:../ghxiao-mvn-repo.git/snapshots clean deploy -Dmaven.test.skip=true 

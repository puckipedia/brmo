#!/bin/bash

export SQLPATH=./.jenkins
# set up topnl tabellen
ls -l ./brmo-topnl-loader/src/main/resources/nl/b3p/topnl/database/
echo "aanmaken top10nl schema"
sqlplus -l -S top10nl/top10nl@192.168.1.26:15210/XE < ./brmo-topnl-loader/src/main/resources/nl/b3p/topnl/database/oracletop10nl.sql
echo "aanmaken top50nl schema"
sqlplus -l -S top50nl/top50nl@192.168.1.26:15210/XE < ./brmo-topnl-loader/src/main/resources/nl/b3p/topnl/database/oracletop50nl.sql
echo "aanmaken top100nl schema"
sqlplus -l -S top100nl/top100nl@192.168.1.26:15210/XE < ./brmo-topnl-loader/src/main/resources/nl/b3p/topnl/database/oracletop100nl.sql
echo "aanmaken top250nl schema"
sqlplus -l -S top250nl/top250nl@192.168.1.26:15210/XE < ./brmo-topnl-loader/src/main/resources/nl/b3p/topnl/database/oracletop250nl.sql

echo "toebedelen grants aan JENKINS_TOPNL"
sqlplus -l -S top10nl/top10nl@192.168.1.26:15210/XE < ./.jenkins/db-grant-topnl.sql
sqlplus -l -S top50nl/top50nl@192.168.1.26:15210/XE < ./.jenkins/db-grant-topnl.sql
sqlplus -l -S top100nl/top100nl@192.168.1.26:15210/XE < ./.jenkins/db-grant-topnl.sql
sqlplus -l -S top250nl/top250nl@192.168.1.26:15210/XE < ./.jenkins/db-grant-topnl.sql

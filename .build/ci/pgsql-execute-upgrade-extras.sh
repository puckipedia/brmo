#!/usr/bin/env bash
set -e
CURSNAPSHOT=$(grep "<version>.*<.version>" -m1 pom.xml | sed -e "s/^.*<version/<version/" | cut -f2 -d">"| cut -f1 -d"<")

echo "Huidige snapshot:" $CURSNAPSHOT
echo "Verwerk extra upgrade scripts voor: " $1

#if [ $CURSNAPSHOT = "2.0.0-SNAPSHOT" ] && [ $1 = "rsgb" ]
#then
#    psql -U postgres -h localhost -d $1 -f ./datamodel/extra_scripts/postgresql/206_bag_views.sql
#    psql -U postgres -h localhost -d $1 -f ./datamodel/extra_scripts/postgresql/207_brk_views.sql
#fi
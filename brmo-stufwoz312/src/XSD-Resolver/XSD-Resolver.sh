#!/bin/bash
./remove.sh
java -Xmx10g -jar saxon/saxon9he.jar -o:generated/temp.xml -s:input/configuration.xml -xsl:xslt/resolveXSDs.xslt versie=

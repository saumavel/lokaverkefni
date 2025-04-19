#!/bin/bash
mvn package
mvn install dependency:copy-dependencies
chmod +x createjar.sh runjar.sh
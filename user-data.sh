#!/bin/bash
su akkahttp <<EOU
cd /opt/akkahttp
cat >> conf/application.conf <<EOF
org.scalax.reactive.db {
  dataSourceClass = "org.postgresql.ds.PGSimpleDataSource"
  properties = {
    url = "jdbc:postgresql://DBHOST:5432/reactive"
    user = "reactiveapp"
    password = "DBPASS"
  }
}
EOF
cd bin
./akka-http-example &
EOU

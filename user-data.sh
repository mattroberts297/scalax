#!/bin/bash
yum install -y git
curl https://bintray.com/sbt/rpm/rpm | tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum install -y sbt
useradd --create-home --system akkahttp
mkdir -p /opt/akkahttp
chown akkahttp:akkahttp /opt/akkahttp
su akkahttp <<EOF
cd /opt/akkahttp
git clone https://github.com/mattroberts297/scalax.git .
git checkout resilient
sed -i '.orig' \
's/127\.0\.0\.1/CLOUDFORMATIONPARAM"/g'
'src/main/resources/application.conf'
sed -i '.orig' \
's/changeme/CLOUDFORMATIONPARAM"/g'
'src/main/resources/application.conf'
nohup sbt run &
EOF


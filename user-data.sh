sudo su
yum install -y git
curl https://bintray.com/sbt/rpm/rpm | tee /etc/yum.repos.d/bintray-sbt-rpm.repo
yum install -y sbt
useradd --create-home --system akkahttp
mkdir -p /opt/akkahttp
chown akkahttp:akkahttp /opt/akkahttp
su akkahttp
cd /opt/akkahttp
git clone https://github.com/mattroberts297/scalax.git .
git checkout responsive
sbt run
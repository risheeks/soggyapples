eCommerce Platform for B2B and B2C sites

Useful maven commands
---------------------
mvn clean compile
mvn clean test
mvn test -Dtest=TestClass
mvn test -Dtest=TestClass#TestMethod
eg: mvn test -Dtest=XxxServiceImplTest#findByXxx
mvn clean install
mvn clean install -DskipTests

mvn clean tomcat7:deploy

mvn clean checkstyle:check
mvn clean checkstyle:checkstyle

mvn clean findbugs:findbugs

mvn clean pmd:pmd

mvn cobertura:check
mvn cobertura:cobertura

Useful mysql command line options
---------------------------------
mysql> ctrl + L to clear screen
mysql> source ~/projects/b2b2c/database/1.1-createTables.sql
mysql> source ~/projects/b2b2c/database/1.2-InsertUserAndCustomer.sql
mysql> source ~/projects/b2b2c/database/1.3-InsertCategoriesAndProducts.sql

 mysqldump -h [hostname] -P [port] -u [user] -p [database] > [dump.sql];

Useful git commands
-------------------
git clone git@gitlab.com:dabbigodlaECOM/b2b2c.git
git checkout -b <branchname>  origin/master (To create a new branch)
git checkout <branchname> (To switch between branches)
git status
git log
git add file/directory
git commit -am "<message related to the commit>"
git push origin <branchname>
git pull -a
git checkout -- <filename> (To discard changes in a file)
git fetch origin master
git rebase origin/master
git log --oneline --graph --decorate
git show <commitid>
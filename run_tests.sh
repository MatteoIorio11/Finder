#Clone a repository into a new directory
echo "## Cloning a repository for testing ##"
git clone git@github.com:MatteoIorio11/FinderTest.git

mv FinderTest ${HOME}

echo "## Running tests ##"
./gradlew test --parallel

rm -rf ${HOME}/FinderTest

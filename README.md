Link to repository: https://github.com/MatteoIorio11/Finder
# Use the library
## How to use the library
If you want to use the library, you just need to add the jar file (https://github.com/MatteoIorio11/Finder/releases/tag/1.1) as your dependency. In order to do that, you just need to add the dependency
like this (gradle):
```kotlin
dependencies {
    implementation(files("github-difference-finder-1.0-all.jar"))
}
```

after that in order to use the library, you just need to call the function `checkDifference` like this:

```java
import org.iorio.core.diff.CheckDifference;

final List<Map.Entry<AbstractRepositoryFile<Path>,AbstractRepositoryFile<URL>>>differences = CheckDifference.checkDifference(
        "MatteoIorio11", // owner name
        "FinderTest", // repository name
        "GITHUB_TOKEN", // your github token
        "/Users/matteoiorio/FinderTest", // local path where is stored the repository
        "main", // branchA
        "dev" // branchB);
```
The function `checkDifference` will return a list of differences between the two branches, each difference is represented by a pair of files, the first file is the local file and the second file is the remote file.

# Run the application
## Preconditions
Before running any code of this project, you need to do the following steps:
1. Create a '.env' file and copy It inside the 'app' directory, then add the following entry:
   ```
   GITHUB_TOKEN=xxxxxx
   ```
## How to run the tests
If you want to run all the tests of the project, you can use the following command:
   ```
   ./gradlew test
   ```
This gradle task will automatically clone the 'FinderTest' repository, then It will be then moved inside your home directory automatically.
After this, the script will run the tests of the project.

## How to run the application
There are two main ways for running the following code:
1. Running without the GUI use: `./gradlew run`, this will run the application without the GUI, In order to run this code you have to run the `./run_tests.sh` because It will use the FinderTest repository. If you
   want to use another repository, you can change the repository path inside the `App.java` file.
2. Running with the GUI use: `./gradlew runWithGUI`, this will run the application with the GUI. This modality is strongly suggested because It will be easier for you to set all the different parameters of the application.



# Bowling Score System (BSS)

This project is a simple console application for calculating the total score of a traditional ten-pin bowling game.
It will display the board results for the games scores you specify. 

![Bowling](public/img/bowling.jpg)

## Technical considerations
- All invalid inputs are ignored (meaning any input that doesn't match the format `Name Pinfalls`). 
If no valid inputs are provided, an exception is thrown.
- Between the name and the pinfalls number you can have any number of blank spaces or tabs.
- The **F** and **X** are valid values for pinfalls, besides numbers from 0-10.
- Extra inputs, negative values, and/or non-valid pinfalls values are ignored. 
- If you don't specify enough values for 10 frames or if you specify more values than the necessary to complete 10 frames, an exception is thrown.
- The app allows the additional scores in the 10th frame in case of a strike.
- The app is case-sensitive so the scores are group by player name (meaning for example that **John** and **john** would be treated as 2 different players)
## Getting Started

These instructions will get a copy of the codebase up and running on a local machine for development
and testing purposes.

### Prerequisites

- **Java 11**
- **Gradle 7**

It is recommended to install Open JDK as the Java Platform. [SDKMan](https://sdkman.io/install) is
suggested to handle Java versions.

### Code Style and formatting

This project uses the Google Java format, and during the build, it checks all the code looking for
improper formatting and issues. You can check your code by running the command `./gradlew spotlessCheck`,
which will inform you about any inconsistency, or you can apply the recommendations directly by running
the command `./gradlew spotlessApply`. Besides, if you are using IntelliJ, you can install the plugin
**google-java-format**, which will format your code when you press `Code tab -> Reformat code`.

### Installing

To get your local environment running successfully, follow these steps:

1. Clone the repo by running `git clone `
2. Build the app with `./gradlew build`
3. Specify the game scores in the [score.txt](src/main/resources/scores.txt) file (within the resource's folder) in the format  `Name Pinsfalls` (e.g `Max 10`)
4. Start the app running `./gradlew run`

**Note:** If you want to pass a different scores file, you can do it through the var args, like: `gradle run --args fileFolder/score-file.txt`.
The file doesn't need to be within the app classpath.

## Tests

### Unit Testing

Tests are run by the following command: `./gradlew test`

Note: this command was configured to run jacocoTestCoverageVerification and jacocoTestReport, the
reports related to code coverage could be found in the folders *build/reports/jacoco/test/html/index.html*
and *build/reports/tests/test/index.html*. However, you can access those same reports through the
[general reports' page](public/index.html) within the [public](public) folder.

## Contact Information

- Author: Bayron Cardenas
- Email: ingenialex96@gmail.com

## Contributing

### Submitting Changes

- Clone the repository from GitLab
- Create a development branch (e.g. `feat/BSS-002`, `fix/BSS-002`, etc.)
- Make necessary changes on the development branch
- Submit a merge request of the development branch against the main branch.
- Once the merge request is approved, you can merge your development branch into main.

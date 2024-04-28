# Pocolifo Client: Welcome

Source tree for PLC!

## Source tree

`logo/` PLC logos.

`run/` Default working directory for the client.

`src/` Source code

`.gitignore` Git file that ignores files

`.gitlab-ci.yml` Gitlab CI configuration

`README.md` This file

`build.gradle` & `settings.gradle` Gradle build configuration

`gradle.properties.example` Template Gradle properties file that goes in `~/.gradle/`

`gradlew` & `gradlew.bat` Gradle CLI wrapper shell scripts

`upload.sh` CI uses this shell script to upload versions to the API

`gradle/` Gradle wrapper directory

`count-line-numbers.py` Tool to count the number of lines across the project

## Getting Started

1. Copy `gradle.properties.example` to `~/.gradle/gradle.properties` and replace `REPLACE_WITH_YOUR_PERSONAL_ACCESS_TOKEN` with a personal access token obtained from GitLab. The token needs to have `read_api` permissions.
2. Clone this repository
3. Import project into IntelliJ IDEA.
4. Run the `runClient` Gradle task

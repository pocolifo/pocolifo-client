# Pocolifo Client: Welcome

Source tree for PLC!

## Source tree

`other/` Miscellaneous things that may be used in the future.

`obfuscation/` JARs and dictionaries required to obfuscate the client. Will probably be replaced by a Gradle plugin at some point.

`logo/` PLC logos.

`run/` Default working directory for the client.

## Getting Started

1. Clone this repository
2. Import project into IntelliJ IDEA. It may fail because you have not authenticated with the GitLab package registry to download necessary Gradle plugins.
3. Create the file `~/.gradle/gradle.properties` from `.gradle.properties.example` and replace `REPLACE_WITH_YOUR_PERSONAL_ACCESS_TOKEN` with a personal access token obtained from GitLab. The token needs to have `read_api` permissions.
4. Run the `runClient` Gradle task

## IMPORTANT
### For development

Don't use the pipe operator in catch expressions. It messes up obfuscation. In these instances, just catch `Exception`
or `Throwable`.
```java
try {
    // code
} catch (SomeException | AnotherException e) { 
    // code
}
```

### Other
YOU ARE NOT PERMITTED TO PUBLISH THE SOURCE CODE OF POCOLIFO CLIENT UNDER AND CIRCUMSTANCE UNLESS PROVIDED WITH EXPLICIT
WRITTEN CONSENT!
mavenGitflowPipeline {

    useJDK11 = true  // Use JDK 11 for the build

    useBranchNameTag = true  // Include the git branch name in the tag of promoted images

    /*************************************************************************
     * Functional Testing Configuration
     *************************************************************************/

    cucumberReportDirectory = "bip-ratings-svc/target/site"  // Directory containing cucumber reports

    cucumberOpts = "--tags @DEV --tags ~@securitypolicy"  // Additional Maven options for running functional tests

    /* Postman Testing Configuration */

    postmanTestCollections = [
        'bip-ratings-svc/src/inttest/resources/bip-ratings-svc.postman_collection.json'  // Set of Postman collections to execute
    ]

    // Optional configuration for Postman testing:
    // postmanFolder = 'token'  // Specific folder to run tests from within the collection
    // postmanEnvironment = 'bip-ratings-svc/src/inttest/resources/bip-ratings-svc-test.postman_environment.json'  // Environment file for Postman tests
    // postmanGlobals = 'bip-ratings-svc/src/inttest/resources/bip-ratings-svc.postman_globals.json'  // Globals file for Postman tests
    // postmanData = 'bip-ratings-svc/src/inttest/resources/bip-ratings-svc.postman_data.csv'  // Data file for Postman tests
    // postmanIterationCount = 3  // Number of iterations to run Postman tests

    /*************************************************************************
     * OWASP Maven Dependency Check Configuration
     *************************************************************************/

    skipOwasp = true  // Skip OWASP Dependency Check stage

    owaspPlugin = "jenkins"  // Plugin to use for OWASP Dependency Check ("maven" or "jenkins")

    unstableTotalCritical = 1  // Threshold for critical vulnerabilities to mark the build as unstable
    unstableTotalHigh = 1  // Threshold for high vulnerabilities to mark the build as unstable
    unstableTotalMedium = 1  // Threshold for medium vulnerabilities to mark the build as unstable
    unstableTotalLow = 1  // Threshold for low vulnerabilities to mark the build as unstable

    // Optional command line arguments for OWASP Dependency Check
    // owaspArgs = "--suppression ./owasp-suppression.xml"
}

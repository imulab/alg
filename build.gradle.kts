plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(files("libs/algs4.jar"))

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
    testImplementation("com.google.code.gson:gson:2.8.5")
}

application {
    // Define the main class for the application
    mainClassName = "io.imulab.alg.App"
}

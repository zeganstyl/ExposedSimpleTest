plugins {
    kotlin("jvm") version "1.5.30"
}

group = "com.miaomiaomiao.test.exposed-simple-test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.exposed:exposed-core:0.32.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.32.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.32.1")
    implementation("com.h2database:h2:1.4.200")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.hibernate:hibernate-core:5.5.6.Final")

//    val log4jVer = "2.14.1"
//    implementation("org.apache.logging.log4j:log4j-api:$log4jVer")
//    implementation("org.apache.logging.log4j:log4j-core:$log4jVer")

    implementation("com.zaxxer:HikariCP:4.0.2")
}
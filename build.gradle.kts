plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.15.0"
}

group = "org.abstruck.mirai"
version = "0.1.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

mirai {
    jvmTarget = JavaVersion.VERSION_1_8
}

dependencies {
    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    implementation("com.mysql:mysql-connector-j:8.1.0")
}

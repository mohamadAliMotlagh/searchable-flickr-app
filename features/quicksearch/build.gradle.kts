plugins {
    id("common.android-library")
}
android{
    namespace = "com.motlagh.features.quicksearch"
}

dependencies {
    implementation(project(":core"))
    room()
    hilt()
}
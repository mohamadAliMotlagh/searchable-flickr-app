plugins {
    id("common.android-library")
}
android{
    namespace = "com.motlagh.features.quicksearch"
}

dependencies {
//    implementation(project(":uikit"))
    implementation(project(":core"))
    room()
    hilt()
}
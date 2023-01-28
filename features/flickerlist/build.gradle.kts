plugins {
    id("common.android-library")
}
android{
    namespace = "com.exmaple.features.flickerlist"
}

dependencies {
    implementation(project(":uikit"))
    implementation(project(":core"))
    implementation(project(":features:imageviewer"))
    hilt()
    implementation(Dependencies.COIL)
}
plugins {
    id("common.android-library")
}
android{
    namespace = "com.exmaple.features.imageviewer"
}

dependencies {
    implementation(project(":uikit"))
    implementation(project(":core"))
    hilt()
    implementation(Dependencies.COIL)
}
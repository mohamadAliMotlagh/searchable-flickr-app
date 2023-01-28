plugins {
    id("common.android-library")
}
android{
    namespace = "com.exmaple.features.flickerlist"
}

dependencies {
    implementation(project(":uikit"))
    implementation(project(":core"))
    hilt()
    implementation(Dependencies.COIL)
}
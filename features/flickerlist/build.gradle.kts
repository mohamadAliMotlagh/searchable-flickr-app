plugins {
    id("common.android-library")
}
android{
    namespace = "com.exmaple.features.flickerlist"
}

dependencies {
    implementation(project(":uikit"))
}
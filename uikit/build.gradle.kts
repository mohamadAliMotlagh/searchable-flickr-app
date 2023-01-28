plugins {
    id("common.android-library")
}
android{
    namespace = "com.exmaple.uikit"
}

dependencies {
    appCompat()
    compose()
    hilt()
    addTestsDependencies()
}
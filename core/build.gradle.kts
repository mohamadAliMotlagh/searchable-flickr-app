plugins {
    id("common.android-library")
}
android{
    namespace = "com.motlagh.core"
}

dependencies {
    hilt()
    retrofitAPI()
    addTestsDependencies()
}
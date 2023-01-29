import AnnotationProcessorsDependencies.HILT
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.jetPackApi(){
    api(Dependencies.LIFECYCLE_VIEWMODEL)
    api(Dependencies.LIFECYCLE_LIVEDATA)
}

fun DependencyHandler.appCompat() {
    api(Dependencies.APPCOMPAT)
    api(Dependencies.CORE_KTX)
}

fun DependencyHandler.compose() {
    api(Dependencies.COMPOSE_MATERIAL)
    api(Dependencies.COMPOSE_UI)
    api(Dependencies.COMPOSE_NAVIGATION)
    api(Dependencies.COMPOSE_PREVIEW)
    api(Dependencies.COMPOSE_LIVE_DATA)
    debugImplementation(Dependencies.COMPOSE_UI_TOOLING)
    debugImplementation(Dependencies.COMPOSE_TEST_MANIFEST)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.HILT)
    implementation(Dependencies.HILT_VIEWMODEL)
    // api ("androidx.hilt:hilt-lifecycle-viewmodel:$DAGGER_VIEWMODEL")
    kapt("androidx.hilt:hilt-compiler:${Versions.DAGGER_VIEWMODEL}")
    kapt(AnnotationProcessorsDependencies.HILT)
}

fun DependencyHandler.room() {
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_RUNTIME)
    kapt(Dependencies.ROOM_COMPILER)
}

fun DependencyHandler.retrofitAPI() {
    api(Dependencies.RETROFIT)
    api(Dependencies.GSON)
    api(Dependencies.LOGGING)
    api(Dependencies.OK_HTTP)
}

fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

fun DependencyHandler.ksp(depName: String) {
    add("ksp", depName)
}

private fun DependencyHandler.compileOnly(depName: String) {
    add("compileOnly", depName)
}

private fun DependencyHandler.api(depName: String) {
    add("api", depName)
}

fun DependencyHandler.debugImplementation(dependencyNotation: String) =
    add("debugImplementation", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: String) =
    add("testImplementation", dependencyNotation)
/**
 * Adds all the tests dependencies to specific configuration.
 */

object TestAndroidDependencies {
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
    const val RUNNER = "androidx.test:runner:${Versions.TEST}"
    const val RULES = "androidx.test:rules:${Versions.TEST}"
    const val JUNIT = "androidx.test.ext:junit:${Versions.EXT}"
    const val RX_IDLER  = "com.squareup.rx.idler:rx2-idler:${Versions.RX_IDLER}"
}
object TestDependencies {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val MOCKITO = "org.mockito:mockito-core:${Versions.MOCKITO}"
    const val EXT = "androidx.test.ext:junit:${Versions.EXT}"
    const val CORE = "androidx.test:core:${Versions.TEST}"
    const val RUNNER = "androidx.test:runner:${Versions.TEST}"
    const val RULES = "androidx.test:rules:${Versions.TEST}"
    const val ARCH_CORE = "androidx.arch.core:core-testing:${Versions.ARCH_CORE}"
}
fun DependencyHandler.addTestsDependencies() {
    testImplementation(TestDependencies.JUNIT)
    testImplementation(TestDependencies.MOCKITO)
    testImplementation(TestDependencies.CORE)
    testImplementation(TestDependencies.ARCH_CORE)
    testImplementation(TestDependencies.RULES)
    testImplementation(TestDependencies.RUNNER)
    testImplementation(TestDependencies.EXT)
}

/**
 * Adds all the Android tests dependencies to specific configuration.
 */
fun DependencyHandler.androidTestImplementation(dependencyNotation: String) =
    add("androidTestImplementation", dependencyNotation)


fun DependencyHandler.addAndroidTestsDependencies() {
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_CORE)
    androidTestImplementation(TestAndroidDependencies.ESPRESSO_CONTRIB)
    androidTestImplementation(TestAndroidDependencies.RUNNER)
    androidTestImplementation(TestAndroidDependencies.RULES)
    androidTestImplementation(TestAndroidDependencies.JUNIT)
    androidTestImplementation(TestAndroidDependencies.RX_IDLER)
}
## Implementation Steps

~~1. Understand the project specification.~~.  
~~2. Understand the project API.~~.  
~~3. Fork project from Github.~~.  
~~4. Create STEPS.md.~~  
~~5. Update Gradle libraries imports on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.1. Apply plugin `kotlin-kapt` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.2. Apply plugin `androidx.navigation.safeargs` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.3. Apply classpath `android.arch.navigation:navigation-safe-args-gradle-plugin` on `build.gradle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.4. Add `dataBinding.enabled=true` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.5. Add support for Java 8 on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.6. Update `minSdkVersion` to 16 on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.7. Add `Constraint Layout` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.8. Add `ViewModel` and `LiveData` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.9. Add `ViewModelScope for Coroutines` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.10. Add `Navigation` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.11. Add `Core with Ktx` on `app/build.gragle`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.11.1. This library is used to call `val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.11.2. It's import is `import androidx.core.net.toUri`.~~  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~~5.12. Add `Retrofit` on `app/build.gragle`.~~  
~~6. Create Gradle libraries versions in tag "ext" of `build.gradle`.~~  
7. Create packages `home` and `result`.  
8. Update files `res/values/styles.xml` and `res/values/colors.xml`.  
9. Create `BindingAdapter` with `bindStatus` method.  
10. Create `HomeActivity.kt`, `HomeViewModel.kt` and `HomeFragment.kt`.  
11. Create `ResultActivity.kt`, `ResultViewModel.kt` and `ResultFragment.kt`.  
12. Create layouts for `fragment_home.xml` and `result_home.xml`, both using `ConstraintLayout`.  
13. Create `Navigation` path.  
14. Update `AndroidManifest` with INTERNET permission.  
15. Create package `network` for the app.  
16. Create `EasynvestService.kt` on app.  
17. Create images on `drawable` folder.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;17.1. File `loading_img.xml`.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;17.2. File `ic_connection_error.xml`.  
18. Test app on smaller devices.  
19. Create `Unit Tests` with `JUnit`.  
20. Create `Instrumented Tests` with `Espresso`.  

## Implementation Details

1. This app was written having `Kotlin` language as main language.
2. The architecture applied here was `Model-View-ViewModel` with `Data Binding`.
3. The screen transitions of the app uses the `Navigation` pattern.
4. The package structure of this app was created by functionalities: Home, Result and Network.
5. Since this app is small and contains only 2 screens, the use of `Dagger 2` library was considered unnecessary.
6. This app contains Unit and Instrumented Tests.

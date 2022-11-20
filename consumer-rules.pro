-keepclassmembers class ** implements com.hack.opensdk.IPackageObserver$Install {
    public void onPackageInstalled(...);
}

-keepclassmembers class ** implements com.hack.opensdk.IPackageObserver$Delete {
    public void onPackageDeleted(...);
}
package dev.george.androidtask.base.fragment

object ActivityFragmentAnnoationManager {
    fun check(clazz: Any): Int {
        val annotationPresent = clazz.javaClass.isAnnotationPresent(
            ActivityFragmentAnnoation::class.java
        )
        check(annotationPresent) { "Activity" }
        val annotation = clazz.javaClass.getAnnotation(
            ActivityFragmentAnnoation::class.java
        )
        return annotation!!.contentId
    }
}
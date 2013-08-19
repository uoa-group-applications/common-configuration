package net.stickycode.stereotype;

/**
 * author: Richard Vowles - http://gplus.to/RichardVowles
 *
 * This is a cheat to allow us to support the "old" @Configured element. without breaking anything.
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
public @interface Configured {
  /**
   * If provided this is the key we look up in the configuration source
   *
   * @return key used for configuration source
   */
  String value() default "";
}

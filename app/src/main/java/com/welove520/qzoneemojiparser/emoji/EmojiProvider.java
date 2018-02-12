package com.welove520.qzoneemojiparser.emoji;

import android.support.annotation.NonNull;

/**
 * Interface for a custom emoji implementation that can be used with {@link EmojiManager}
 *
 * @since 0.4.0
 */
public interface EmojiProvider {
  /**
   * Returns an array of categories.
   *
   * @return The Array of categories.
   * @since 0.4.0
   */
  @NonNull
  EmojiCategory[] getCategories();
}

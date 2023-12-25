package com.longtv.zappy.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Constants {
    String[] category = new String[] {"Music", "Story", "Movie", "Learning"};
    String TOOL_BAR = "tool_bar";
    String DATA = "data";
    String FROM_HOME = "from_home";

    List<String> topFragment = Arrays.asList("HomeBoxFragment", "HomeBoxFilmFragment", "HomeBoxMusicFragment", "HomeBoxStoryFragment");
    String APP_LINK = "https://facebook.com/";
    String IS_NOTIFICATION = "is_notification";
}

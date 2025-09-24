// 代码生成时间: 2025-09-24 17:09:22
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import views.html;
import models.UserSettings;

/**
 * Controller responsible for switching themes.
 */
public class ThemeSwitcher extends Controller {

    /**
     * Method to switch the theme based on the user's request.
     *
     * @param themeName The name of the theme to switch to.
     * @return A redirect to the previous page with the new theme applied.
     */
    public Result switchTheme(String themeName) {
        // Error handling for invalid theme names
        if (themeName == null || themeName.isEmpty()) {
            return badRequest("Theme name cannot be empty");
        }

        // Check if the theme is supported
        if (!isSupportedTheme(themeName)) {
            return badRequest("Theme not supported");
        }

        // Save the theme preference to the user settings
        try {
            UserSettings.setThemePreference(themeName);
        } catch (Exception e) {
            return internalServerError("Error saving theme preference");
        }

        // Redirect back to the previous page with a flash message
        flash("success", "Theme switched to: " + themeName);
        return redirect(Http.Context.current().request().header("Referer").get());
    }

    /**
     * Helper method to check if a theme is supported.
     *
     * @param themeName The name of the theme to check.
     * @return True if the theme is supported, false otherwise.
     */
    private boolean isSupportedTheme(String themeName) {
        // List of supported themes
        String[] supportedThemes = {"light", "dark", "colorful"};
        for (String theme : supportedThemes) {
            if (theme.equals(themeName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

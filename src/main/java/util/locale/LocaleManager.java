package util.locale;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {

    public LocaleManager() {
        this.resourceBundle = ResourceBundle.getBundle(resourceName, new Locale("en", "EN"));
    }

    private ResourceBundle resourceBundle;
    private final String resourceName = "text";

    public void changeLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(resourceName, locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}

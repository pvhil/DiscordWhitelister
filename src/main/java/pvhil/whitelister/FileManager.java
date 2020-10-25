package pvhil.whitelister;

public class FileManager {
    private static FileWriter fileWriter = new FileWriter(main.getTutorial().getDataFolder().getPath(), "config.yml");

    public static void loadFile() {
        setValue("settings.discordkey", "BOTTOKEN");
        setValue("//languages: de and en, Type custom in lang if your want to use custom text", "//");
        setValue("lang", "en");
        setValue("errorDc","custom discord error(your minecraft name is missing)");
        setValue("titleDc","custom title");
        setValue("descDc","custom description");

    }

    private static void setValue(final String valuePath, final String value){
        if(!fileWriter.valueExist(valuePath)) {
            fileWriter.setValue(valuePath, value);
            fileWriter.save();

        }
    }

    public static Object getValue(final String valuePath){
        return fileWriter.getObject(valuePath);
    }
}

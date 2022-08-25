package tech.settler.reactgenerator;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ReactGeneratorConfigurable implements Configurable {
    private ReactGeneratorSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "React Generator Settings";
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingsComponent = new ReactGeneratorSettingsComponent();
        return settingsComponent.getMainPanel();
    }

    @Override
    public boolean isModified() {
        ReactGeneratorSettingsState settings = ReactGeneratorSettingsState.getInstance();
        boolean modified = settingsComponent.getUseTypeScript() != settings.useTypeScript;
        modified |= settingsComponent.getGenerateIndexFile() != settings.generateIndexFile;
        modified |= settingsComponent.getGenerateTypesFile() != settings.generateTypesFile;
        modified |= settingsComponent.getGenerateHookFile() != settings.generateHookFile;
        modified |= settingsComponent.getGenerateStyledFile() != settings.generateStyledFile;
        modified |= settingsComponent.getGenerateSagaFile() != settings.generateSagaFile;

        return modified;
    }

    @Override
    public void apply() {
        ReactGeneratorSettingsState settings = ReactGeneratorSettingsState.getInstance();
        settings.useTypeScript = settingsComponent.getUseTypeScript();
        settings.generateIndexFile = settingsComponent.getGenerateIndexFile();
        settings.generateTypesFile = settingsComponent.getGenerateTypesFile();
        settings.generateHookFile = settingsComponent.getGenerateHookFile();
        settings.generateStyledFile = settingsComponent.getGenerateStyledFile();
        settings.generateSagaFile = settingsComponent.getGenerateSagaFile();
    }

    @Override
    public void reset() {
        ReactGeneratorSettingsState settings = ReactGeneratorSettingsState.getInstance();
        settingsComponent.setUseTypeScript(settings.useTypeScript);
        settingsComponent.setGenerateIndexFile(settings.generateIndexFile);
        settingsComponent.setGenerateTypesFile(settings.generateTypesFile);
        settingsComponent.setGenerateHookFile(settings.generateHookFile);
        settingsComponent.setGenerateStyledFile(settings.generateStyledFile);
        settingsComponent.setGenerateSagaFile(settings.generateSagaFile);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}

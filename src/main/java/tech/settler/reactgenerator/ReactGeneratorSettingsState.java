package tech.settler.reactgenerator;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "tech.settler.react-generator.ReactComponentsSettingsState",
        storages = @Storage("reactComponentsSettings.xml")
)
public class ReactGeneratorSettingsState implements PersistentStateComponent<ReactGeneratorSettingsState> {
    public boolean useTypeScript = false;

    public boolean generateIndexFile = false;
    public boolean generateTypesFile = false;
    public boolean generateHookFile = false;
    public boolean generateStyledFile = false;

    public boolean generateSagaFile = false;

    public static ReactGeneratorSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ReactGeneratorSettingsState.class);
    }

    @Override
    @Nullable
    public ReactGeneratorSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ReactGeneratorSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}

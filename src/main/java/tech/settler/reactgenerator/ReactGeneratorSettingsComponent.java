package tech.settler.reactgenerator;

import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class ReactGeneratorSettingsComponent {
    private final JPanel mainPanel;

    private final JBCheckBox useTypeScriptCheckBox = new JBCheckBox();

    private final JBCheckBox generateIndexFileCheckBox = new JBCheckBox();
    private final JBCheckBox generateTypesFileCheckBox = new JBCheckBox();
    private final JBCheckBox generateHookFileCheckBox = new JBCheckBox();
    private final JBCheckBox generateStyledFileCheckBox = new JBCheckBox();

    public ReactGeneratorSettingsComponent() {
        int verticalGap = 5;

        //noinspection DialogTitleCapitalization
        JPanel settingsPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Use TypeScript", useTypeScriptCheckBox, verticalGap, false).getPanel();
        settingsPanel.setBorder(IdeBorderFactory.createTitledBorder("Settings"));

        JPanel generationPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Generate index file"), generateIndexFileCheckBox, verticalGap, false)
                .addLabeledComponent(new JBLabel("Generate types file"), generateTypesFileCheckBox, verticalGap, false)
                .addLabeledComponent(new JBLabel("Generate hook file"), generateHookFileCheckBox, verticalGap, false)
                .addLabeledComponent(new JBLabel("Generate styled file"), generateStyledFileCheckBox, verticalGap, false)
                .getPanel();
        generationPanel.setBorder(IdeBorderFactory.createTitledBorder("Generation"));

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(settingsPanel)
                .addComponent(generationPanel)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public boolean getUseTypeScript() {
        return useTypeScriptCheckBox.isSelected();
    }

    public void setUseTypeScript(boolean newValue) {
        useTypeScriptCheckBox.setSelected(newValue);
    }

    public boolean getGenerateIndexFile() {
        return generateIndexFileCheckBox.isSelected();
    }

    public void setGenerateIndexFile(boolean newValue) {
        generateIndexFileCheckBox.setSelected(newValue);
    }

    public boolean getGenerateTypesFile() {
        return generateTypesFileCheckBox.isSelected();
    }

    public void setGenerateTypesFile(boolean newValue) {
        generateTypesFileCheckBox.setSelected(newValue);
    }

    public boolean getGenerateHookFile() {
        return generateHookFileCheckBox.isSelected();
    }

    public void setGenerateHookFile(boolean newValue) {
        generateHookFileCheckBox.setSelected(newValue);
    }

    public boolean getGenerateStyledFile() {
        return generateStyledFileCheckBox.isSelected();
    }

    public void setGenerateStyledFile(boolean newValue) {
        generateStyledFileCheckBox.setSelected(newValue);
    }
}

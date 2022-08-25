package tech.settler.reactgenerator;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.ComponentValidator;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;

public class ReactGeneratorSettingsComponent {
    private final JPanel mainPanel;

    private final JBCheckBox useTypeScriptCheckBox = new JBCheckBox();
    private final JBCheckBox generateIndexFileCheckBox = new JBCheckBox();
    private final JBCheckBox generateTypesFileCheckBox = new JBCheckBox();
    private final JBCheckBox generateHookFileCheckBox = new JBCheckBox();
    private final JBCheckBox generateStyledFileCheckBox = new JBCheckBox();

    private final JBCheckBox generateSagaFileCheckBox = new JBCheckBox();

    private final TextFieldWithBrowseButton storePathField = new TextFieldWithBrowseButton();

    private final JBTextField storeClassNameField = new JBTextField();

    public ReactGeneratorSettingsComponent() {
        int verticalGap = 5;

        new ComponentValidator(storePathField).withValidator(() -> {
            String storePath = storePathField.getText();
            if (StringUtil.isNotEmpty(storePath)) {
                storeClassNameField.setEnabled(true);

                if (StringUtil.isEmpty(storeClassNameField.getText()))
                    return new ValidationInfo("Store class name must be set", storeClassNameField);
                else
                    return null;
            }

            storeClassNameField.setEnabled(false);
            storeClassNameField.setText("");
            return null;
        }).installOn(storeClassNameField);

        storePathField.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileDescriptor()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
                if (!storePathField.getText().isEmpty()) storeClassNameField.setEnabled(true);
            }
        });
        storePathField.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(storeClassNameField).ifPresent(ComponentValidator::revalidate);
            }
        });
        storeClassNameField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                ComponentValidator.getInstance(storeClassNameField).ifPresent(ComponentValidator::revalidate);
            }
        });
        storeClassNameField.setEnabled(!storePathField.getText().isEmpty());

        //noinspection DialogTitleCapitalization
        JPanel settingsPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Use TypeScript", useTypeScriptCheckBox, verticalGap, false).getPanel();
        settingsPanel.setBorder(IdeBorderFactory.createTitledBorder("Settings"));

        JPanel componentsPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Generate index file"), generateIndexFileCheckBox, verticalGap, false)
                .addLabeledComponent(new JBLabel("Generate types file"), generateTypesFileCheckBox, verticalGap, false)
                .addLabeledComponent(new JBLabel("Generate hook file"), generateHookFileCheckBox, verticalGap, false)
                .addLabeledComponent(new JBLabel("Generate styled file"), generateStyledFileCheckBox, verticalGap, false)
                .getPanel();
        componentsPanel.setBorder(IdeBorderFactory.createTitledBorder("Components"));

        JPanel reduxPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Generate saga file"), generateSagaFileCheckBox, verticalGap, false)
                .addLabeledComponent(new JBLabel("Store path"), storePathField, verticalGap, false)
                .addLabeledComponent(new JBLabel("Store class name"), storeClassNameField, verticalGap, false)
                .getPanel();
        reduxPanel.setBorder(IdeBorderFactory.createTitledBorder("Redux"));

        mainPanel = FormBuilder.createFormBuilder()
                .addComponent(settingsPanel)
                .addComponent(componentsPanel)
                .addComponent(reduxPanel)
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

    public boolean getGenerateSagaFile() {
        return generateSagaFileCheckBox.isSelected();
    }

    public void setGenerateSagaFile(boolean newValue) {
        generateSagaFileCheckBox.setSelected(newValue);
    }

    public String getStorePath() {
        return storePathField.getText();
    }

    public void setStorePath(String newValue) {
        storePathField.setText(newValue);
    }

    public String getStoreClassName() {
        return storeClassNameField.getText();
    }

    public void setStoreClassName(String newValue) {
        storeClassNameField.setText(newValue);
    }
}

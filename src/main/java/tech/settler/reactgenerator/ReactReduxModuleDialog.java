package tech.settler.reactgenerator;

import com.intellij.openapi.ui.DialogWrapper;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ReactReduxModuleDialog extends DialogWrapper {
    public JTextField moduleNameField;
    public JCheckBox useTypescriptCheckBox;
    public JCheckBox generateSagaFileCheckBox;

    public ReactReduxModuleDialog() {
        super(true);
        setTitle("Create React Redux Module");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        ReactGeneratorSettingsState settings = ReactGeneratorSettingsState.getInstance();

        JPanel dialogPanel = new JPanel(new VerticalLayout());

        JLabel label = new JLabel("Enter a name for the Redux module");
        label.setPreferredSize(new Dimension(300, 30));
        dialogPanel.add(label);

        moduleNameField = new JTextField();
        moduleNameField.setPreferredSize(new Dimension(300, 30));
        dialogPanel.add(moduleNameField);

        useTypescriptCheckBox = new JCheckBox("Use TypeScript", settings.useTypeScript);
        generateSagaFileCheckBox = new JCheckBox("Create Saga", settings.generateSagaFile);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setPreferredSize(new Dimension(300, 30));
        checkBoxPanel.add(useTypescriptCheckBox);
        checkBoxPanel.add(generateSagaFileCheckBox);
        dialogPanel.add(checkBoxPanel);

        return dialogPanel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return moduleNameField;
    }
}

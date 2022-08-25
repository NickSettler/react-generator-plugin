package tech.settler.reactgenerator;

import com.intellij.openapi.ui.DialogWrapper;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ReactComponentNameDialog extends DialogWrapper {
    public JTextField componentNameField;
    public JCheckBox useTypescriptCheckBox;
    public JCheckBox generateIndexFileCheckBox;
    public JCheckBox generateTypesFileCheckBox;
    public JCheckBox generateHookFileCheckBox;
    public JCheckBox generateStyledFileCheckBox;

    public ReactComponentNameDialog() {
        super(true);
        setTitle("Create React Component");
        init();
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        ReactGeneratorSettingsState settings = ReactGeneratorSettingsState.getInstance();

        JPanel dialogPanel = new JPanel(new VerticalLayout());

        JLabel label = new JLabel("Enter a name for the React Component");
        label.setPreferredSize(new Dimension(300, 30));
        dialogPanel.add(label);

        componentNameField = new JTextField();
        componentNameField.setPreferredSize(new Dimension(300, 30));
        dialogPanel.add(componentNameField);

        useTypescriptCheckBox = new JCheckBox("Use TypeScript", settings.useTypeScript);
        generateIndexFileCheckBox = new JCheckBox("Generate index file", settings.generateIndexFile);
        generateTypesFileCheckBox = new JCheckBox("Generate types file", settings.generateTypesFile);
        generateHookFileCheckBox = new JCheckBox("Generate hook file", settings.generateHookFile);
        generateStyledFileCheckBox = new JCheckBox("Generate styled file", settings.generateStyledFile);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setPreferredSize(new Dimension(300, 100));
        checkBoxPanel.add(useTypescriptCheckBox);
        checkBoxPanel.add(generateIndexFileCheckBox);
        checkBoxPanel.add(generateTypesFileCheckBox);
        checkBoxPanel.add(generateHookFileCheckBox);
        checkBoxPanel.add(generateStyledFileCheckBox);
        dialogPanel.add(checkBoxPanel);

        return dialogPanel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return componentNameField;
    }
}

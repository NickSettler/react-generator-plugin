package tech.settler.reactgenerator;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class CreateReactComponentAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        boolean hasProject = project != null;

        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        if (file == null) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }

        boolean fileIsADirectory = file.isDirectory();

        boolean enabled = hasProject && fileIsADirectory;

        e.getPresentation().setEnabledAndVisible(enabled);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        assert project != null;

        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        assert file != null;

        ReactGeneratorSettingsState settings = ReactGeneratorSettingsState.getInstance();

        ReactComponentNameDialog dialog = new ReactComponentNameDialog();
        if (dialog.showAndGet()) {
            String componentName = dialog.componentNameField.getText().trim();
            boolean useTypescript = dialog.useTypescriptCheckBox.isSelected();
            boolean generateIndexFile = dialog.generateIndexFileCheckBox.isSelected();
            boolean generateTypesFile = dialog.generateTypesFileCheckBox.isSelected();
            boolean generateHookFile = dialog.generateHookFileCheckBox.isSelected();
            boolean generateStyledFile = dialog.generateStyledFileCheckBox.isSelected();

            if (componentName.isEmpty()) return;

            if (componentName.matches("^\\d.*$")) {
                Messages.showErrorDialog("Component name should not start with a number", "Error");
                return;
            }

            String fileExtension = useTypescript ? ".ts" : ".js";
            String componentExtension = fileExtension + "x";

            String directoryName = file.getName();

            AtomicReference<VirtualFile> finalFile = new AtomicReference<>(file);

            if (!componentName.equals(directoryName))
                ApplicationManager.getApplication().runWriteAction(() -> {
                    VirtualFile newDirectory;
                    try {
                        newDirectory = file.createChildDirectory(this, componentName);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    finalFile.set(newDirectory);
                });

            String componentTypesFileName = componentName + ".types";
            String componentStyledFileName = componentName + ".styled";
            String componentHookFileName = "use" + componentName;

            String componentHookName = "use" + componentName;
            String componentHookTypeName = "T" + componentName + "Hook";

            String componentPropsName = "T" + componentName + "Props";
            String componentStatePropsName = "T" + componentName + "StateProps";
            String componentDispatchPropsName = "T" + componentName + "DispatchProps";

            Properties properties = new Properties();
            if (useTypescript) properties.setProperty("IS_TS", "true");
            if (generateTypesFile) properties.setProperty("WITH_TYPES", "true");
            if (StringUtil.isNotEmpty(settings.storePath)) {
                Path storePath = Paths.get(settings.storePath);
                Path currentPath = Paths.get(finalFile.get().getPath());
                Path pathRelative = currentPath.relativize(storePath);
                properties.setProperty("STORE_PATH", pathRelative.toString());
            }
            if (StringUtil.isNotEmpty(settings.storeClassName))
                properties.setProperty("STORE_CLASS_NAME", settings.storeClassName);

            properties.setProperty("COMPONENT_NAME", componentName);

            properties.setProperty("COMPONENT_FILE_NAME", componentName);
            properties.setProperty("COMPONENT_TYPES_FILE_NAME", componentTypesFileName);
            properties.setProperty("COMPONENT_STYLED_FILE_NAME", componentStyledFileName);
            properties.setProperty("COMPONENT_HOOK_FILE_NAME", componentHookFileName);

            properties.setProperty("COMPONENT_HOOK_NAME", componentHookName);
            properties.setProperty("COMPONENT_HOOK_TYPE_NAME", componentHookTypeName);

            properties.setProperty("COMPONENT_PROPS_NAME", componentPropsName);
            properties.setProperty("COMPONENT_STATE_PROPS_NAME", componentStatePropsName);
            properties.setProperty("COMPONENT_DISPATCH_PROPS_NAME", componentDispatchPropsName);

            FileTemplate componentTemplate = FileTemplateManager.getInstance(project)
                    .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.COMPONENT_TEMPLATE);

            ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), componentName + componentExtension, componentTemplate, properties));

            if (generateIndexFile) {
                FileTemplate indexTemplate = FileTemplateManager.getInstance(project)
                        .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.COMPONENT_INDEX);
                ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "index" + fileExtension, indexTemplate, properties));
            }

            if (useTypescript && generateTypesFile) {
                FileTemplate typesTemplate = FileTemplateManager.getInstance(project)
                        .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.COMPONENT_TYPES);
                ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), componentTypesFileName + fileExtension, typesTemplate, properties));
            }

            if (generateHookFile) {
                FileTemplate hookTemplate = FileTemplateManager.getInstance(project)
                        .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.COMPONENT_HOOK);
                ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), componentHookFileName + fileExtension, hookTemplate, properties));
            }

            if (generateStyledFile) {
                ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), componentStyledFileName + fileExtension));
            }
        }
    }
}

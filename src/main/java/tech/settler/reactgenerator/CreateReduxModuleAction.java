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
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class CreateReduxModuleAction extends AnAction {
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

        ReactReduxModuleDialog dialog = new ReactReduxModuleDialog();
        if (dialog.showAndGet()) {
            String moduleName = dialog.moduleNameField.getText().trim();
            boolean useTypescript = dialog.useTypescriptCheckBox.isSelected();
            boolean generateSagaFile = dialog.generateSagaFileCheckBox.isSelected();

            if (moduleName.isEmpty()) return;

            if (moduleName.matches("^\\d.*$")) {
                Messages.showErrorDialog("Module name should not start with a number", "Error");
                return;
            }

            String directoryName = file.getName();

            AtomicReference<VirtualFile> finalFile = new AtomicReference<>(file);

            if (!moduleName.equals(directoryName))
                ApplicationManager.getApplication().runWriteAction(() -> {
                    VirtualFile newDirectory;
                    try {
                        newDirectory = file.createChildDirectory(this, moduleName);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    finalFile.set(newDirectory);
                });

            String moduleSchemaType = "T" + StringUtils.capitalize(moduleName) + "Schema";

            Properties properties = new Properties();
            if (useTypescript) properties.setProperty("IS_TS", "true");
            if (generateSagaFile) properties.setProperty("GENERATE_SAGA_FILE", "true");
            if (StringUtil.isNotEmpty(settings.storePath)) {
                Path storePath = Paths.get(settings.storePath);
                Path currentPath = Paths.get(finalFile.get().getPath());
                Path pathRelative = currentPath.relativize(storePath);
                properties.setProperty("STORE_PATH", pathRelative.toString());
            }
            if (StringUtil.isNotEmpty(settings.storeClassName))
                properties.setProperty("STORE_CLASS_NAME", settings.storeClassName);

            properties.setProperty("MODULE_NAME", moduleName);
            properties.setProperty("MODULE_SCHEMA_TYPE", moduleSchemaType);

            FileTemplate indexTemplate = FileTemplateManager.getInstance(project)
                    .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_INDEX);
            ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "index.ts", indexTemplate, properties));

            FileTemplate moduleTemplate = FileTemplateManager.getInstance(project)
                    .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_MODULE);
            ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "module.ts", moduleTemplate, properties));

            if (useTypescript) {
                FileTemplate typesTemplate = FileTemplateManager.getInstance(project)
                        .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_TYPES);
                ApplicationManager.getApplication().runWriteAction(() -> {
                    try {
                        finalFile.get().createChildDirectory(this, "types");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get().findChild("types"), moduleName + ".types.ts", typesTemplate, properties));
            }

            FileTemplate reducerTemplate = FileTemplateManager.getInstance(project)
                    .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_REDUCER);
            ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "reducer.ts", reducerTemplate, properties));

            FileTemplate actionsTemplate = FileTemplateManager.getInstance(project)
                    .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_ACTIONS);
            ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "actions.ts", actionsTemplate, properties));

            FileTemplate selectorsTemplate = FileTemplateManager.getInstance(project)
                    .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_SELECTORS);
            ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "selectors.ts", selectorsTemplate, properties));

            FileTemplate schemaTemplate = FileTemplateManager.getInstance(project)
                    .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_SCHEMA);
            ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "schema.ts", schemaTemplate, properties));

            if (generateSagaFile) {
                FileTemplate sagaTemplate = FileTemplateManager.getInstance(project)
                        .getJ2eeTemplate(ReactComponentsFileTemplateGroupFactory.REDUX_MODULE_SAGA);
                ApplicationManager.getApplication().runWriteAction(FileProcessor.createFile(project, finalFile.get(), "saga.ts", sagaTemplate, properties));
            }
        }
    }
}

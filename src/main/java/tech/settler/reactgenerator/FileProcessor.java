package tech.settler.reactgenerator;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.util.Properties;

public class FileProcessor {
    public static Runnable createFile(Project project, VirtualFile file, String filename) {
        return () -> {
            try {
                file.createChildData(null, filename);
            } catch (IOException ex) {
                Messages.showErrorDialog(project, "Could not create file " + filename, "Error");
            }
        };
    }

    public static Runnable createFile(Project project, VirtualFile file, String filename, FileTemplate template, Properties properties) {
        return () -> {
            String renderedTemplate;

            try {
                renderedTemplate = template.getText(properties);
            } catch (IOException e1) {
                Messages.showErrorDialog(project, "Could not render template", "Error");
                return;
            }
            try {
                VirtualFile componentFile = file.createChildData(null, filename);
                componentFile.setBinaryContent(renderedTemplate.getBytes());
            } catch (IOException ex) {
                Messages.showErrorDialog(project, "Could not create file " + filename, "Error");
            }
        };
    }
}

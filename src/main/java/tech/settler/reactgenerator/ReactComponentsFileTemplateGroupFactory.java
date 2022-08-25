package tech.settler.reactgenerator;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

import static icons.ReactGeneratorIcons.PluginIcon;

public class ReactComponentsFileTemplateGroupFactory implements FileTemplateGroupDescriptorFactory {
    public static final String COMPONENT_TEMPLATE = "React Components Component.tsx";
    public static final String COMPONENT_INDEX = "React Components Index.ts";
    public static final String COMPONENT_HOOK = "React Components Hook.ts";
    public static final String COMPONENT_TYPES = "React Components Types.ts";
    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("React Generator", PluginIcon);

        group.addTemplate(new FileTemplateDescriptor(COMPONENT_TEMPLATE, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(COMPONENT_HOOK, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(COMPONENT_INDEX, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(COMPONENT_TYPES, PluginIcon));

        return group;
    }
}

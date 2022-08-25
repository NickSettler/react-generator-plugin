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

    public static final String REDUX_MODULE_INDEX = "Redux Module Index.ts";
    public static final String REDUX_MODULE_MODULE = "Redux Module Module.ts";
    public static final String REDUX_MODULE_TYPES = "Redux Module Types.ts";
    public static final String REDUX_MODULE_REDUCER = "Redux Module Reducer.ts";
    public static final String REDUX_MODULE_ACTIONS = "Redux Module Actions.ts";
    public static final String REDUX_MODULE_SELECTORS = "Redux Module Selectors.ts";
    public static final String REDUX_MODULE_SCHEMA = "Redux Module Schema.ts";
    public static final String REDUX_MODULE_SAGA = "Redux Module Saga.ts";
    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("React Generator", PluginIcon);

        group.addTemplate(new FileTemplateDescriptor(COMPONENT_TEMPLATE, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(COMPONENT_HOOK, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(COMPONENT_INDEX, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(COMPONENT_TYPES, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_INDEX, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_MODULE, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_TYPES, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_REDUCER, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_ACTIONS, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_SELECTORS, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_SCHEMA, PluginIcon));
        group.addTemplate(new FileTemplateDescriptor(REDUX_MODULE_SAGA, PluginIcon));


        return group;
    }
}
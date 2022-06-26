/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.comusika.studio;

import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.exbin.framework.XBBaseApplication;
import org.exbin.framework.api.Preferences;
import org.exbin.framework.api.XBApplicationModuleRepository;
import org.exbin.framework.bined.BinedModule;
import org.exbin.framework.bined.preferences.BinaryAppearancePreferences;
import org.exbin.framework.about.api.AboutModuleApi;
import org.exbin.framework.editor.api.EditorModuleApi;
import org.exbin.framework.file.api.FileModuleApi;
import org.exbin.framework.frame.api.ApplicationFrameHandler;
import org.exbin.framework.frame.api.FrameModuleApi;
import org.exbin.framework.action.api.ActionModuleApi;
import org.exbin.framework.options.api.OptionsModuleApi;
import org.exbin.framework.update.api.UpdateModuleApi;
import org.exbin.framework.utils.LanguageUtils;
import org.exbin.framework.editor.api.EditorProvider;
import org.exbin.framework.editor.api.EditorProviderVariant;
import org.exbin.framework.help.online.api.HelpOnlineModuleApi;
import org.exbin.framework.operation.undo.api.OperationUndoModuleApi;
import org.exbin.framework.operation.undo.api.UndoFileHandler;

/**
 * Comusika application main class.
 *
 * @version 0.1.1 2022/01/25
 * @author ExBin Project (http://exbin.org)
 */
@ParametersAreNonnullByDefault
public class StudioApp {

    private static final String BINARY_PLUGIN_ID = "binary";

    private static final String OPTION_HELP = "h";
    private static final String OPTION_VERBOSE = "v";
    private static final String OPTION_DEV = "dev";

    private static final ResourceBundle bundle = LanguageUtils.getResourceBundleByClass(StudioApp.class);

    /**
     * Main method launching the application.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            // Parameters processing
            Options opt = new Options();
            opt.addOption(OPTION_HELP, "help", false, bundle.getString("cl_option_help"));
            opt.addOption(OPTION_VERBOSE, false, bundle.getString("cl_option_verbose"));
            opt.addOption(OPTION_DEV, false, bundle.getString("cl_option_dev"));
            OptionGroup editorProviderType = new OptionGroup();
            opt.addOptionGroup(editorProviderType);
            BasicParser parser = new BasicParser();
            CommandLine cl = parser.parse(opt, args);
            if (cl.hasOption(OPTION_HELP)) {
                HelpFormatter f = new HelpFormatter();
                f.printHelp(bundle.getString("cl_syntax"), opt);
            } else {
                boolean verboseMode = cl.hasOption(OPTION_VERBOSE);
                boolean devMode = cl.hasOption(OPTION_DEV);
                String editorProvideType = editorProviderType.getSelected();

                XBBaseApplication app = new XBBaseApplication();
                Preferences preferences = app.createPreferences(StudioApp.class);
                app.setAppBundle(bundle, LanguageUtils.getResourceBaseNameBundleByClass(StudioApp.class));
                BinaryAppearancePreferences binaryAppearanceParameters = new BinaryAppearancePreferences(preferences);

                XBApplicationModuleRepository moduleRepository = app.getModuleRepository();
                moduleRepository.addClassPathModules();
                moduleRepository.addModulesFromManifest(StudioApp.class);
                moduleRepository.loadModulesFromPath(new File("plugins").toURI());
                moduleRepository.initModules();
                app.init();

                final FrameModuleApi frameModule = moduleRepository.getModuleByInterface(FrameModuleApi.class);
                EditorModuleApi editorModule = moduleRepository.getModuleByInterface(EditorModuleApi.class);
                ActionModuleApi actionModule = moduleRepository.getModuleByInterface(ActionModuleApi.class);
                AboutModuleApi aboutModule = moduleRepository.getModuleByInterface(AboutModuleApi.class);
                HelpOnlineModuleApi linkModule = moduleRepository.getModuleByInterface(HelpOnlineModuleApi.class);
                OperationUndoModuleApi undoModule = moduleRepository.getModuleByInterface(OperationUndoModuleApi.class);
                FileModuleApi fileModule = moduleRepository.getModuleByInterface(FileModuleApi.class);
                OptionsModuleApi optionsModule = moduleRepository.getModuleByInterface(OptionsModuleApi.class);
                UpdateModuleApi updateModule = moduleRepository.getModuleByInterface(UpdateModuleApi.class);

                BinedModule binedModule = moduleRepository.getModuleByInterface(BinedModule.class);
                EditorProviderVariant editorProviderVariant = EditorProviderVariant.SINGLE;
                binedModule.initEditorProvider(editorProviderVariant);
                EditorProvider editorProvider = binedModule.getEditorProvider();
                editorModule.registerEditor(BINARY_PLUGIN_ID, editorProvider);

                frameModule.createMainMenu();
                try {
                    updateModule.setUpdateUrl(new URL(bundle.getString("update_url")));
                    updateModule.setUpdateDownloadUrl(new URL(bundle.getString("update_download_url")));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(StudioApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                updateModule.registerDefaultMenuItem();
                aboutModule.registerDefaultMenuItem();
                try {
                    linkModule.setOnlineHelpUrl(new URL(bundle.getString("online_help_url")));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(StudioApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                linkModule.registerOnlineHelpMenu();

                frameModule.registerExitAction();
                frameModule.registerBarsVisibilityActions();

                fileModule.registerMenuFileHandlingActions();
                if (editorProviderVariant == EditorProviderVariant.MULTI) {
                    editorModule.registerMenuFileCloseActions();
                }

                fileModule.registerToolBarFileHandlingActions();
                fileModule.registerRecenFilesMenuActions();
                fileModule.registerCloseListener();

                undoModule.registerMainMenu();
                undoModule.registerMainToolBar();
                undoModule.registerUndoManagerInMainMenu();

                // Register clipboard editing actions
                actionModule.registerClipboardTextActions();
                actionModule.registerMenuClipboardActions();
                actionModule.registerToolBarClipboardActions();

                optionsModule.registerMenuAction();

                binedModule.registerEditFindMenuActions();
                binedModule.registerCodeTypeToolBarActions();
                binedModule.registerShowUnprintablesToolBarActions();
//                binedModule.registerEditFindToolBarActions();
                binedModule.registerViewUnprintablesMenuActions();
                binedModule.registerViewValuesPanelMenuActions();
                binedModule.registerToolsOptionsMenuActions();
                binedModule.registerClipboardCodeActions();
                binedModule.registerOptionsMenuPanels();
                binedModule.registerGoToPosition();
                binedModule.registerInsertDataAction();
                binedModule.registerPropertiesMenu();
                // TODO binedModule.registerPrintMenu();
                binedModule.registerViewModeMenu();
                binedModule.registerCodeTypeMenu();
                binedModule.registerPositionCodeTypeMenu();
                binedModule.registerHexCharactersCaseHandlerMenu();
                binedModule.registerLayoutMenu();

                final ApplicationFrameHandler frameHandler = frameModule.getFrameHandler();
//                UndoHandlerWrapper undoHandlerWrapper = new UndoHandlerWrapper();

                undoModule.setUndoHandler(((UndoFileHandler) editorProvider).getUndoHandler());
                editorModule.registerUndoHandler();

                binedModule.registerStatusBar();
                binedModule.registerOptionsPanels();
                binedModule.getBinaryStatusPanel();
                updateModule.registerOptionsPanels();

                binedModule.loadFromPreferences(preferences);

                frameModule.addExitListener((ApplicationFrameHandler afh) -> {
                    frameModule.saveFramePosition();
                    return true;
                });

                frameHandler.setMainPanel(editorModule.getEditorComponent());

                frameHandler.setDefaultSize(new Dimension(600, 400));
                frameModule.loadFramePosition();
                optionsModule.initialLoadFromPreferences();
                frameHandler.showFrame();

                String filePath = null;
                List fileArgs = cl.getArgList();
                if (!fileArgs.isEmpty()) {
                    filePath = (String) fileArgs.get(0);
                }

                if (filePath == null) {
                    binedModule.start();
                } else {
                    binedModule.startWithFile(filePath);
                }

                updateModule.checkOnStart(frameHandler.getFrame());
            }
        } catch (ParseException | RuntimeException ex) {
            Logger.getLogger(StudioApp.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}

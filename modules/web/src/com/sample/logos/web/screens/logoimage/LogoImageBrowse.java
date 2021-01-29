package com.sample.logos.web.screens.logoimage;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.components.StreamResource;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.sample.logos.entity.LogoImage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import sun.security.jgss.LoginConfigImpl;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@UiController("logos_LogoImage.browse")
@UiDescriptor("logo-image-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class LogoImageBrowse extends MasterDetailScreen<LogoImage> {
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private Logger log;
    @Inject
    private Notifications notifications;

    @Subscribe("uploadField")
    public void onUploadFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        LogoImage logo = getEditedEntity();

        File file = fileUploadingAPI.getFile(uploadField.getFileId());
        if (file != null) {
            try {
                try (FileInputStream input = new FileInputStream(file)) {
                    logo.setContent(IOUtils.toByteArray(input));
                }
                logo.setFileName(event.getFileName());
            } catch (IOException e) {
                log.error("Error", e);
                notifications.create(Notifications.NotificationType.ERROR)
                        .withCaption("Failed to upload image")
                        .show();
            }
        }
    }


}
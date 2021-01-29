package com.sample.logos.web.controller;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.security.WebAnonymousSessionHolder;
import com.sample.logos.entity.LogoImage;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.concurrent.Callable;

@Controller
public class LogoController {
    @Inject
    private DataManager dataManager;

    @Inject
    protected WebAnonymousSessionHolder anonymousSessionHolder;

    @RequestMapping(value = "/logo/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> loadLogo(@PathVariable String fileName) throws Exception {
        LogoImage logo = withAnonymousUserSession(() -> {
            return dataManager.load(LogoImage.class)
                    .query("e.fileName = :name")
                    .parameter("name", fileName)
                    .optional()
                    .orElse(null);
        });

        if (logo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        byte[] content = logo.getContent();
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    private <T> T withAnonymousUserSession(Callable<T> callable) throws Exception {
        UserSession userSession = anonymousSessionHolder.getAnonymousSession();
        AppContext.setSecurityContext(new SecurityContext(userSession));
        try {
            return callable.call();
        } finally {
            AppContext.setSecurityContext(null);
        }
    }
}

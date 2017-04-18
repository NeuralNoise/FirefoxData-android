/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.accounts;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Data class for storing the url endpoints associated with a Firefox account.
 *
 * Inspired by iOS' FirefoxAccountConfiguration:
 *   https://github.com/mozilla-mobile/firefox-ios/blob/748e137dfd5b020b56fc481b25e0d2366acb3df2/Account/FirefoxAccountConfiguration.swift
 */
public class FirefoxAccountEndpointConfig {

    private static final String CONTEXT = "fx_ios_v1"; // TODO: get lib one. enters some mode...

    public final String label;

    public final URI authServerURL;
    public final URI oauthServerURL;
    public final URI profileServerURL;

    public final URI signInURL;
    public final URI settingsURL;
    public final URI forceAuthURL;

    private FirefoxAccountEndpointConfig(final String label, final String authServerURL, final String oauthServerURL,
            final String profileServerURL, final String signInURL, final String settingsURL, final String forceAuthURL) {
        this.label = label;
        try {
            this.authServerURL = new URI(authServerURL);
            this.oauthServerURL = new URI(oauthServerURL);
            this.profileServerURL = new URI(profileServerURL);
            this.signInURL = new URI(signInURL);
            this.settingsURL = new URI(settingsURL);
            this.forceAuthURL = new URI(forceAuthURL);
        } catch (final URISyntaxException e) {
            throw new IllegalArgumentException("Expected valid URI", e);
        }
    }

    public static FirefoxAccountEndpointConfig getStableDev() {
        return new FirefoxAccountEndpointConfig(
                /* label */ "StableDev",
                /* authServer */ "https://stable.dev.lcip.org/auth/v1",
                /* oauthServer */ "https://oauth-stable.dev.lcip.org",
                /* profileServer */ "https://stable.dev.lcip.org/profile",
                /* signIn */ appendContextParam("https://stable.dev.lcip.org/signin?service=sync"),
                /* settings */ appendContextParam("https://stable.dev.lcip.org/settings"),
                /* forceAuth */ appendContextParam("https://stable.dev.lcip.org/force_auth?service=sync")
        );
    }

    public static FirefoxAccountEndpointConfig getLatestDev() {
        return new FirefoxAccountEndpointConfig(
                /* label */ "LatestDev",
                /* authServer */ "https://latest.dev.lcip.org/auth/v1",
                /* oauthServer */ "https://oauth-latest.dev.lcip.org",
                /* profileServer */ "https://latest.dev.lcip.org/profile",
                /* signIn */ appendContextParam("https://latest.dev.lcip.org/signin?service=sync"),
                /* settings */ appendContextParam("https://latest.dev.lcip.org/settings"),
                /* forceAuth */ appendContextParam("https://latest.dev.lcip.org/force_auth?service=sync")
        );
    }

    public static FirefoxAccountEndpointConfig getStage() {
        return new FirefoxAccountEndpointConfig(
                /* label */ "Stage",
                /* authServer */ "https://api-accounts.stage.mozaws.net/v1",
                /* oauthServer */ "https://oauth.stage.mozaws.net/v1",
                /* profileServer */ "https://profile.stage.mozaws.net/v1",
                /* signIn */ appendContextParam("https://accounts.stage.mozaws.net/signin?service=sync"),
                /* settings */ appendContextParam("https://accounts.stage.mozaws.net/settings"),
                /* forceAuth */ appendContextParam("https://accounts.stage.mozaws.net/force_auth?service=sync")
        );
    }

    public static FirefoxAccountEndpointConfig getProduction() {
        return new FirefoxAccountEndpointConfig(
                /* label */ "Production",
                /* authServer */ "https://api.accounts.firefox.com/v1",
                /* oauthServer */ "https://oauth.accounts.firefox.com/v1",
                /* profileServer */ "https://profile.accounts.firefox.com/v1",
                /* signIn */ appendContextParam("https://accounts.firefox.com/signin?service=sync"),
                /* settings */ appendContextParam("https://accounts.firefox.com/settings"),
                /* forceAuth */ appendContextParam("https://accounts.firefox.com/force_auth?service=sync")
        );
    }

    private static String appendContextParam(final String url) {
        // TODO: better to append URL params with a dedicated url method.
        return url + (url.contains("?") ? "&" : "?") + "context=" + CONTEXT;
    }
}
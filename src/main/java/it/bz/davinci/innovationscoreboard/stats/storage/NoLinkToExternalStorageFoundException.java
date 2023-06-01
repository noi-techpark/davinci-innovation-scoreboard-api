// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.storage;

public class NoLinkToExternalStorageFoundException extends RuntimeException {
    public NoLinkToExternalStorageFoundException(String message) {
        super(message);
    }
}

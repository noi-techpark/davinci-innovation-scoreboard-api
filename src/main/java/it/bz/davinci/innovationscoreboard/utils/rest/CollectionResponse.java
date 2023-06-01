// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.utils.rest;

import lombok.Data;

import java.util.Collection;

@Data
public class CollectionResponse<T> {

    private final Collection<T> results;

    public CollectionResponse(Collection<T> results) {
        this.results = results;
    }
}

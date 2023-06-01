// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IctInCompaniesWithAtLeast10EmployeesEs {
    private String ITTER107;
    private String Territory;
    private String VARICT;
    private String dataType;
    private String ATECO_2007;
    private String NACE_2007;
    private String CLLVT;
    private String sizeClassesOfPersonsEmployed;
    private String TIME;
    private String selectTime;
    private BigDecimal value;
    private String flagCodes;
    private String flags;
}

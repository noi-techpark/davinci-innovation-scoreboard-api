// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.davinci.innovationscoreboard.stats.rest;

import it.bz.davinci.innovationscoreboard.stats.ResearchAndDevelopmentAggregator;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/statistics")
public class ResearchAndDevelopmentController {

    private final ResearchAndDevelopmentAggregator researchAndDevelopmentAggregator;

    @Autowired
    public ResearchAndDevelopmentController(ResearchAndDevelopmentAggregator researchAndDevelopmentAggregator) {
        this.researchAndDevelopmentAggregator = researchAndDevelopmentAggregator;
    }

    @GetMapping(value = "/research-and-development-personnel-in-house-divided-by-territory")
    public StatisticsResponseDto getResearchAndDevelopmentPersonnelInHouseDividedByTerritory() {
        return researchAndDevelopmentAggregator.getResearchAndDevelopmentPersonnelInHouseDividedByTerritory();
    }

    @GetMapping(value = "/domestic-research-and-development-expenditure-in-house-divided-by-territory")
    public StatisticsResponseDto getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory() {
        return researchAndDevelopmentAggregator.getDomesticResearchAndDevelopmentExpenditureInHouseDividedByTerritory();
    }
}

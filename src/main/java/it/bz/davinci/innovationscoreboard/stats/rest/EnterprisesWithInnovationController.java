package it.bz.davinci.innovationscoreboard.stats.rest;

import it.bz.davinci.innovationscoreboard.stats.EnterprisesWithInnovationAggregator;
import it.bz.davinci.innovationscoreboard.stats.dto.StatisticsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/statistics")
public class EnterprisesWithInnovationController {

    private final EnterprisesWithInnovationAggregator enterprisesWithInnovationAggregator;

    @Autowired
    public EnterprisesWithInnovationController(EnterprisesWithInnovationAggregator enterprisesWithInnovationAggregator) {
        this.enterprisesWithInnovationAggregator = enterprisesWithInnovationAggregator;
    }

    @GetMapping(value = "/enterprises-with-innovation-activities-divided-by-territory")
    public StatisticsResponseDto getEnterprisesWithInnovationActivitiesDividedByTerritory() {
        return enterprisesWithInnovationAggregator.getEnterprisesWithInnovationActivitiesDividedByTerritory();
    }

    @GetMapping(value = "/enterprises-with-innovation-activities-in-italy-devided-by-nace")
    public StatisticsResponseDto getEnterprisesWithInnovationActivitiesInItalyDevidedByNACE() {
        return enterprisesWithInnovationAggregator.getEnterprisesWithInnovationActivitiesInItalyDevidedByNACE();
    }

    @GetMapping(value = "enterprises-that-have-introduced-product-or-process-innovations-divided-by-territory")
    public StatisticsResponseDto getEnterprisesThatHaveIntroducedProductOrProcessInnovationsDividedByTerritory() {
        return enterprisesWithInnovationAggregator.getEnterprisesThatHaveIntroducedProductOrProcessInnovationsDividedByTerritory();
    }

    @GetMapping(value = "enterprises-that-have-introduced-product-or-process-innovations-in-italy-divided-by-nace")
    public StatisticsResponseDto getEnterprisesThatHaveIntroducedProductOrProcessInnovationsInItalyDividedByNace() {
        return enterprisesWithInnovationAggregator.getEnterprisesThatHaveIntroducedProductOrProcessInnovationsInItalyDividedByNace();
    }

}

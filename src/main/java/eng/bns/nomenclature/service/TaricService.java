package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.TaricRequest;

public interface TaricService {
    void createTaric (TaricRequest taricRequest);
    void updateTaric (TaricRequest taricRequest);
}

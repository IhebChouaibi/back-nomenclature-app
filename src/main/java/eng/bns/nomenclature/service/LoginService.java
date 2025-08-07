package eng.bns.nomenclature.service;

import com.nimbusds.jose.JOSEException;
import eng.bns.nomenclature.dto.AuthRequest;
import eng.bns.nomenclature.dto.AuthResponse;
import eng.bns.nomenclature.entities.User;

public interface LoginService

{
    AuthResponse login (AuthRequest authRequest) throws JOSEException;

}

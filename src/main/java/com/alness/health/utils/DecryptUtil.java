package com.alness.health.utils;

import javax.crypto.SecretKey;

import com.alness.health.taxpayer.dto.response.LegalRepresentativeResponse;
import com.alness.health.taxpayer.dto.response.TaxpayerResponse;

public class DecryptUtil {
    private DecryptUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void decryptLegalRep(LegalRepresentativeResponse response, String keyS) {
        SecretKey key = TextEncrypterUtil.stringToKey(keyS);
        response.setFullName(TextEncrypterUtil.decrypt(response.getFullName(), key));
        response.setRfc(TextEncrypterUtil.decrypt(response.getRfc(), key));
    }

    public static void decryptTaxpayer(TaxpayerResponse response, String keyS) {
        SecretKey key = TextEncrypterUtil.stringToKey(keyS);
        response.setCorporateReasonOrNaturalPerson(
                TextEncrypterUtil.decrypt(response.getCorporateReasonOrNaturalPerson(), key));
        response.setRfc(TextEncrypterUtil.decrypt(response.getRfc(), key));
    }

    /*public static void decryptLegalRepLite(LegalRepResp response, String keyS) {
        SecretKey key = TextEncrypterUtil.stringToKey(keyS);
        response.setFullName(TextEncrypterUtil.decrypt(response.getFullName(), key));
        response.setRfc(TextEncrypterUtil.decrypt(response.getRfc(), key));
    }

    public static void decryptTaxpayerLite(TaxpayerResp response, String keyS) {
        SecretKey key = TextEncrypterUtil.stringToKey(keyS);
        response.setCorporateReasonOrNaturalPerson(
                TextEncrypterUtil.decrypt(response.getCorporateReasonOrNaturalPerson(), key));
        response.setRfc(TextEncrypterUtil.decrypt(response.getRfc(), key));
    }*/

}

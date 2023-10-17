package br.gov.mt.mti.siga.assinador.controller;

import bluecrystal.service.api.BlucApi;
import bluecrystal.service.api.ValidateResponse;
import br.gov.mt.mti.siga.assinador.domain.HashResponse;
import br.gov.mt.mti.siga.assinador.domain.SignRequestBody;
import br.gov.mt.mti.siga.assinador.tools.HashTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.Date;

@Slf4j
@RestController
public class AssinadorController {

    @SneakyThrows
    @PostMapping("/api/v1/assinador-popup/doc/{id}/hash")
    public HashResponse hash(@PathVariable(name = "id") String id) {

        //TODO SUBSTITUA PELO CODIGO DE BUSCA DO DOCUMENTO A PARTIR DO ID
        final byte[] hash = Files.readAllBytes(new ClassPathResource("/documento/MTIATA202300024.pdf").getFile().toPath());

        HashResponse hashResponse = new HashResponse();
        hashResponse.setSha1(HashTools.toBase64Encode(HashTools.toSha1(hash)));
        hashResponse.setSha256(HashTools.toBase64Encode(HashTools.toSha256(hash)));
        log.info("Gerando hash do Id {} {}", id, hashResponse);

        return hashResponse;
    }

    @PutMapping("/api/v1/assinador-popup/doc/{id}/sign")
    public void assinadorPopupSave(@PathVariable(name = "id") String id, @RequestBody SignRequestBody signRequestBody) {
        log.info("Salvando Id {} Assinatura {}", id, signRequestBody.getEnvelope());
        log.info("Salvando Time {} ", signRequestBody.getTime());
        //TODO SALVE O CONTEUDO DO ENVELOPE DE ASSINATURA base64 e time PARA POSTERIOR ENVIO AO SIGADOC
    }


    @SneakyThrows
    @GetMapping("/api/v1/assinador-popup/doc/validar")
    public void validar() {

        final byte[] assinatura = Files.readAllBytes(new ClassPathResource("/assinaturas/assinatura_MTIOFI202300033.p7s").getFile().toPath());

        BlucApi blucApi = new BlucApi();
        ValidateResponse validateResponse = new ValidateResponse();
//        final int i = blucApi.validateSign(HashTools.toBase64Decode("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"), HashTools.toBase64Decode("oOPZS105jK7r1Y/Jfim5qFDdUyI="),HashTools.toBase64Decode("avvi9gH6ywYmiEmfkOrdfu2jeC/Jj7QTcXM9YALUZ8U="), new Date(), false, validateResponse);
        final int j = blucApi.validateSign(HashTools.toBase64Decode("MYIBxjAYBgkqhkiG9w0BCQMxCwYJKoZIhvcNAQcBMBwGCSqGSIb3DQEJBTEPFw0yMzAyMTQyMjE2MDRaMC8GCSqGSIb3DQEJBDEiBCBq++L2AfrLBiaISZ+Q6t1+7aN4L8mPtBNxcz1gAtRnxTCBlAYLKoZIhvcNAQkQAg8xgYQwgYEGCGBMAQcBAQIDMC8wCwYJYIZIAWUDBAIBBCCxboi793MipnmVt5B4d47T0Op8iFh7b21Ri3Fej3aj1TBEMEIGCyqGSIb3DQEJEAUBFjNodHRwOi8vcG9saXRpY2FzLmljcGJyYXNpbC5nb3YuYnIvUEFfQURfUkJfdjJfMy5kZXIwgcMGCyqGSIb3DQEJEAIvMYGzMIGwMIGtMIGqBCDsfWsOvJpjr03qhD5wtmlfW7cMraSIF+N65TZwheJ5uDCBhTB5pHcwdTELMAkGA1UEBhMCQlIxEzARBgNVBAoMCklDUC1CcmFzaWwxNjA0BgNVBAsMLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFsIGRvIEJyYXNpbCAtIFJGQjEZMBcGA1UEAwwQQUMgU0VSQVNBIFJGQiB2NQIIRjBjx8YV1g4="), HashTools.toBase64Decode("oOPZS105jK7r1Y/Jfim5qFDdUyI="), HashTools.toBase64Decode("avvi9gH6ywYmiEmfkOrdfu2jeC/Jj7QTcXM9YALUZ8U="), new Date(), false, validateResponse);
        System.out.println(validateResponse.getCn());

    }
}

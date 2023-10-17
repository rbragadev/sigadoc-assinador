package br.gov.mt.mti.siga.assinador.domain;

import lombok.Data;

@Data
public class SignRequestBody {
    private String certificate;
    private String code;
    private String cpf;
    private String envelope;
    private String extra;
    private String policy;
    private String policyversion;
    private String sha1;
    private String sha256;
    private String time;
}

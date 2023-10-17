package br.gov.mt.mti.siga.assinador.domain;

import lombok.Data;

@Data
public class HashResponse {
    private String sha1;
    private String sha256;
}

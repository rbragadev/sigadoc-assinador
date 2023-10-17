//
// Provider: AssijusPopup
//

var providerSmartPass = {
  assinar: function () {
    var parent = this;

    // Lógica para iniciar o processo de assinatura
    parent.beginCallback();

    var file = document.getElementById("fileToUpload").files[0];
    if (!file) {
      alert("Selecione um arquivo para assinar.");
      return;
    }

    let formData = new FormData();
    formData.append("file", file);

    $.ajax({
      url: "https://dev-seguro.xvia.mti.mt.gov.br/r1/mt-dev/GOV/MTI/SMARTPASS/file-upload",
      type: "POST",
      data: formData,
      contentType: false,
      processData: false,
      success: function (response) {
        var hash = response.hash;
        parent.hashCallback(hash, function (hashData) {
          $.post(
            "https://dev-seguro.xvia.mti.mt.gov.br/r1/mt-dev/GOV/MTI/SMARTPASS/signature-request",
            {
              userId: "05104603563",
              description: "Teste de assinatura",
              payload: hashData.sha256,
              detached: true,
              coordinates: {
                x: 100,
                y: 100,
              },
            },
            function (signatureResponse) {
              parent.saveCallback(
                hash,
                signatureResponse,
                function (saveResponse) {
                  if (saveResponse.success) {
                    // Lógica após salvar assinatura com sucesso
                    parent.endCallback();
                  } else {
                    // Lógica de erro após tentativa de salvar assinatura
                    parent.errorCallback(
                      hash,
                      "Erro ao salvar assinatura",
                      function () {}
                    );
                  }
                }
              );
            }
          );
        });
      },
      error: function (jqXHR, textStatus, errorMessage) {
        parent.errorCallback(null, errorMessage, function () {});
      },
    });
  },
  beginCallback: function () {
    $("#log").append("<li>Início</li>");
  },
  hashCallback: function (hash, callback) {
    $("#log").append("<li>Fornecendo hash: " + hash + "</li>");
    callback({ sha256: hash }); // Supondo que estamos usando sha256 para SmartPass
  },
  saveCallback: function (id, sign, callback) {
    $("#log").append(
      "<li>Gravando assinatura de " + id + ": " + JSON.stringify(sign) + "</li>"
    );
    callback({ success: true });
  },
  errorCallback: function (id, err, callback) {
    $("#log").append("<li>Erro assinando " + id + ": " + err + "</li>");
    callback();
  },
  endCallback: function () {
    $("#log").append("<li>Fim</li>");
    // Lógica adicional após o fim do processo
  },
};

var providerAssijusPopup = {
  assinar: function (urlRedirect) {
    var parent = this;
    //Passagem de parâmetro para javascript contido no assijus em https://assijus.mt.gov.br/assijus/popup-api.js
    window.produzirAssinaturaDigital({
      //versão do bootstrap
      ui: "bootstrap-4",

      //url do ambiente do assijus(realizar apontamento para producao após previo pedido de cadastro do dominio da aplicacao)
      iframePopupUrl: "https://homologa.assijus.mt.gov.br/assijus/popup.html",

      // array de documentos com o atributo id do documento referente ao sistema de origem,
      // para que possa ser utilizado nos endpoints  do AssinadorController e
      // ocorra a interação entre o sistema de origem e o Assijus
      // O atributo code recebe o nome/numero do documento para ser exibido pelo popup durante a assinatura
      docs: [{ id: 545, code: "MTIOFI202300033" }],

      beginCallback: function () {
        $("#log").append("<li>Início</li>");
      },

      hashCallback: function (id, funcaoRetornoAssijus) {
        $.ajax({
          url: "/api/v1/assinador-popup/doc/" + id + "/hash",
          type: "POST",
          headers: { "X-API-KEY": "a4d24bc2-f342-4029-b20e-a548aee6d1a7" },
          async: false,
          success: function (xhr) {
            $("#log").append(
              "<li>Fornecendo hash para " +
                id +
                ": " +
                JSON.stringify(xhr) +
                "</li>"
            );
            funcaoRetornoAssijus({ sha1: xhr.sha1, sha256: xhr.sha256 });
          },
          error: function (xhr) {
            $("#log").append(
              "ID: " +
                id +
                " - Erro calculando hash de documento: " +
                xhr.responseJSON.errormsg
            );
            funcaoRetornoAssijus({});
          },
        });
      },

      saveCallback: function (id, sign, funcaoRetornoAssijus) {
        $.ajax({
          url: "/api/v1/assinador-popup/doc/" + id + "/sign",
          type: "PUT",
          contentType: "application/json",
          headers: { "X-API-KEY": "a4d24bc2-f342-4029-b20e-a548aee6d1a7" },
          data: JSON.stringify(sign),
          async: false,
          success: function (xhr) {
            $("#log").append(
              "<li>Gravando assinatura de " +
                id +
                ": " +
                JSON.stringify(sign) +
                "</li>"
            );
            funcaoRetornoAssijus({ success: true });
          },
          error: function (xhr) {
            $("#log").append(
              "ID: " +
                id +
                " - Erro na gravação da assinatura: " +
                xhr.responseJSON.errormsg
            );
            funcaoRetornoAssijus({});
          },
        });
      },

      errorCallback: function (id, err, funcaoRetornoAssijus) {
        $("#log").append(
          "<li>Erro assinando " + id + ": " + JSON.stringify(err) + "</li>"
        );
        funcaoRetornoAssijus();
      },

      endCallback: function () {
        $("#log").append("<li>Fim</li>");
        //pode adicionar um redirect apos o fim da assinatura
      },
    });
  },
};

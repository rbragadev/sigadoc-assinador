//package br.gov.mt.mti.siga.assinador.filter;
//
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//
//
///**
// * Filtro de preautenticacao a apartir do header definido na propriedade siga.assinador.client.x-api-key-name
// *
// * @author Ricardo Santos
// */
//public class APIKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
//    private String principalRequestHeader;
//
////    public APIKeyAuthFilter(String principalRequestHeader) {
//        this.principalRequestHeader = principalRequestHeader;
//    }
//
//    @Override
//    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//        return request.getHeader(principalRequestHeader);
//    }
//
//    @Override
//    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//        return "N/A";
//    }
//}

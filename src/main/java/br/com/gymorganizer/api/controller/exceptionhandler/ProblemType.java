package br.com.gymorganizer.api.controller.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    USUARIO_JA_ATIVO("/usuario-ja-ativo", "Usuário já ativo"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "mensagem incompreensível"),
    ATRIBUTO_EM_USO("/atributo-em-uso", "Atributo em uso"),
    ERRO_DE_NEGOCIO("/erro-de-negocio", "Erro de negocio");

    private String uri;
    private String title;

    ProblemType(String path, String title) {
        this.uri = path;
        this.title = title;
    }
}

package br.com.gymorganizer.api.controller.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    USUARIO_JA_ATIVO("/usuario-ja-ativo", "Usuário ja ativo"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensiva", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ATRIBUTO_EM_USO("/atributo-em-uso", "Atributo em uso");

    private String uri;
    private String title;

    ProblemType(String path, String title) {
        this.uri = path;
        this.title = title;
    }

}

package com.br.SmsTi.Enum;// Convenção: 'enums' em minúsculo para pacotes

// A anotação @Data do Lombok não é necessária para enums simples como este
public enum Categorias {
    HARDWARE("Problemas com equipamentos físicos"),
    SOFTWARE("Problemas com programas e aplicações"),
    REDES("Problemas de conectividade e comunicação"),
    SEGURANCA("Incidentes de segurança e acessos"),
    DADOS_E_ARMAZENAMENTO("Problemas com dados e armazenamento"),
    SISTEMA_OPERACIONAL("Problemas com o sistema operacional"),
    PERIFERICOS("Problemas com impressoras, mouses, teclados, etc."),
    SERVICOS_CORPORATIVOS("Problemas com sistemas críticos da empresa (ERP, CRM, E-mail)"),
    ACESSO_E_SENHA("Problemas de login, senhas e permissões"),
    CONFIGURACAO("Solicitações ou problemas de configuração"),
    OUTROS("Outros tipos de problemas não listados");

    private final String descricao;

    // O construtor deve ter o mesmo nome do enum: 'Categorias'
    Categorias(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
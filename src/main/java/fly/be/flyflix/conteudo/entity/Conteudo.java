package fly.be.flyflix.conteudo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "conteudos")
@Entity(name = "Conteudo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String duracao;
    private String link;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private boolean ativo;

//    public Conteudo(DadosCadastroConteudo dados) {
//        this.nome = dados.nome();
//        this.descricao = dados.descricao();
//        this.duracao = dados.duracao();
//        this.link = dados.link();
//        this.categoria = dados.categoria();
//        this.ativo = true;
//    }

//    public void atualizarInformacoes(DadosConteudo dados){
//        if (dados.nome()!=null) {
//            this.nome = dados.nome();
//        }
//        if (dados.descricao()!=null) {
//            this.descricao = dados.descricao();
//        }
//        if (dados.duracao()!=null) {
//            this.duracao = dados.duracao();
//        }
//        if (dados.link()!=null) {
//            this.link = dados.link();
//        }
//        if (dados.categoria()!=null) {
//            this.categoria = dados.categoria();
//        }
//    }
//
//    public void arquivar() {
//        this.ativo = false;
//    }
}

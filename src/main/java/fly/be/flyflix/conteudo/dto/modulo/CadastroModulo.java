package fly.be.flyflix.conteudo.dto.modulo;

import fly.be.flyflix.conteudo.dto.aula.CadastroAula;
import java.util.List;

public record CadastroModulo(
        String titulo,
        Integer ordem,
        Long cursoId,
        List<CadastroAula> aulas // ✅ Lista de aulas que pertencem ao módulo
) {}
